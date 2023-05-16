package cool.scx.lab.thread;

import org.testng.annotations.Test;

import java.util.concurrent.CompletableFuture;

/**
 * @see <a href='https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8293066'>https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8293066<a/>
 */
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
