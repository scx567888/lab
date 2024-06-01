package cool.scx.lab.djc;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.util.ArrayList;
import java.util.List;

public final class DynamicJavaCompiler {

    private static final JavaCompiler javac;

    static {
        javac = ToolProvider.getSystemJavaCompiler();
    }

    public static Class<?> compile(String className, String sourceCode) throws ClassNotFoundException {
        var classList = compile(List.of(new SourceCode(className, sourceCode)));
        for (var c : classList) {
            if (c.getName().equals(className)) {
                return c;
            }
        }
        throw new RuntimeException();
    }

    public static List<Class<?>> compile(SourceCode sourceCode, SourceCode... sourceCodes) throws ClassNotFoundException {
        var list = new ArrayList<SourceCode>();
        list.add(sourceCode);
        list.addAll(List.of(sourceCodes));
        return compile(list);
    }

    public static List<Class<?>> compile(Iterable<? extends JavaFileObject> sourceCodes) throws ClassNotFoundException {

        var fileManager = new MemoryFileManager(
                javac.getStandardFileManager(null, null, null),
                getDynamicClassLoader());

        var task = javac.getTask(null, fileManager, null, null, null, sourceCodes);

        task.call();

        var list = new ArrayList<Class<?>>();
        for (var name : sourceCodes) {
            list.add(fileManager.getClassLoader(null).loadClass(name.getName()));
        }
        return list;
    }

    public static DynamicClassLoader getDynamicClassLoader() {
        var classLoader = new DynamicClassLoader(ClassLoader.getSystemClassLoader());
//        classLoader.addFakeClass("java.lang.System", System.class);
        return classLoader;
    }

}
