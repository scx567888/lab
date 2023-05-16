package cool.scx.lab.computer_theory;

/**
 * 模拟基本逻辑门及其组合关系
 *
 * @author scx567888
 * @version 1.0.0
 */
public class LogicGate {

    /**
     * 非门 (基本)
     *
     * @param a a boolean
     * @return a boolean
     */
    public static boolean not(boolean a) {
        return !a;
    }

    /**
     * 与门 (基本)
     *
     * @param a a boolean
     * @param b a boolean
     * @return a boolean
     */
    public static boolean and(boolean a, boolean b) {
        return a && b;
    }

    /**
     * 或门 (基本)
     *
     * @param a a boolean
     * @param b a boolean
     * @return a boolean
     */
    public static boolean or(boolean a, boolean b) {
        return a || b;
    }

    /**
     * 异或门 (组合)
     *
     * @param a a boolean
     * @param b a boolean
     * @return a boolean
     */
    public static boolean xor(boolean a, boolean b) {
        var bothTrue = and(a, b);
        var notBothTrue = not(bothTrue);
        var hasOneOrTwoTrue = or(a, b);
        return and(notBothTrue, hasOneOrTwoTrue);
    }

    /**
     * 与非门 (组合)
     *
     * @param a a boolean
     * @param b a boolean
     * @return a boolean
     */
    public static boolean nand(boolean a, boolean b) {
        var bothTrue = and(a, b);
        return not(bothTrue);
    }

    /**
     * 或非门 (组合)
     *
     * @param a a boolean
     * @param b a boolean
     * @return a boolean
     */
    public static boolean nor(boolean a, boolean b) {
        var atLeastOneIsTrue = or(a, b);
        return not(atLeastOneIsTrue);
    }

    /**
     * 同或门 [也叫异或非门] (组合)
     *
     * @param a a boolean
     * @param b a boolean
     * @return a boolean
     */
    public static boolean xnor(boolean a, boolean b) {
        var bothTrue = and(a, b);
        var atLeastOneIsTrue = or(a, b);
        var bothFalse = not(atLeastOneIsTrue);
        return or(bothTrue, bothFalse);
    }

}
