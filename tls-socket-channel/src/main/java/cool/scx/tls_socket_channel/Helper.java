package cool.scx.tls_socket_channel;

import cool.scx.net.tls.TLS;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Helper {

    public static ServerSocketChannel createServerSocketChannel() throws IOException {
        return ServerSocketChannel.open();
    }

    public static ServerSocketChannel createServerSocketChannel(TLS tls) throws IOException {
        return new TLSServerSocketChannel(tls);
    }

    public static SocketChannel createSocketChannel() throws IOException {
        return SocketChannel.open();
    }

    public static SocketChannel createSocketChannel(TLS tls) throws IOException {
        return new TLSSocketChannel(tls);
    }

}
