package cool.scx.core;

import io.vertx.core.VertxOptions;

public final class ScxOptions {

    private final VertxOptions _vertxOptions;

    public ScxOptions() {
        this._vertxOptions = new VertxOptions();
    }

    public VertxOptions _vertxOptions() {
        return _vertxOptions;
    }

}
