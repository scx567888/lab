package cool.scx.tls_socket_channel;

import cool.scx.net.tls.TLS;

import javax.net.ssl.SSLEngine;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TLSSocketChannel extends AbstractSocketChannel {

    private final SSLEngine sslEngine;

    protected TLSSocketChannel(TLS tls, boolean useClientMode, SocketChannel socketChannel) {
        super(socketChannel);
        this.sslEngine = tls.sslContext().createSSLEngine();
        this.sslEngine.setUseClientMode(useClientMode);
    }

    protected TLSSocketChannel(TLS tls, boolean useClientMode) throws IOException {
        this(tls, useClientMode, SocketChannel.open());
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
