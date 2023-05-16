package cool.scx.lab.reflect_and_invoke;

import cool.scx.lab.reflect_and_invoke.bean.User;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class TestNew {

    static final Constructor<User> constructor = initConstructor();

    static final MethodHandle mh = initMH();

    private static MethodHandle initMH() {
        try {
            return MethodHandles.lookup().unreflectConstructor(constructor);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Constructor<User> initConstructor() {
        Constructor<User> method;
        try {
            method = User.class.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        method.setAccessible(true);
        return method;
    }

    @Test
    public static Object normal() throws Throwable {
        System.out.println("---- normal ----");
        var log = new Log();
        Object value = null;
        for (int outer = 0; outer < 50; outer++) {
            long start = System.nanoTime();
            for (int i = 0; i < 100000000; i++) {
                value = new User();
            }
            log.add((System.nanoTime() - start));
        }
        log.log();
        return value;
    }

    @Test
    public static Object reflect() throws Throwable {
        System.out.println("---- reflect ----");
        var log = new Log();
        Object value = null;
        for (int outer = 0; outer < 50; outer++) {
            long start = System.nanoTime();
            for (int i = 0; i < 100000000; i++) {
                value = constructor.newInstance();
            }
            log.add((System.nanoTime() - start));
        }
        log.log();
        return value;
    }

    @Test
    public static Object methodHandle() throws Throwable {
        System.out.println("---- methodHandle ----");
        var log = new Log();
        Object value = null;
        for (int outer = 0; outer < 50; outer++) {
            long start = System.nanoTime();
            for (int i = 0; i < 100000000; i++) {
                value = mh.invoke();
            }
            log.add((System.nanoTime() - start));
        }
        log.log();
        return value;
    }

    public static void main(String[] args) throws Throwable {
        normal();
        reflect();
        methodHandle();
    }

    public static class Log {
        private final List<Long> all = new ArrayList<>();

        public void add(long time) {
            var i = all.size();
            all.add(time);
            System.out.println(i + " : " + (time/1000_000) + " ms");
        }

        public void log() {
            long max = all.stream().mapToLong(c -> c).max().getAsLong();
            long min = all.stream().mapToLong(c -> c).min().getAsLong();
            long sum = all.stream().mapToLong(c -> c).sum();
            long average = (sum - max - min) / (all.size() - 2);
            System.out.println("*********************\n" +
                    "* max     :  " + max + " ms\n" +
                    "* min     :  " + min + " ms\n" +
                    "* sum     :  " + sum + " ms\n" +
                    "* average :  " + average + " ms\n" +
                    "*********************");
        }
    }

}
