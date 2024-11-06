package cool.scx.tls_socket_channel;

import cool.scx.net.tls.TLS;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class TLSServerSocketChannel extends AbstractServerSocketChannel {

    private final TLS tls;

    public TLSServerSocketChannel(TLS tls) throws IOException {
        this.tls = tls;
    }

    @Override
    public SocketChannel accept() throws IOException {
        var accept = this.serverSocketChannel.accept();
        return new TLSSocketChannel(tls, accept);
    }

}
