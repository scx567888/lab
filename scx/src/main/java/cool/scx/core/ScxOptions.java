package cool.scx.core;

import io.vertx.core.VertxOptions;

public final class ScxOptions {

    private final VertxOptions vertxOptions;

    public ScxOptions() {
        this.vertxOptions = new VertxOptions();
    }

    public VertxOptions vertxOptions() {
        return vertxOptions;
    }

}
