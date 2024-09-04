package cool.scx.core;

import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;

public interface ScxHttpServer {
    
    Scx scx();

    ScxHttpServer requestHandler(Handler<HttpServerRequest> handler);

    Future<HttpServer> listen(int port);

    HttpServer _vertxHttpServer();

}
