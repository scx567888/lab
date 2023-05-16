package cool.scx.lab.djc;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public final class DynamicClassLoader extends ClassLoader {

    private final Map<String, MemoryJavaFileObject> classObjects = new HashMap<>();
    private final Map<String, Class<?>> fakeClassMap = new HashMap<>();

    public void addFakeClass(String className, Class<?> fakeClass) {

        try {
            Class<? extends Class> aClass = fakeClass.getClass();

            Field name = aClass.getDeclaredField("name");
            name.setAccessible(true);
            name.set(fakeClass,className);
            fakeClassMap.put(className, fakeClass);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    public DynamicClassLoader(ClassLoader parent) {
        super(parent);
    }

    public void addClassObject(MemoryJavaFileObject fo) {
        classObjects.put(fo.getName(), fo);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        var classObject = classObjects.get(name);
        if (classObject == null) {
            return super.findClass(name);
        }
        var byteCode = classObject.getBytes();
        return super.defineClass(name, byteCode, 0, byteCode.length);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        var fakeClass = fakeClassMap.get(name);
        if (fakeClass != null) {
            return fakeClass;
        }
        return super.loadClass(name, resolve);
    }

}
