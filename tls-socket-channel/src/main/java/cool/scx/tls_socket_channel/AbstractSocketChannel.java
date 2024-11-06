package cool.scx.tls_socket_channel;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Set;

public abstract class AbstractSocketChannel extends SocketChannel {

    protected final SocketChannel socketChannel;

    protected AbstractSocketChannel(SocketChannel socketChannel) {
        super(SelectorProvider.provider());
        this.socketChannel = socketChannel;
    }

    @Override
    public SocketChannel bind(SocketAddress local) throws IOException {
        socketChannel.bind(local);
        return this;
    }

    @Override
    public <T> SocketChannel setOption(SocketOption<T> name, T value) throws IOException {
        socketChannel.setOption(name, value);
        return this;
    }

    @Override
    public <T> T getOption(SocketOption<T> name) throws IOException {
        return socketChannel.getOption(name);
    }

    @Override
    public Set<SocketOption<?>> supportedOptions() {
        return socketChannel.supportedOptions();
    }

    @Override
    public SocketChannel shutdownInput() throws IOException {
        socketChannel.shutdownInput();
        return this;
    }

    @Override
    public SocketChannel shutdownOutput() throws IOException {
        socketChannel.shutdownOutput();
        return this;
    }

    @Override
    public Socket socket() {
        return socketChannel.socket();
    }

    @Override
    public boolean isConnected() {
        return socketChannel.isConnected();
    }

    @Override
    public boolean isConnectionPending() {
        return socketChannel.isConnectionPending();
    }

    @Override
    public boolean connect(SocketAddress remote) throws IOException {
        return socketChannel.connect(remote);
    }

    @Override
    public boolean finishConnect() throws IOException {
        return socketChannel.finishConnect();
    }

    @Override
    public SocketAddress getRemoteAddress() throws IOException {
        return socketChannel.getRemoteAddress();
    }

    @Override
    public SocketAddress getLocalAddress() throws IOException {
        return socketChannel.getLocalAddress();
    }

    @Override
    protected void implCloseSelectableChannel() throws IOException {
        socketChannel.close();
    }

    @Override
    protected void implConfigureBlocking(boolean block) throws IOException {
        socketChannel.configureBlocking(block);
    }

}
