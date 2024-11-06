package cool.scx.tls_socket_channel;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

public class InputStreamWrapper extends InputStream {

    private final ReadableByteChannel channel;

    public InputStreamWrapper(ReadableByteChannel channel) {
        this.channel = channel;
    }

    @Override
    public int read() throws IOException {
        var buffer = ByteBuffer.wrap(new byte[]{0});
        int read = channel.read(buffer);
        if (read == -1) {
            return -1;
        }
        return buffer.array()[0] & 0xFF;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        var buffer = ByteBuffer.wrap(b, off, len);
        return channel.read(buffer);
    }

}
