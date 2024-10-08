package cool.scx.net;

public class ScxTCPServerOptions {

    private int port;

    public ScxTCPServerOptions() {
        this.port = 0;
    }

    public int port() {
        return port;
    }

    public ScxTCPServerOptions port(int port) {
        this.port = port;
        return this;
    }

}
