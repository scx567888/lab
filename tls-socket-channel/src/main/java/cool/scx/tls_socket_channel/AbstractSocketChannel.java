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
        this.socketChannel.bind(local);
        return this;
    }

    @Override
    public <T> SocketChannel setOption(SocketOption<T> name, T value) throws IOException {
        this.socketChannel.setOption(name, value);
        return this;
    }

    @Override
    public <T> T getOption(SocketOption<T> name) throws IOException {
        return this.socketChannel.getOption(name);
    }

    @Override
    public Set<SocketOption<?>> supportedOptions() {
        return this.socketChannel.supportedOptions();
    }

    @Override
    public SocketChannel shutdownInput() throws IOException {
        this.socketChannel.shutdownInput();
        return this;
    }

    @Override
    public SocketChannel shutdownOutput() throws IOException {
        this.socketChannel.shutdownOutput();
        return this;
    }

    @Override
    public Socket socket() {
        return this.socketChannel.socket();
    }

    @Override
    public boolean isConnected() {
        return this.socketChannel.isConnected();
    }

    @Override
    public boolean isConnectionPending() {
        return this.socketChannel.isConnectionPending();
    }

    @Override
    public boolean connect(SocketAddress remote) throws IOException {
        return this.socketChannel.connect(remote);
    }

    @Override
    public boolean finishConnect() throws IOException {
        return this.socketChannel.finishConnect();
    }

    @Override
    public SocketAddress getRemoteAddress() throws IOException {
        return this.socketChannel.getRemoteAddress();
    }

    @Override
    public SocketAddress getLocalAddress() throws IOException {
        return this.socketChannel.getLocalAddress();
    }

    @Override
    protected void implCloseSelectableChannel() throws IOException {
        this.socketChannel.close();
    }

    @Override
    protected void implConfigureBlocking(boolean block) throws IOException {
        this.socketChannel.configureBlocking(block);
    }

}
