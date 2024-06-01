package cool.scx.lab.netty.http_proxy.handler.proxy;


import cool.scx.lab.netty.http_proxy.util.ClientHttpRequest;
import io.netty.channel.ChannelHandlerContext;


public interface IProxyHandler {
    /**
     * 发送到server
     *
     * @param clientRequest 客户端请求
     * @param ctx           ChannelHandlerContext
     * @param msg           数据
     */
    void sendToServer(ClientHttpRequest clientRequest, final ChannelHandlerContext ctx, final Object msg);

    /**
     * 发送到client
     */
    void sendToClient(ClientHttpRequest clientRequest, final ChannelHandlerContext ctx, final Object msg);
}
