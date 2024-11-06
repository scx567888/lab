package cool.scx.tls_socket_channel;

import cool.scx.net.tls.TLS;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

//todo 待完成
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
        sslEngine.beginHandshake();
        SSLEngineResult.HandshakeStatus handshakeStatus = sslEngine.getHandshakeStatus();
        ByteBuffer myNetData = ByteBuffer.allocate(sslEngine.getSession().getPacketBufferSize());
        ByteBuffer peerNetData = ByteBuffer.allocate(sslEngine.getSession().getPacketBufferSize());
        ByteBuffer peerAppData = ByteBuffer.allocate(sslEngine.getSession().getApplicationBufferSize());
        while (handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED &&
                handshakeStatus != SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING) {
            switch (handshakeStatus) {
                case NEED_UNWRAP:
                    if (socketChannel.read(peerNetData) < 0) {
                        throw new IOException("Failed to read data during TLS handshake.");
                    }
                    peerNetData.flip();
                    SSLEngineResult unwrapResult = sslEngine.unwrap(peerNetData, peerAppData);
                    peerNetData.compact();
                    handshakeStatus = unwrapResult.getHandshakeStatus();
                    break;
                case NEED_WRAP:
                    myNetData.clear();
                    SSLEngineResult wrapResult = sslEngine.wrap(ByteBuffer.allocate(0), myNetData);
                    handshakeStatus = wrapResult.getHandshakeStatus();
                    myNetData.flip();
                    while (myNetData.hasRemaining()) {
                        socketChannel.write(myNetData);
                    }
                    break;
                case NEED_TASK:
                    Runnable task;
                    while ((task = sslEngine.getDelegatedTask()) != null) {
                        task.run();
                    }
                    handshakeStatus = sslEngine.getHandshakeStatus();
                    break;
                default:
                    throw new IllegalStateException("Unexpected handshake status: " + handshakeStatus);
            }
        }
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
