package cool.scx.lab.thread;

import cool.scx.common.util.StopWatch;
import org.testng.annotations.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import static cool.scx.common.util.ScxExceptionHelper.ignore;
import static cool.scx.common.util.ScxExceptionHelper.wrap;

public class TestVirtualThread {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        test1();
        //当注释掉 test2 时 test3 可以正确打印出 666
        //bug 请看 JDK19BUGDemo
        test2();
        test3();
    }

    @Test
    public static void test1() throws InterruptedException {
        var l = new InheritableThreadLocal<Integer>();
        var vv = IntStream.range(0, 99).mapToObj(i -> {
            l.set(i);
            return Thread.startVirtualThread(() -> {
                ignore(() -> Thread.sleep(10));
                System.out.println(i + " : " + l.get());
            });
        }).toList();
        for (var c : vv) {
            c.join();
        }
    }

    @Test
    public static void test2() throws ExecutionException, InterruptedException {
        var tl = new InheritableThreadLocal<Integer>();
        tl.set(777);
        var future = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread());
            wrap(() -> Thread.sleep(2000));
            System.out.println(tl.get());
        });
        var future1 = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread());
            wrap(() -> Thread.sleep(1000));
            System.out.println(tl.get());
        });
        future.get();
        future1.get();
    }

    @Test
    public static void test3() throws ExecutionException, InterruptedException {
        //并行测试
        var tl = new InheritableThreadLocal<Integer>();
        tl.set(666);
        StopWatch.start("123");
        //任务 a
        var a = CompletableFuture.supplyAsync(() -> {
            System.out.println("a : " + Thread.currentThread());
            wrap(() -> Thread.sleep(2000));
            var s = tl.get();
            System.out.println("a :" + s);
            System.out.println("a : " + Thread.currentThread());
            return s;
        });
        //任务 b
        var b = CompletableFuture.supplyAsync(() -> {
            System.out.println("b : " + Thread.currentThread());
            wrap(() -> Thread.sleep(1000));
            var s = tl.get();
            System.out.println("b :" + s);
            System.out.println("b : " + Thread.currentThread());
            return s;
        });
        //获取结果
        Integer integer = a.get();
        Integer integer1 = b.get();
        //耗时为最大的任务耗时
        System.out.println(StopWatch.stopToMillis("123") + " : " + integer + " " + integer1);
    }

}
