package cool.scx.lab.netty.http_proxy.handler.proxy;

import cool.scx.lab.netty.http_proxy.handler.response.HttpProxyResponseHandler;
import cool.scx.lab.netty.http_proxy.util.ClientHttpRequest;
import cool.scx.lab.netty.http_proxy.util.Helper;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpProxyHandler extends ChannelInboundHandlerAdapter implements IProxyHandler {

    private final Logger logger = LoggerFactory.getLogger(HttpProxyHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.debug("[HttpProxyHandler]");
        if (msg instanceof HttpRequest httpRequest) {
            //获取客户端请求
            var clientRequest = Helper.getClientHttpRequest(ctx.channel(), httpRequest);
            if (httpRequest.method() == HttpMethod.CONNECT) {
                //代理建立成功
                //HTTP代理建立连接
                HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, Helper.CONNECT_SUCCESS);
                ctx.writeAndFlush(response);
                logger.debug("[HttpProxyHandler][channelRead] sendSuccessResponseConnect");
                ctx.channel().pipeline().remove("httpRequestDecoder");
                ctx.channel().pipeline().remove("httpResponseEncoder");
                ctx.channel().pipeline().remove("httpAggregator");
                ReferenceCountUtil.release(msg);
                return;
            }
            if (clientRequest.isHttps()) {
                //https请求不在此处转发
                super.channelRead(ctx, msg);
                return;
            }
            sendToServer(clientRequest, ctx, msg);
            return;
        }
        super.channelRead(ctx, msg);
    }

    @Override
    public void sendToServer(ClientHttpRequest clientRequest, ChannelHandlerContext ctx, Object msg) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(ctx.channel().eventLoop())
                // 注册线程池
                .channel(ctx.channel().getClass())
                // 使用NioSocketChannel来作为连接用的channel类
                .handler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        //添加接收远程server的handler
                        ch.pipeline().addLast(new HttpRequestEncoder());
                        ch.pipeline().addLast(new HttpResponseDecoder());
                        ch.pipeline().addLast(new HttpObjectAggregator(6553600));
                        //代理handler,负责给客户端响应结果
                        ch.pipeline().addLast(new HttpProxyResponseHandler(ctx.channel()));
                    }
                });

        //连接远程server
        ChannelFuture cf = bootstrap.connect(clientRequest.host(), clientRequest.port());
        cf.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                //连接成功
                future.channel().writeAndFlush(msg);
                logger.debug("[operationComplete] connect remote server success!");
            } else {
                //连接失败
                logger.error("[operationComplete] 连接远程server失败了");
                ctx.channel().close();
            }
        });
    }

    @Override
    public void sendToClient(ClientHttpRequest clientRequest, ChannelHandlerContext ctx, Object msg) {

    }

}
