package cool.scx.lab.computer_theory;

import static cool.scx.lab.computer_theory.LogicGate.*;
import static cool.scx.lab.computer_theory.Utils.booleanToInt;
import static cool.scx.lab.computer_theory.Utils.intToBoolean;

/**
 * 模拟 算术逻辑单元
 *
 * @author scx567888
 * @version 1.0.0
 */
public class ALU {

    /**
     * 半加法器 (返回的数组永远是两位 : [进位, 总和] )
     *
     * @param a a boolean
     * @param b a boolean
     * @return an array of {@link boolean} objects
     */
    public static boolean[] halfAdder(boolean a, boolean b) {
        //两者都为 true 则表示需要进位
        var carry = and(a, b);
        //1 + 1 的和为 0 正好符合异或门结果
        var sum = xor(a, b);
        return new boolean[]{carry, sum};
    }

    /**
     * 半加法器 [数字版] (返回的数组永远是两位 : [进位, 总和] )
     *
     * @param a a int
     * @param b a int
     * @return an array of {@link int} objects
     */
    public static int[] halfAdder(int a, int b) {
        var booleans = intToBoolean(a, b);
        var result = halfAdder(booleans[0], booleans[1]);
        return booleanToInt(result);
    }

    /**
     * 全加法器 (返回的数组永远是两位 : [进位, 总和] )
     *
     * @param a a boolean
     * @param b a boolean
     * @param c a boolean
     * @return an array of {@link boolean} objects
     */
    public static boolean[] fullAdder(boolean a, boolean b, final boolean c) {
        //先算其中两个数相加
        var r1 = halfAdder(a, b);
        var r1Carry = r1[0];
        var r1Sum = r1[1];
        //用上一个数的和与下一个数相加
        var r2 = halfAdder(r1Sum, c);
        var r2Carry = r2[0];
        var r2Sum = r2[1];
        //两次相加中有任意一次进位了就表示需要一次进位
        var carry = or(r1Carry, r2Carry);
        return new boolean[]{carry, r2Sum};
    }

    /**
     * 全加法器 (返回的数组永远是两位 : [进位, 总和] )
     *
     * @param a a int
     * @param b a int
     * @param c a int
     * @return an array of {@link int} objects
     */
    public static int[] fullAdder(int a, int b, int c) {
        var booleans = intToBoolean(a, b, c);
        var result = fullAdder(booleans[0], booleans[1], booleans[2]);
        return booleanToInt(result);
    }

    /**
     * 判断一个数字是否为 0
     *
     * @param a 8 位二进制数组
     * @return 是否为 0
     */
    public static boolean isZero(boolean[] a) {
        if (a.length != 8) {
            throw new IllegalArgumentException("参数数组数量必须为 8");
        }
        var r1 = or(a[0], a[1]);
        var r2 = or(a[2], a[3]);
        var r3 = or(a[4], a[5]);
        var r4 = or(a[6], a[7]);
        var r5 = or(r1, r2);
        var r6 = or(r3, r4);
        var r = or(r5, r6);
        return not(r);
    }

    /**
     * a
     *
     * @param a a
     * @return a
     */
    public static boolean isZero(int[] a) {
        var a1 = intToBoolean(a);
        return isZero(a1);
    }

    /**
     * 8 位行波进位加法器
     * 现代计算机一般不使用此种加法器而是使用 [超前进位加法器] 可以了解一下
     *
     * @param a 8 位二进制数组
     * @param b 8 位二进制数组
     * @return 8 位二进制数组
     */
    public static boolean[] _8BitRippleCarryAdder(boolean[] a, boolean[] b) {
        if (a.length != 8 || b.length != 8) {
            throw new IllegalArgumentException("参数数组数量必须为 8");
        }
        var r = new boolean[8];
        //从后往前 (倒着) 逐位相加 第一次因为不涉及到进位 所以可以用 半加法器
        //也可以如下
        // var r1 = fullAdder(false, a[7], b[7]);
        var r1 = halfAdder(a[7], b[7]);
        //这里 r1[0] 为进位 r1[1] 为和数 所以我们直接将 r(结果集) 的最后一位设置为 r1[1] 同时将 r1[0] 相加到下一次数据中
        r[7] = r1[1];
        //同上 不在赘述
        var r2 = fullAdder(r1[0], a[6], b[6]);
        r[6] = r2[1];
        var r3 = fullAdder(r2[0], a[5], b[5]);
        r[5] = r3[1];
        var r4 = fullAdder(r3[0], a[4], b[4]);
        r[4] = r4[1];
        var r5 = fullAdder(r4[0], a[3], b[3]);
        r[3] = r5[1];
        var r6 = fullAdder(r5[0], a[2], b[2]);
        r[2] = r6[1];
        var r7 = fullAdder(r6[0], a[1], b[1]);
        r[1] = r7[1];
        var r8 = fullAdder(r7[0], a[0], b[0]);
        r[0] = r8[1];
        //两个8 位二进制数最大为 255 所以可能导致数据溢出 这里进行校验
        if (r8[0]) {
            throw new RuntimeException("数据溢出");
        }
        return r;
    }

    /**
     * 8 位行波进位加法器
     *
     * @param a an array of {@link int} objects
     * @param b an array of {@link int} objects
     * @return an array of {@link int} objects
     */
    public static int[] _8BitRippleCarryAdder(int[] a, int[] b) {
        var a1 = intToBoolean(a);
        var b1 = intToBoolean(b);
        var result = _8BitRippleCarryAdder(a1, b1);
        return booleanToInt(result);
    }

}
