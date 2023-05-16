package cool.scx.lab.netty.http_proxy.util;

import java.io.Serializable;

public final class ClientHttpRequest implements Serializable {

    private final String host;
    private final int port;
    private boolean isHttps;

    public ClientHttpRequest(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public boolean isHttps() {
        return isHttps;
    }

    public void setHttps(boolean https) {
        isHttps = https;
    }

    public String host() {
        return host;
    }

    public int port() {
        return port;
    }

}
