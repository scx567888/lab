package cool.scx.core;

import io.vertx.core.http.HttpServerOptions;

public final class ScxHttpServerOptions {
    
    private final HttpServerOptions _vertxHttpServerOptions;

    public ScxHttpServerOptions() {
        this._vertxHttpServerOptions = new HttpServerOptions();
    }

    public HttpServerOptions _vertxHttpServerOptions() {
        return _vertxHttpServerOptions;
    }
    
}
