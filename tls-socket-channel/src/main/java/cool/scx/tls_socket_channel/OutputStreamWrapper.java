package cool.scx.tls_socket_channel;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

public class OutputStreamWrapper extends OutputStream {

    private final WritableByteChannel channel;

    public OutputStreamWrapper(WritableByteChannel channel) {
        this.channel = channel;
    }

    @Override
    public void write(int b) throws IOException {
        write(new byte[]{(byte) b});
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        var buffer = ByteBuffer.wrap(b, off, len);
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
    }

}
