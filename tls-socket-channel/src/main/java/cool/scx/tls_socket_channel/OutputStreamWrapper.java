package cool.scx.tls_socket_channel;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class OutputStreamWrapper extends OutputStream {

    private final SocketChannel socketChannel;

    public OutputStreamWrapper(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void write(int b) throws IOException {
        write(new byte[]{(byte) b}, 0, 1);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        var buffer = ByteBuffer.wrap(b, off, len);
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
    }

}
