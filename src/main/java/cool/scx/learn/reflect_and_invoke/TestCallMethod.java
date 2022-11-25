package cool.scx.learn.reflect_and_invoke;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

class TestCallMethod {

    public String someField;

    public String someField() {
        return someField;
    }

    static final Method method = initMethod();
    static final MethodHandle mh = initMH();

    private static MethodHandle initMH() {
        try {
            return MethodHandles.lookup().unreflect(method);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Method initMethod() {
        Method method;
        try {
            method = TestCallMethod.class.getMethod("someField");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        method.setAccessible(true);
        return method;
    }

    public static void main(String[] args) throws Throwable {
        var t = new TestCallMethod();
        t.someField = "名字";
        mh.bindTo(t);
        System.out.println("---- normal ----");
        normal(t);
        System.out.println("---- reflect ----");
        reflect(t);
        System.out.println("---- methodHandle ----");
        methodHandle(t);
    }

    public static Object normal(TestCallMethod t) throws Throwable {
        Object value = null;
        for (int outer = 0; outer < 30; outer++) {
            long start = System.nanoTime();
            for (int i = 0; i < 100000000; i++) {
                value = t.someField();
            }
            long time = (System.nanoTime() - start) / 1000000;
            System.out.println("it took" + time + "ms");
        }
        return value;
    }

    public static Object reflect(TestCallMethod t) throws Throwable {
        Object value = null;
        for (int outer = 0; outer < 30; outer++) {
            long start = System.nanoTime();
            for (int i = 0; i < 100000000; i++) {
                value = method.invoke(t);
            }
            long time = (System.nanoTime() - start) / 1000000;
            System.out.println("it took" + time + "ms");
        }
        return value;
    }

    public static Object methodHandle(TestCallMethod t) throws Throwable {
        Object value = null;
        for (int outer = 0; outer < 30; outer++) {
            long start = System.nanoTime();
            for (int i = 0; i < 100000000; i++) {
                value = mh.invoke(t);
            }
            long time = (System.nanoTime() - start) / 1000000;
            System.out.println("it took" + time + "ms");
        }
        return value;
    }

}