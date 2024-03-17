package cool.scx.lab.thread;

import cool.scx.common.util.RandomUtils;

import java.util.stream.IntStream;

public class Demo2 {
    public static void main(String[] args) throws InterruptedException {

        var cache = new ThreadLocal<Integer>();

        var threads = IntStream.range(0, 10).mapToObj(index -> Thread.ofVirtual().unstarted(() -> {
            var random = RandomUtils.randomInt( 100);
            cache.set(random);
            if (index == 0) {
                System.out.println(Thread.currentThread());
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (index == 0) {
                System.out.println(Thread.currentThread());
            }
            System.out.println(random + " : " + cache.get());
        })).toList();

        threads.forEach(Thread::start);
        for (var thread : threads) {
            thread.join();
        }
    }
}
