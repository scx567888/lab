package cool.scx.lab.reflect_and_invoke;

import cool.scx.lab.reflect_and_invoke.bean.User;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

/**
 * 可以看到 ,在 jdk-18 以后 反射的性能已经相当高了
 */
public class TestCallMethod {

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
            method = User.class.getMethod("name");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        method.setAccessible(true);
        return method;
    }

    public static Object normal(User t) throws Throwable {
        System.out.println("---- normal ----");
        var log = new TestNew.Log();
        Object value = null;
        for (int outer = 0; outer < 50; outer++) {
            long start = System.nanoTime();
            for (int i = 0; i < 100000000; i++) {
                value = t.name();
            }
            log.add((System.nanoTime() - start));
        }
        log.log();
        return value;
    }

    public static Object reflect(User t) throws Throwable {
        System.out.println("---- reflect ----");
        var log = new TestNew.Log();
        Object value = null;
        for (int outer = 0; outer < 50; outer++) {
            long start = System.nanoTime();
            for (int i = 0; i < 100000000; i++) {
                value = method.invoke(t);
            }
            log.add((System.nanoTime() - start));
        }
        log.log();
        return value;
    }

    public static Object methodHandle(User t) throws Throwable {
        System.out.println("---- methodHandle ----");
        var log = new TestNew.Log();
        Object value = null;
        for (int outer = 0; outer < 50; outer++) {
            long start = System.nanoTime();
            for (int i = 0; i < 100000000; i++) {
                value = mh.invoke(t);
            }
            log.add((System.nanoTime() - start));
        }
        log.log();
        return value;
    }


    public static void main(String[] args) throws Throwable {
        var t = new User();
        t.name = "scx";
        normal(t);
        reflect(t);
        methodHandle(t);
    }

}
