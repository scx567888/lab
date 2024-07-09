package cool.scx.core;

import cool.scx.core.impl.ScxImpl;
import io.vertx.core.Vertx;

public interface Scx {

    static Scx scx() {
        return scx(new ScxOptions());
    }

    static Scx scx(ScxOptions options) {
        return new ScxImpl(options);
    }

    default ScxHttpServer createHttpServer() {
        return createHttpServer(new ScxHttpServerOptions());
    }

    ScxHttpServer createHttpServer(ScxHttpServerOptions options);

    Vertx vertx();

}
