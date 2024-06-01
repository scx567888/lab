package cool.scx.lab.leetcode;

import org.testng.annotations.Test;

import static cool.scx.lab.leetcode.TwoSum.twoSum;
import static cool.scx.common.util.ArrayUtils.toObjectArray;
import static org.testng.Assert.assertEqualsNoOrder;

public class TwoSumTest {

    public static void main(String[] args) {
        test1();
        test2();
        test3();
    }

    @Test
    public static void test1() {
        var nums = new int[]{2, 7, 11, 15};
        var target = 9;
        var correctResults = new int[]{0, 1};
        var result = twoSum(nums, target);
        assertEqualsNoOrder(toObjectArray(correctResults), toObjectArray(result), null);
    }

    @Test
    public static void test2() {
        var nums = new int[]{3, 2, 4};
        var target = 6;
        var correctResults = new int[]{1, 2};
        var result = twoSum(nums, target);
        assertEqualsNoOrder(toObjectArray(correctResults), toObjectArray(result), null);
    }

    @Test
    public static void test3() {
        var nums = new int[]{3, 3};
        var target = 6;
        var correctResults = new int[]{0, 1};
        var result = twoSum(nums, target);
        assertEqualsNoOrder(toObjectArray(correctResults), toObjectArray(result), null);
    }

}
