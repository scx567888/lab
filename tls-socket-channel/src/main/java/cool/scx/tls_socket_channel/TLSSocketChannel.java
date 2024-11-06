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
    private final ByteBuffer networkDataBuffer;
    private final ByteBuffer applicationDataBuffer;

    protected TLSSocketChannel(TLS tls, boolean useClientMode, SocketChannel socketChannel) {
        super(socketChannel);
        // 初始化 ssl 引擎
        this.sslEngine = tls.sslContext().createSSLEngine();
        this.sslEngine.setUseClientMode(useClientMode);
        // 初始化缓冲区
        this.encryptedBuffer = ByteBuffer.allocate(sslEngine.getSession().getPacketBufferSize());
        this.networkDataBuffer = ByteBuffer.allocate(sslEngine.getSession().getPacketBufferSize());
        this.applicationDataBuffer = ByteBuffer.allocate(sslEngine.getSession().getApplicationBufferSize());
    }

    protected TLSSocketChannel(TLS tls, boolean useClientMode) throws IOException {
        this(tls, useClientMode, SocketChannel.open());
    }

    public void startHandshake() throws IOException {
        sslEngine.beginHandshake();
        SSLEngineResult.HandshakeStatus handshakeStatus = sslEngine.getHandshakeStatus();

        while (handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED &&
                handshakeStatus != SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING) {
            switch (handshakeStatus) {
                case NEED_UNWRAP:
                    if (socketChannel.read(networkDataBuffer) < 0) {
                        throw new IOException("Failed to read data during TLS handshake.");
                    }
                    networkDataBuffer.flip();
                    SSLEngineResult unwrapResult = sslEngine.unwrap(networkDataBuffer, applicationDataBuffer);
                    networkDataBuffer.compact();
                    handshakeStatus = unwrapResult.getHandshakeStatus();
                    break;
                case NEED_WRAP:
                    encryptedBuffer.clear();
                    SSLEngineResult wrapResult = sslEngine.wrap(ByteBuffer.allocate(0), encryptedBuffer);
                    handshakeStatus = wrapResult.getHandshakeStatus();
                    encryptedBuffer.flip();
                    while (encryptedBuffer.hasRemaining()) {
                        socketChannel.write(encryptedBuffer);
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
        int bytesRead = 0; // 初始化读取字节数

        // 从通道读取加密数据到 networkDataBuffer
        if (socketChannel.read(networkDataBuffer) < 0) {
            return -1; // 通道已关闭
        }
        networkDataBuffer.flip(); // 准备解密

        while (networkDataBuffer.hasRemaining()) {
            SSLEngineResult result = sslEngine.unwrap(networkDataBuffer, applicationDataBuffer);
            switch (result.getStatus()) {
                case OK -> {
                    applicationDataBuffer.flip();
                    int limit = Math.min(applicationDataBuffer.remaining(), dst.remaining());
                    ByteBuffer tempBuffer = applicationDataBuffer.duplicate(); // 创建临时缓冲区用于批量传输
                    tempBuffer.limit(tempBuffer.position() + limit);
                    dst.put(tempBuffer);
                    applicationDataBuffer.position(applicationDataBuffer.position() + limit);
                    bytesRead += limit;
                    applicationDataBuffer.compact();
                }
                case BUFFER_OVERFLOW -> {
                    // 扩展目标缓冲区大小
                    ByteBuffer newBuffer = ByteBuffer.allocate(dst.capacity() * 2);
                    dst.flip();
                    newBuffer.put(dst);
                    dst = newBuffer;
                }
                case BUFFER_UNDERFLOW -> {
                    // 调整网络数据缓冲区大小使用临时缓冲区
                    ByteBuffer newNetDataBuffer = ByteBuffer.allocate(networkDataBuffer.capacity() * 2);
                    networkDataBuffer.flip();
                    newNetDataBuffer.put(networkDataBuffer);
                    networkDataBuffer.clear();
                    networkDataBuffer.put(newNetDataBuffer);
                    if (socketChannel.read(networkDataBuffer) < 0) {
                        return -1; // 通道已关闭
                    }
                    networkDataBuffer.flip();
                }
                case CLOSED -> {
                    sslEngine.closeOutbound();
                    return bytesRead;
                }
                default -> throw new IllegalStateException("Unexpected SSLEngine result status: " + result.getStatus());
            }
        }
        return bytesRead;
    }


    @Override
    public long read(ByteBuffer[] dsts, int offset, int length) throws IOException {
        long totalBytesRead = 0;
        for (int i = offset; i < offset + length; i++) {
            totalBytesRead += read(dsts[i]);
        }
        return totalBytesRead;
    }

    @Override
    public int write(ByteBuffer src) throws IOException {
        int bytesEncrypted = 0; // 初始化加密字节计数
        var tempBuffer = this.encryptedBuffer;
        while (src.hasRemaining()) {
            tempBuffer.clear();
            var result = sslEngine.wrap(src, tempBuffer);
            bytesEncrypted += result.bytesConsumed(); // 累积加密的数据量

            switch (result.getStatus()) {
                case OK -> {
                    tempBuffer.flip();
                    while (tempBuffer.hasRemaining()) {
                        socketChannel.write(tempBuffer);
                    }
                }
                // 扩展缓冲区大小 尽管 在合理设置缓冲区大小的情况下，不应该发生 BUFFER_OVERFLOW
                case BUFFER_OVERFLOW -> {
                    tempBuffer = ByteBuffer.allocate(tempBuffer.capacity() * 2);
                }
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
