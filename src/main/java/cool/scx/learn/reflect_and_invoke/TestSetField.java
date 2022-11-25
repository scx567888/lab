package cool.scx.learn.reflect_and_invoke;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.function.BiConsumer;

class TestSetField {
    public Object someField;
    static final Field field = initField();
    static final MethodHandle mh = initMH();
    static final BiConsumer<Object, Object> mm = ObjectHelper.createSetter(MethodHandles.lookup(), field);
    static final BiConsumer<Object, Object> m = (a, b) -> {
        try {
            mh.invoke(a, b);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    };

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
            field = TestSetField.class.getDeclaredField("someField");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        field.setAccessible(true);
        return field;
    }

    public static void main(String[] args) throws Throwable {
        var t = new TestSetField();
        System.out.println("---- normal ----");
        normal(t);
        System.out.println("---- reflect ----");
        reflect(t);
        System.out.println("---- methodHandle ----");
        methodHandle(t);
        System.out.println("---- methodHandle1 ----");
        methodHandle1(t);
        System.out.println("---- methodHandle1 ----");
        methodHandle2(t);
    }

    public static void normal(TestSetField t) throws Throwable {
        Object value = new Object();
        for (int outer = 0; outer < 30; outer++) {
            long start = System.nanoTime();
            for (int i = 0; i < 100000000; i++) {
                t.someField = value;
            }
            long time = (System.nanoTime() - start) / 1000000;
            System.out.println("it took" + time + "ms");
        }
    }

    public static void reflect(TestSetField t) throws Throwable {
        Object value = new Object();
        for (int outer = 0; outer < 30; outer++) {
            long start = System.nanoTime();
            for (int i = 0; i < 100000000; i++) {
                field.set(t, value);
            }
            long time = (System.nanoTime() - start) / 1000000;
            System.out.println("it took" + time + "ms");
        }
    }

    public static void methodHandle(TestSetField t) throws Throwable {
        Object value = new Object();
        for (int outer = 0; outer < 30; outer++) {
            long start = System.nanoTime();
            for (int i = 0; i < 100000000; i++) {
                mh.invokeExact(t, value);
            }
            long time = (System.nanoTime() - start) / 1000000;
            System.out.println("it took" + time + "ms");
        }
    }

    public static void methodHandle1(TestSetField t) throws Throwable {
        Object value = new Object();
        for (int outer = 0; outer < 30; outer++) {
            long start = System.nanoTime();
            for (int i = 0; i < 100000000; i++) {
                mm.accept(t, value);
            }
            long time = (System.nanoTime() - start) / 1000000;
            System.out.println("it took" + time + "ms");
        }
    }

    public static void methodHandle2(TestSetField t) throws Throwable {
        Object value = new Object();
        for (int outer = 0; outer < 30; outer++) {
            long start = System.nanoTime();
            for (int i = 0; i < 100000000; i++) {
                m.accept(t, value);
            }
            long time = (System.nanoTime() - start) / 1000000;
            System.out.println("it took" + time + "ms");
        }
    }

}