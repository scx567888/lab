package cool.scx.test.thread;

import cool.scx.util.exception.ScxExceptionHelper;
import org.testng.annotations.Test;

public class TestVirtualThread {

    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    @Test
    public static void test1() throws InterruptedException {
        for (int i = 0; i < 999; i++) {
            int finalI = i;
            Thread.startVirtualThread(() -> {
                ScxExceptionHelper.ignore(() -> Thread.sleep(10));
                System.out.println(finalI + " : " + Thread.currentThread());
            }).join();
        }
    }

}
