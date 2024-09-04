package cool.scx.core.impl;

import cool.scx.core.Scx;
import cool.scx.core.ScxHttpServer;
import cool.scx.core.ScxHttpServerOptions;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.impl.HttpServerImpl;
import io.vertx.core.impl.VertxInternal;

public class ScxHttpServerImpl implements ScxHttpServer {

    private final HttpServer _vertxHttpServer;
    private final Scx scx;

    public ScxHttpServerImpl(ScxImpl scx, ScxHttpServerOptions options) {
        this.scx = scx;
        this._vertxHttpServer = new HttpServerImpl((VertxInternal) scx._vertx(), options._vertxHttpServerOptions());
    }

    @Override
    public Scx scx() {
        return scx;
    }

    @Override
    public ScxHttpServer requestHandler(Handler<HttpServerRequest> handler) {
        _vertxHttpServer.requestHandler(h -> Thread.ofVirtual().name("scx-http-server-request-handler").start(() -> handler.handle(h)));
        return this;
    }

    @Override
    public Future<HttpServer> listen(int port) {
        return _vertxHttpServer.listen(port);
    }

    @Override
    public HttpServer _vertxHttpServer() {
        return _vertxHttpServer;
    }

}
