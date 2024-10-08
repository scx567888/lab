package cool.scx.net;

import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.function.Consumer;

import static cool.scx.net.ScxTCPServerHelper.createServerSocket;

public class ScxTCPServerImpl implements ScxTCPServer {

    private final ScxTCPServerOptions options;
    private final Thread serverThread;
    private Consumer<ScxTCPSocket> connectHandler;
    private ServerSocket serverSocket;
    private boolean running;

    public ScxTCPServerImpl() {
        this(new ScxTCPServerOptions());
    }

    public ScxTCPServerImpl(ScxTCPServerOptions options) {
        this.options = options;
        this.serverThread = Thread.ofPlatform().unstarted(this::listen);
    }

    @Override
    public ScxTCPServer onConnect(Consumer<ScxTCPSocket> connectHandler) {
        this.connectHandler = connectHandler;
        return this;
    }

    @Override
    public void start() {
        if (running) {
            throw new IllegalStateException("Server is already running");
        }

        try {
            this.serverSocket = createServerSocket(options.tls());
            this.serverSocket.bind(new InetSocketAddress(options.port()));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        running = true;

        serverThread.start();
    }

    @Override
    public void stop() {
        if (!running) {
            return;
        }

        running = false;

        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        serverThread.interrupt();
    }

    private void listen() {
        while (running) {
            try {
                var socket = this.serverSocket.accept();
                Thread.ofVirtual().start(() -> {
                    try {
                        //尝试握手
                        if (socket instanceof SSLSocket sslSocket) {
                            sslSocket.startHandshake();
                        }
                        //调用处理器
                        var tcpSocket = new ScxTCPSocketImpl(socket);
                        connectHandler.accept(tcpSocket);
                    } catch (Exception e) {
                        //暂时忽略
                    }
                });
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

}
