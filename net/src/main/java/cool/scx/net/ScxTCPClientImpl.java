package cool.scx.net;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.Socket;
import java.net.SocketAddress;

import static cool.scx.net.ScxTCPClientHelper.createSocket;

public class ScxTCPClientImpl implements ScxTCPClient {

    private final ScxTCPClientOptions options;

    public ScxTCPClientImpl() {
        this(new ScxTCPClientOptions());
    }

    public ScxTCPClientImpl(ScxTCPClientOptions options) {
        this.options = options;
    }

    @Override
    public Socket connect(SocketAddress endpoint) {
        var socket = createSocket(options.tls());
        try {
            socket.connect(endpoint);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return socket;
    }

}
