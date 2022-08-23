package cool.scx.test.thread;

import cool.scx.util.exception.ScxExceptionHelper;
import org.testng.annotations.Test;

public class TestVirtualThread {

    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    @Test
    public static void test1() throws InterruptedException {
        var vThread = Thread.ofVirtual();
        for (int i = 0; i < 999; i++) {
            int finalI = i;
            vThread.start(() -> {
                ScxExceptionHelper.ignore(() -> Thread.sleep(10));
                System.out.println(finalI);
            }).join();
        }
    }

}
