package cool.scx.lab.djc;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

import static javax.tools.JavaFileObject.Kind.*;

public final class SourceCode extends SimpleJavaFileObject {

    private final String src;

    private final String className;

    public SourceCode(String className, String src) {
        super(URI.create("string:///" + className.replace('.', '/') + SOURCE.extension), SOURCE);
        this.className = className;
        this.src = src;
    }

    @Override
    public String getName() {
        return className;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return src;
    }

}
