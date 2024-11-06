package cool.scx.tls_socket_channel;

import cool.scx.net.ScxTCPSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

public class TCPSocket2 implements ScxTCPSocket {

    private final SocketChannel socketChannel;
    private final InputStream in;
    private final OutputStream out;

    public TCPSocket2(SocketChannel socket) {
        this.socketChannel = socket;
        this.in = new InputStreamWrapper(socketChannel);
        this.out = new OutputStreamWrapper(socketChannel);
    }

    @Override
    public InputStream inputStream() {
        return in;
    }

    @Override
    public OutputStream outputStream() {
        return out;
    }

    @Override
    public void close() throws IOException {
        socketChannel.close();
    }

    @Override
    public boolean isClosed() {
        return !socketChannel.isOpen();
    }

    @Override
    public SocketAddress remoteAddress() {
        try {
            return socketChannel.getRemoteAddress();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
