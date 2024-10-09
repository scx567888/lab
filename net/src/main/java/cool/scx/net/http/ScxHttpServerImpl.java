package cool.scx.net.http;

import cool.scx.http.*;
import cool.scx.http.uri.ScxURI;
import cool.scx.net.*;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

import static cool.scx.net.InputStreamLineReader.readMatch;
import static java.nio.charset.StandardCharsets.UTF_8;

public class ScxHttpServerImpl implements ScxHttpServer {

    private final ScxTCPServer tcpServer;
    private final ScxHttpServerOptions options;
    private Consumer<ScxHttpServerRequest> requestHandler;
    private Consumer<ScxServerWebSocket> webSocketHandler;
    private Consumer<Throwable> errorHandler;

    public ScxHttpServerImpl(ScxHttpServerOptions options) {
        this.options=options;
        this.tcpServer = new ScxTCPServerImpl(new ScxTCPServerOptions().port(options.port()).tls((TLS) options.tls()));
        this.tcpServer.onConnect(this::listen);
    }

    private void listen(ScxTCPSocket scxTCPSocket) {
        var rawInputStream = scxTCPSocket.inputStream();
        var rawOutputStream = scxTCPSocket.outputStream();
        var inputStream = new BufferedInputStream(rawInputStream);
        var outputStream = rawOutputStream;
        try {
            System.out.println("新连接");
            while (true) {

                //读取请求行
                var requestLineByte = readMatch(inputStream, "\r\n".getBytes());
                var requestLine = new String(requestLineByte, 0, requestLineByte.length - 2, StandardCharsets.UTF_8);
                var split = requestLine.split(" ");

                var method = ScxHttpMethod.of(split[0]);
                var uri = ScxURI.of(URLDecoder.decode(split[1], UTF_8));
                var version = HttpVersion.of(split[2]);

                //读取请求头
                var headersStrByte = readMatch(inputStream, "\r\n\r\n".getBytes());
                var headersStr = new String(headersStrByte, 0, headersStrByte.length - 4, StandardCharsets.UTF_8);

                var headers = ScxHttpHeaders.of(headersStr);


                InputStream nowRequestInputStream = inputStream;

                if (headers.contentLength() == null) {
                    nowRequestInputStream = new ByteArrayInputStream(new byte[0]);
                }

                var request = new ScxHttpServerRequestImpl(method, uri, version, headers, nowRequestInputStream, outputStream);

                requestHandler.accept(request);

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ScxHttpServer requestHandler(Consumer<ScxHttpServerRequest> handler) {
        this.requestHandler = handler;
        return this;
    }

    @Override
    public ScxHttpServer webSocketHandler(Consumer<ScxServerWebSocket> handler) {
        this.webSocketHandler = handler;
        return this;
    }

    @Override
    public ScxHttpServer errorHandler(Consumer<Throwable> handler) {
        this.errorHandler = handler;
        return this;
    }

    @Override
    public void start() {
        tcpServer.start();
    }

    @Override
    public void stop() {
        tcpServer.stop();
    }

    @Override
    public int port() {
        return tcpServer.port();
    }

}
