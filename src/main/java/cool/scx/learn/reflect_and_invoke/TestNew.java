package cool.scx.learn.reflect_and_invoke;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;

public class TestNew {

    public String someField;

    public String someField() {
        return someField;
    }

    static final Constructor<TestNew> constructor = initConstructor();
    static final MethodHandle mh = initMH();

    private static MethodHandle initMH() {
        try {
            return MethodHandles.lookup().unreflectConstructor(constructor);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Constructor<TestNew> initConstructor() {
        Constructor<TestNew> method;
        try {
            method = TestNew.class.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        method.setAccessible(true);
        return method;
    }

    public static void main(String[] args) throws Throwable {
        System.out.println("---- normal ----");
        normal();
        System.out.println("---- reflect ----");
        reflect();
        System.out.println("---- methodHandle ----");
        methodHandle();
    }

    public static Object normal() throws Throwable {
        Object value = null;
        for (int outer = 0; outer < 30; outer++) {
            long start = System.nanoTime();
            for (int i = 0; i < 100000000; i++) {
                value = new TestNew();
            }
            long time = (System.nanoTime() - start) / 1000000;
            System.out.println("it took" + time + "ms");
        }
        return value;
    }

    public static Object reflect() throws Throwable {
//        ResultSet
        Object value = null;
        for (int outer = 0; outer < 30; outer++) {
            long start = System.nanoTime();
            for (int i = 0; i < 100000000; i++) {
                value = constructor.newInstance();
            }
            long time = (System.nanoTime() - start) / 1000000;
            System.out.println("it took" + time + "ms");
        }
        return value;
    }

    public static Object methodHandle() throws Throwable {
        Object value =null;
        for (int outer = 0; outer < 30; outer++) {
            long start = System.nanoTime();
            for (int i = 0; i < 100000000; i++) {
               value= mh.invokeWithArguments();
            }
            long time = (System.nanoTime() - start) / 1000000;
            System.out.println("it took" + time + "ms");
        }
        return value;
    }


}