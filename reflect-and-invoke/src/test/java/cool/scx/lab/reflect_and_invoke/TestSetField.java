package cool.scx.lab.reflect_and_invoke;

import cool.scx.lab.reflect_and_invoke.bean.User;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.function.BiConsumer;

public class TestSetField {

    static final Field field = initField();
    static final MethodHandle mh = initMH();
    static final BiConsumer<Object, Object> c = ObjectHelper.createSetter(MethodHandles.lookup(), field);

    private static MethodHandle initMH() {
        try {
            return MethodHandles.lookup().unreflectSetter(field);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Field initField() {
        Field field;
        try {
            field = User.class.getDeclaredField("name");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        field.setAccessible(true);
        return field;
    }

    public static void normal(User t) throws Throwable {
        System.out.println("---- normal ----");
        var log = new TestNew.Log();
        var value = "scx";
        for (int outer = 0; outer < 50; outer++) {
            long start = System.nanoTime();
            for (int i = 0; i < 100000000; i++) {
                t.name = value;
            }
            log.add((System.nanoTime() - start));
        }
        log.log();
    }

    public static void reflect(User t) throws Throwable {
        System.out.println("---- reflect ----");
        var log = new TestNew.Log();
        var value = "scx";
        for (int outer = 0; outer < 50; outer++) {
            long start = System.nanoTime();
            for (int i = 0; i < 100000000; i++) {
                field.set(t, value);
            }
            log.add((System.nanoTime() - start));
        }
        log.log();
    }

    public static void methodHandle(User t) throws Throwable {
        System.out.println("---- methodHandle ----");
        var log = new TestNew.Log();
        var value = "scx";
        for (int outer = 0; outer < 50; outer++) {
            long start = System.nanoTime();
            for (int i = 0; i < 100000000; i++) {
                mh.invokeExact(t, value);
            }
            log.add((System.nanoTime() - start));
        }
        log.log();
    }

    public static void dynamicLambda(User t) throws Throwable {
        System.out.println("---- dynamicLambda ----");
        var log = new TestNew.Log();
        var value = "scx";
        for (int outer = 0; outer < 50; outer++) {
            long start = System.nanoTime();
            for (int i = 0; i < 100000000; i++) {
                c.accept(t, value);
            }
            log.add((System.nanoTime() - start));
        }
        log.log();
    }

    public static void main(String[] args) throws Throwable {
        var t = new User();
        normal(t);
        reflect(t);
        methodHandle(t);
        dynamicLambda(t);
    }

}
