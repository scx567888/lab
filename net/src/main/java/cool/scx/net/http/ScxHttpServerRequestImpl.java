package cool.scx.net.http;

import cool.scx.http.*;
import cool.scx.http.uri.ScxURI;
import cool.scx.http.uri.ScxURIWritable;

import java.io.InputStream;
import java.io.OutputStream;

public class ScxHttpServerRequestImpl implements ScxHttpServerRequest {

    private final ScxHttpMethod method;
    private final ScxURIWritable uri;
    private final HttpVersion version;
    private final ScxHttpHeadersWritable headers;
    private final InputStream inputStream;
    private final ScxHttpBodyImpl body;
    private final ScxHttpServerResponseImpl response;

    public ScxHttpServerRequestImpl(ScxHttpMethod method, ScxURIWritable uri, HttpVersion version, ScxHttpHeadersWritable headers, InputStream inputStream, OutputStream outputStream) {
        this.method = method;
        this.uri = uri;
        this.version = version;
        this.headers = headers;
        this.inputStream = inputStream;
        this.body = new ScxHttpBodyImpl(inputStream, headers, 1024 * 8);
        this.response = new ScxHttpServerResponseImpl(this, outputStream);
    }

    @Override
    public ScxHttpServerResponse response() {
        return response;
    }

    @Override
    public ScxHttpMethod method() {
        return method;
    }

    @Override
    public ScxURI uri() {
        return uri;
    }

    @Override
    public HttpVersion version() {
        return version;
    }

    @Override
    public ScxHttpHeaders headers() {
        return headers;
    }

    @Override
    public ScxHttpBody body() {
        return body;
    }

    @Override
    public PeerInfo remotePeer() {
        return null;
    }

    @Override
    public PeerInfo localPeer() {
        return null;
    }

}
