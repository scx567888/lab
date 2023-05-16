package cool.scx.lab.netty.http_proxy.handler.proxy;

import cool.scx.lab.netty.http_proxy.handler.response.SocksResponseHandler;
import cool.scx.lab.netty.http_proxy.util.ClientHttpRequest;
import cool.scx.lab.netty.http_proxy.util.Helper;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class SocksProxyHandler extends ChannelInboundHandlerAdapter implements IProxyHandler {
    private Logger logger = LoggerFactory.getLogger(HttpsProxyHandler.class);

    private ChannelFuture notHttpReuqstCf;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.debug("[SocksProxyHandler]");
        var clientRequest = Helper.getClientHttpRequest(ctx.channel());
        sendToServer(clientRequest, ctx, msg);
    }

    @Override
    public void sendToServer(ClientHttpRequest clientRequest, ChannelHandlerContext ctx, Object msg) {
        //不是http请求就不管，全转发出去
        if (notHttpReuqstCf == null) {
            //连接至目标服务器
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(ctx.channel().eventLoop())
                    // 复用客户端连接线程池
                    .channel(ctx.channel().getClass())
                    // 使用NioSocketChannel来作为连接用的channel类
                    .handler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new SocksResponseHandler(ctx.channel()));
                        }
                    });
            notHttpReuqstCf = bootstrap.connect(clientRequest.host(), clientRequest.port());
            notHttpReuqstCf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        future.channel().writeAndFlush(msg);
                    } else {
                        ctx.channel().close();
                    }
                }
            });
        } else {
            notHttpReuqstCf.channel().writeAndFlush(msg);
        }
    }

    @Override
    public void sendToClient(ClientHttpRequest clientRequest, ChannelHandlerContext ctx, Object msg) {

    }
}
