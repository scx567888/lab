package cool.scx.test.thread;

import cool.scx.util.exception.ScxExceptionHelper;
import org.testng.annotations.Test;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class TestVirtualThread {

    public static void main(String[] args) throws InterruptedException {
        test1();
        test2();
    }

    @Test
    public static void test1() throws InterruptedException {
        var l = new InheritableThreadLocal<Integer>();
        var vv = IntStream.range(0, 99).mapToObj(i -> {
            l.set(i);
            return Thread.startVirtualThread(() -> {
                ScxExceptionHelper.ignore(() -> Thread.sleep(10));
                System.out.println(i + " : " + l.get());
            });
        }).toList();
        for (var c : vv) {
            c.join();
        }
    }

    @Test
    public static void test2() {
        var tl = new InheritableThreadLocal<String>();
        tl.set("不是空");
        CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread());
            // java 19 之前 则会为空
            System.out.println(tl.get());
        }).join();
    }

}
