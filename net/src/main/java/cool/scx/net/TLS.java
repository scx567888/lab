package cool.scx.net;

import java.nio.file.Path;

public class TLS {

    private boolean enabled;
    private Path path;
    private String password;

    public TLS() {
        this.enabled = true;
        this.path = null;
        this.password = null;
    }

    public boolean enabled() {
        return enabled;
    }

    public TLS enabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public Path path() {
        return path;
    }

    public TLS path(Path path) {
        this.path = path;
        return this;
    }

    public String password() {
        return password;
    }

    public TLS password(String password) {
        this.password = password;
        return this;
    }

}
