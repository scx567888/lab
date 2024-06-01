package cool.scx.lab.jvm;

import org.testng.annotations.Test;

/**
 * 栈帧过多导致的栈溢出, 因为 jvm 的大部分实现都没有做尾递归优化, Loom 项目可能会有
 * <a href="https://wiki.openjdk.org/display/loom/Main">Loom</a>
 */
public class Main {

    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    @Test
    public static void test1() {
        try {
            m1();
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println(count);
        }
    }

    /**
     * 使用无限递归触发栈溢出
     */
    public static void m1() {
        count += 1;
        m1();
    }

}
