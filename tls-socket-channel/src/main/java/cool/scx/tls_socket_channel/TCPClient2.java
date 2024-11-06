package cool.scx.tls_socket_channel;

import cool.scx.net.ScxTCPClient;
import cool.scx.net.ScxTCPClientOptions;
import cool.scx.net.ScxTCPSocket;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

import static cool.scx.tls_socket_channel.Helper.createSocketChannel;
import static java.lang.System.Logger.Level.ERROR;

public class TCPClient2 implements ScxTCPClient {

    private static final System.Logger logger = System.getLogger(TCPClient2.class.getName());

    private final ScxTCPClientOptions options;

    public TCPClient2() {
        this(new ScxTCPClientOptions());
    }

    public TCPClient2(ScxTCPClientOptions options) {
        this.options = options;
    }

    @Override
    public ScxTCPSocket connect(SocketAddress endpoint) {

        var tls = options.tls();

        //todo 处理代理
        var proxy = options.proxy();

        SocketChannel socketChannel;
        try {

            if (tls != null && tls.enabled()) {
                socketChannel = createSocketChannel(tls);
            } else {
                socketChannel = createSocketChannel();
            }

            socketChannel.connect(endpoint);

            //主动调用握手 快速检测 ssl 错误 防止等到调用用户处理程序时才发现 
            if (socketChannel instanceof TLSSocketChannel sslSocket) {
                try {
                    sslSocket.startHandshake();
                } catch (IOException e) {
                    logger.log(ERROR, "SSL 握手失败 : " + e.getMessage());
                    try {
                        sslSocket.close();
                    } catch (IOException ce) {
                        e.addSuppressed(ce);
                    }
                    throw e;
                }
            }

        } catch (IOException e) {
            logger.log(ERROR, "连接失败 : ", e);
            throw new UncheckedIOException(e);
        }

        return new TCPSocket2(socketChannel);

    }

}
