package cool.scx.lab.djc.fake;

import java.io.PrintStream;

public class System {

    public static void exit(int status) {
        java.lang.System.out.println("并没有效果");
    }

    public static final PrintStream out = java.lang.System.out;

}
