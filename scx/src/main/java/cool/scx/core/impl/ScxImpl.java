package cool.scx.core.impl;

import cool.scx.core.Scx;
import cool.scx.core.ScxHttpServer;
import cool.scx.core.ScxHttpServerOptions;
import cool.scx.core.ScxOptions;
import io.vertx.core.Vertx;

public class ScxImpl implements Scx {

    private final Vertx vertx;

    public ScxImpl(ScxOptions options) {
        this.vertx = Vertx.vertx(options.vertxOptions());
    }

    @Override
    public ScxHttpServer createHttpServer(ScxHttpServerOptions options) {
        return new ScxHttpServerImpl(this, options);
    }

    @Override
    public Vertx vertx() {
        return vertx;
    }

}
