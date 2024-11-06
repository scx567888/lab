package cool.scx.tls_socket_channel;

import cool.scx.net.tls.TLS;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TLSSocketChannel extends AbstractSocketChannel {

    private final TLS tls;

    protected TLSSocketChannel(TLS tls, SocketChannel socketChannel) {
        super(socketChannel);
        this.tls = tls;
    }

    protected TLSSocketChannel(TLS tls) throws IOException {
        this.tls = tls;
    }

    public void startHandshake() throws IOException {

    }

    @Override
    public int read(ByteBuffer dst) throws IOException {
        return 0;
    }

    @Override
    public long read(ByteBuffer[] dsts, int offset, int length) throws IOException {
        return 0;
    }

    @Override
    public int write(ByteBuffer src) throws IOException {
        return 0;
    }

    @Override
    public long write(ByteBuffer[] srcs, int offset, int length) throws IOException {
        return 0;
    }

}
