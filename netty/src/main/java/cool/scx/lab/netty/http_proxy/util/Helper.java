package cool.scx.lab.netty.http_proxy.util;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.AttributeKey;

public final class Helper {

    public static final AttributeKey<ClientHttpRequest> CLIENT_HTTP_REQUEST_KEY = AttributeKey.valueOf("clientHttpRequest");

    public  static final HttpResponseStatus CONNECT_SUCCESS = new HttpResponseStatus(200, "Connection established");

    /**
     * 获取代理请求
     *
     * @param httpRequest http请求
     */
    private static ClientHttpRequest parseClientHttpRequest(HttpRequest httpRequest) {
        //从header中获取出host
        var host = httpRequest.headers().get("host");
        //从host中获取出端口
        String[] hostStrArr = host.split(":");
        int port = 80;
        if (hostStrArr.length > 1) {
            port = Integer.parseInt(hostStrArr[1]);
        } else if (httpRequest.uri().startsWith("https")) {
            port = 443;
        }
        return new ClientHttpRequest(hostStrArr[0], port);
    }

    public static ClientHttpRequest getClientHttpRequest(Channel channel, HttpRequest httpRequest) {
        //将clientRequest保存到channel中
        var attribute = channel.attr(CLIENT_HTTP_REQUEST_KEY);
        var clientRequest = attribute.get();
        if (clientRequest == null) {
            //从本次请求中获取
            clientRequest = parseClientHttpRequest(httpRequest);
            //将clientRequest保存到channel中
            attribute.setIfAbsent(clientRequest);
        }
        return clientRequest;
    }

    public static ClientHttpRequest getClientHttpRequest(Channel channel) {
        var attribute = channel.attr(CLIENT_HTTP_REQUEST_KEY);
        return attribute.get();
    }

}
