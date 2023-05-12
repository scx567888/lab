package cool.scx.test.djc;

import jdk.jshell.JShell;

public class aaa {
    public static void main(String[] args) {
        var s= "System.exit(1)";
        JShell jShell = JShell.create();
        jShell.eval(s);
    }
}
