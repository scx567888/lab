package cool.scx.learn.thread;

import cool.scx.util.exception.ScxExceptionHelper;

public class TestVirtualThread {

    public static void main(String[] args) throws InterruptedException {
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
