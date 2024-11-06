package cool.scx.tls_socket_channel;

import cool.scx.net.tls.TLS;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class TLSServerSocketChannel extends AbstractServerSocketChannel {

    private final TLS tls;

    public TLSServerSocketChannel(TLS tls, ServerSocketChannel socketChannel) throws IOException {
        super(socketChannel);
        this.tls = tls;
    }

    public TLSServerSocketChannel(TLS tls) throws IOException {
        this(tls, ServerSocketChannel.open());
    }

    @Override
    public SocketChannel accept() throws IOException {
        var accept = this.serverSocketChannel.accept();
        return new TLSSocketChannel(tls, accept);
    }

}
