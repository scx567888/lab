package cool.scx.lab.jvm;

public class Main2 {

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            while (true) {

            }
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(100000000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

}
