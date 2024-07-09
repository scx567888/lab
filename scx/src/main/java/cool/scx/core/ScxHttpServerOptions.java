package cool.scx.core;

import io.vertx.core.http.HttpServerOptions;

public final class ScxHttpServerOptions {
    
    private final HttpServerOptions vertxHttpServerOptions;

    public ScxHttpServerOptions() {
        this.vertxHttpServerOptions = new HttpServerOptions();
    }

    public HttpServerOptions vertxHttpServerOptions() {
        return vertxHttpServerOptions;
    }
    
}
