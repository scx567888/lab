package cool.scx.lab.thread;

import java.util.concurrent.ForkJoinPool;

public class Demo1 {

    public static void main(String[] args) throws InterruptedException {
        Runnable r = () -> System.out.println(Thread.currentThread());

        var pThread =
                Thread.ofPlatform()
                        .unstarted(r);
        pThread.start();
        pThread.join();

        var vThread =
                Thread.ofVirtual()
                        .unstarted(r);
        vThread.start();
        vThread.join();

        System.out.println(vThread.getClass());

        var task = ForkJoinPool.commonPool()
                .submit(r);

        task.join();


    }

}
