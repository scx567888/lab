package cool.scx.lab.computer_theory;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * <p>Utils class.</p>
 *
 * @author scx567888
 * @version 1.0.0
 */
public class Utils {

    /**
     * <p>booleanToInt.</p>
     *
     * @param booleans a boolean
     * @return an array of {@link int} objects
     */
    public static int[] booleanToInt(boolean... booleans) {
        var ints = new int[booleans.length];
        for (int i = 0; i < booleans.length; i++) {
            ints[i] = (booleans[i] ? 1 : 0);
        }
        return ints;
    }

    /**
     * <p>intToBoolean.</p>
     *
     * @param ints a int
     * @return an array of {@link boolean} objects
     */
    public static boolean[] intToBoolean(int... ints) {
        var booleans = new boolean[ints.length];
        for (int i = 0; i < ints.length; i++) {
            switch (ints[i]) {
                case 1 -> booleans[i] = true;
                case 0 -> booleans[i] = false;
                default -> throw new IllegalArgumentException("数据只能为 0 或 1 : " + ints[i]);
            }
        }
        return booleans;
    }

    /**
     * 十进制转 二进制
     *
     * @param i 十进制数字
     * @return 二进制数组
     */
    public static int[] decimalToBinary(int i) {
        var s = Integer.toBinaryString(i);
        s = "0".repeat(8 - s.length()) + s;
        return Arrays.stream(s.split("")).mapToInt(Integer::valueOf).toArray();
    }

    /**
     * 二进制转 十进制
     *
     * @param bins 二进制数组
     * @return a int
     */
    public static int binaryToDecimal(int[] bins) {
        return Integer.valueOf(Arrays.stream(bins).mapToObj(String::valueOf).collect(Collectors.joining()), 2);
    }

}
