package cool.scx.net.http;

import cool.scx.http.*;

import java.io.IOException;
import java.io.OutputStream;

import static cool.scx.http.HttpFieldName.CONNECTION;

public class ScxHttpServerResponseImpl implements ScxHttpServerResponse {

    private final ScxHttpServerRequestImpl request;
    private final OutputStream rawOutputStream;
    private final OutputStream outputStream;
    private final ScxHttpHeadersWritable headers;
    private HttpStatusCode status;
    private boolean send;

    public ScxHttpServerResponseImpl(ScxHttpServerRequestImpl request, OutputStream outputStream) {
        this.request = request;
        this.rawOutputStream = outputStream;
        this.outputStream = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                beforeSend();
                outputStream.write(b);
            }

            @Override
            public void write(byte[] b) throws IOException {
                beforeSend();
                outputStream.write(b);
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                beforeSend();
                outputStream.write(b, off, len);
            }

            @Override
            public void flush() throws IOException {
                beforeSend();
                outputStream.flush();
            }

            @Override
            public void close() throws IOException {

            }

        };
        this.status = HttpStatusCode.OK;
        this.headers = ScxHttpHeaders.of();
        this.headers.add(CONNECTION, "keep-alive");
    }

    @Override
    public ScxHttpServerRequest request() {
        return request;
    }

    @Override
    public HttpStatusCode status() {
        return status;
    }

    @Override
    public ScxHttpHeadersWritable headers() {
        return headers;
    }

    @Override
    public ScxHttpServerResponse status(HttpStatusCode code) {
        this.status = code;
        return this;
    }

    @Override
    public OutputStream outputStream() {
        return outputStream;
    }

    @Override
    public void end() {
        try {
            rawOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isClosed() {
        return false;
    }

    public void beforeSend() {
        if (send) {
            return;
        }
        doBeforeSend();
        send = true;
    }

    private void doBeforeSend() {
        var sb = new StringBuilder();
        sb.append("HTTP/1.1").append(" ").append(status.code()).append(" ").append(status.description()).append("\r\n");
        var headersStr = headers.encode();
        sb.append(headersStr);
        sb.append("\r\n");
        try {
            rawOutputStream.write(sb.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
