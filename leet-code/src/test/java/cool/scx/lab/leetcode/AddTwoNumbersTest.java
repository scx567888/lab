package cool.scx.lab.leetcode;

import org.testng.annotations.Test;

import static cool.scx.lab.common.SingleLinkedListNode.*;
import static cool.scx.lab.leetcode.AddTwoNumbers.addTwoNumbers;
import static org.testng.Assert.assertEquals;

public class AddTwoNumbersTest {

    public static void main(String[] args) {
        test1();
        test2();
        test3();
    }

    @Test
    public static void test1() {
        var l1 = new int[]{2, 4, 3};
        var l2 = new int[]{5, 6, 4};
        var correctResults = new int[]{7, 0, 8};
        var listNode1 = fromIntArray(l1);
        var listNode2 = fromIntArray2(l2);
        var result = addTwoNumbers(listNode1, listNode2);
        var finalResult = toIntArray(result);
        assertEquals(correctResults, finalResult);
    }

    @Test
    public static void test2() {
        var l1 = new int[]{0};
        var l2 = new int[]{0};
        var correctResults = new int[]{0};
        var listNode1 = fromIntArray(l1);
        var listNode2 = fromIntArray2(l2);
        var result = addTwoNumbers(listNode1, listNode2);
        var finalResult = toIntArray(result);
        assertEquals(correctResults, finalResult);
    }

    @Test
    public static void test3() {
        var l1 = new int[]{9, 9, 9, 9, 9, 9, 9};
        var l2 = new int[]{9, 9, 9, 9};
        var correctResults = new int[]{8, 9, 9, 9, 0, 0, 0, 1};
        var listNode1 = fromIntArray(l1);
        var listNode2 = fromIntArray2(l2);
        var result = addTwoNumbers(listNode1, listNode2);
        var finalResult = toIntArray(result);
        assertEquals(correctResults, finalResult);
    }

}
