package cool.scx.lab.netty.http_proxy.handler.proxy;


import cool.scx.lab.netty.http_proxy.handler.response.HttpProxyResponseHandler;
import cool.scx.lab.netty.http_proxy.util.ClientHttpRequest;
import cool.scx.lab.netty.http_proxy.util.Helper;
import cool.scx.lab.netty.http_proxy.util.HttpsSupport;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.util.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpsProxyHandler extends ChannelInboundHandlerAdapter implements IProxyHandler {
    private Logger logger = LoggerFactory.getLogger(HttpsProxyHandler.class);
    private ChannelFuture httpsRequestCf;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.debug("[HttpsProxyHandler]");
        Attribute<ClientHttpRequest> clientRequestAttribute = ctx.channel().attr(Helper.CLIENT_HTTP_REQUEST_KEY);
        ClientHttpRequest clientRequest = clientRequestAttribute.get();
        if (msg instanceof HttpRequest) {
            sendToServer(clientRequest, ctx, msg);
        } else if (msg instanceof HttpContent) {
            logger.debug("[HttpsProxyHandler][HttpContent]不作处理！");
            //content不做处理
//            ReferenceCountUtil.release(msg);
        } else {
            ByteBuf byteBuf = (ByteBuf) msg;
            // ssl握手
            if (byteBuf.getByte(0) == 22) {
                logger.debug("[HttpsProxyHandler][do hands]");
                sendToClient(clientRequest, ctx, msg);
            }
        }
    }

    @Override
    public void sendToServer(ClientHttpRequest clientRequest, ChannelHandlerContext ctx, Object msg) {
        logger.debug("[HttpsProxyHandler][sendToServer] 发送https请求到server");
        Channel clientChannel = ctx.channel();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup(1))
                // 注册线程池
                .channel(NioSocketChannel.class)
                // 使用NioSocketChannel来作为连接用的channel类
                .handler(new ChannelInitializer() {

                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        //添加一个ssl处理器进行处理
                        ch.pipeline().addLast(
                                HttpsSupport.getInstance().getClientSslCtx().newHandler(ch.alloc(),
                                        clientRequest.host(), clientRequest.port()));
                        ch.pipeline().addLast("httpCodec", new HttpClientCodec());
                        //添加响应处理器
                        ch.pipeline().addLast("proxyClientHandle", new HttpProxyResponseHandler(clientChannel));
                    }
                });
        httpsRequestCf = bootstrap.connect(clientRequest.host(), clientRequest.port());
        //建立连接
        httpsRequestCf.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                future.channel().writeAndFlush(msg);
                logger.debug("[HttpsProxyHandler][sendToServer]目标连接创建成功，并已转发了数据包");
            } else {
                logger.error("[HttpsProxyHandler][sendToServer]连接远程server失败");
            }
        });
    }

    @Override
    public void sendToClient(ClientHttpRequest clientRequest, ChannelHandlerContext ctx, Object msg) {
        try {
            logger.debug("[HttpsProxyHandler][sendToClient] 与客户端进行https握手");
            SslContext sslCtx = SslContextBuilder
                    .forServer(HttpsSupport.getInstance().getServerPriKey(), HttpsSupport.getInstance().getCert(clientRequest.host())).build();
            //接收客户端请求，将客户端的请求内容解码
            ctx.pipeline().addFirst("httpRequestDecoder", new HttpRequestDecoder());
            //发送响应给客户端，并将发送内容编码
            ctx.pipeline().addFirst("httpResponseEncoder", new HttpResponseEncoder());
            //http聚合
            ctx.pipeline().addLast("httpAggregator", new HttpObjectAggregator(65536));
            //ssl处理
            ctx.pipeline().addFirst("sslHandle", sslCtx.newHandler(ctx.alloc()));
            // 重新过一遍pipeline，拿到解密后的的http报文
            ctx.pipeline().fireChannelRead(msg);
            Attribute<ClientHttpRequest> clientRequestAttribute = ctx.channel().attr(Helper.CLIENT_HTTP_REQUEST_KEY);
            clientRequest.setHttps(true);
            clientRequestAttribute.set(clientRequest);
        } catch (Exception e) {
            logger.error("[sendToServer] err:{}", e.getMessage());
        }
    }

}
