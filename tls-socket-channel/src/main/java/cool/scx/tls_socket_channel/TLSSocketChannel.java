package cool.scx.tls_socket_channel;

import cool.scx.net.tls.TLS;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

//todo 待完成
public class TLSSocketChannel extends AbstractSocketChannel {

    private final SSLEngine sslEngine;
    private final ByteBuffer encryptedBuffer;

    protected TLSSocketChannel(TLS tls, boolean useClientMode, SocketChannel socketChannel) {
        super(socketChannel);
        //初始化 ssl 引擎
        this.sslEngine = tls.sslContext().createSSLEngine();
        this.sslEngine.setUseClientMode(useClientMode);
        //初始化 buffer
        this.encryptedBuffer = ByteBuffer.allocate(sslEngine.getSession().getPacketBufferSize());
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
        int bytesEncrypted = 0; // 初始化加密字节计数
        var encryptedBuffer = this.encryptedBuffer;
        while (src.hasRemaining()) {
            encryptedBuffer.clear();
            SSLEngineResult result = sslEngine.wrap(src, encryptedBuffer);
            bytesEncrypted += result.bytesConsumed(); // 累积加密的数据量

            switch (result.getStatus()) {
                case OK -> {
                    encryptedBuffer.flip();
                    while (encryptedBuffer.hasRemaining()) {
                        socketChannel.write(encryptedBuffer);
                    }
                }
                // 扩展缓冲区大小 尽管 在合理设置缓冲区大小的情况下，不应该发生 BUFFER_OVERFLOW
                case BUFFER_OVERFLOW -> encryptedBuffer = ByteBuffer.allocate(encryptedBuffer.capacity() * 2);
                case BUFFER_UNDERFLOW -> throw new SSLException("Buffer underflow during TLS write, unexpected state.");
                case CLOSED -> throw new IOException("SSLEngine closed during write");
            }
        }
        return bytesEncrypted;
    }

    @Override
    public long write(ByteBuffer[] srcs, int offset, int length) throws IOException {
        long totalBytesEncrypted = 0;
        for (int i = offset; i < offset + length; i++) {
            while (srcs[i].hasRemaining()) {
                totalBytesEncrypted += write(srcs[i]);
            }
        }
        return totalBytesEncrypted;
    }

}
