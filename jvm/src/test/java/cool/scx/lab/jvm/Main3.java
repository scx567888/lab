package cool.scx.lab.jvm;

import java.util.ArrayList;

public class Main3 {

    public static void main(String[] args) {
        int count = 0;
        var s = new ArrayList<>();
        try {
            while (true) {
                count += 1;
                s.add(new Object());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println(count);
        }

    }

}
