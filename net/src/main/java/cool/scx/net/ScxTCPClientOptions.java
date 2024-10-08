package cool.scx.net;

public class ScxTCPClientOptions {

    private TLS tls;

    public ScxTCPClientOptions() {
        this.tls = null;
    }

    public TLS tls() {
        return tls;
    }

    public ScxTCPClientOptions tls(TLS tls) {
        this.tls = tls;
        return this;
    }

}
