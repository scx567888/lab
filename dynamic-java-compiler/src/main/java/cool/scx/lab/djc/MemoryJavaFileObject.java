package cool.scx.lab.djc;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

import static javax.tools.JavaFileObject.Kind.CLASS;

public final class MemoryJavaFileObject extends SimpleJavaFileObject {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    private final String className;

    public MemoryJavaFileObject(String className) {
        super(URI.create(className), CLASS);
        this.className = className;
    }

    @Override
    public String getName() {
        return className;
    }

    @Override
    public OutputStream openOutputStream() {
        return outputStream;
    }

    public byte[] getBytes() {
        return outputStream.toByteArray();
    }

}
