package cool.scx.tls_socket_channel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Set;

public abstract class AbstractServerSocketChannel extends ServerSocketChannel {

    protected final ServerSocketChannel serverSocketChannel;

    protected AbstractServerSocketChannel() throws IOException {
        super(SelectorProvider.provider());
        this.serverSocketChannel = ServerSocketChannel.open();
    }

    @Override
    public ServerSocketChannel bind(SocketAddress local, int backlog) throws IOException {
        this.serverSocketChannel.bind(local, backlog);
        return this;
    }

    @Override
    public <T> ServerSocketChannel setOption(SocketOption<T> name, T value) throws IOException {
        this.serverSocketChannel.setOption(name, value);
        return this;
    }

    @Override
    public <T> T getOption(SocketOption<T> name) throws IOException {
        return this.serverSocketChannel.getOption(name);
    }

    @Override
    public Set<SocketOption<?>> supportedOptions() {
        return this.serverSocketChannel.supportedOptions();
    }

    @Override
    public ServerSocket socket() {
        return this.serverSocketChannel.socket();
    }

    @Override
    public SocketAddress getLocalAddress() throws IOException {
        return serverSocketChannel.getLocalAddress();
    }

    @Override
    protected void implCloseSelectableChannel() throws IOException {
        serverSocketChannel.close();
    }

    @Override
    protected void implConfigureBlocking(boolean block) throws IOException {
        serverSocketChannel.configureBlocking(block);
    }

}
