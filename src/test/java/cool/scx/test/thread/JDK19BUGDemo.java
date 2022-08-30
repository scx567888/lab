package cool.scx.test.thread;

import org.testng.annotations.Test;

import java.util.concurrent.CompletableFuture;

public class JDK19BUGDemo {

    public static void main(String[] args) {
        test();
    }

    @Test
    public static void test() {
        // jdk 19 bug ?
        // 为什么 除第一个以外都输出 null
        for (int i = 0; i < 9; i++) {
            var tl = new InheritableThreadLocal<String>();
            tl.set("value");
            CompletableFuture.runAsync(() -> System.out.println(tl.get())).join();
        }
    }

}
