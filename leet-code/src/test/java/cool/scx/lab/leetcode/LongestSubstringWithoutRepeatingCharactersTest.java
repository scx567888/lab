package cool.scx.lab.leetcode;

import org.testng.Assert;
import org.testng.annotations.Test;

import static cool.scx.lab.leetcode.LongestSubstringWithoutRepeatingCharacters.lengthOfLongestSubstring;

public class LongestSubstringWithoutRepeatingCharactersTest {

    public static void main(String[] args) {
        test1();
        test2();
        test3();
    }

    @Test(enabled = false)//todo
    public static void test1() {
        var s = "abcabcbb";
        var correctResults = 3;
        var result = lengthOfLongestSubstring(s);
        Assert.assertEquals(correctResults, result);
    }

    @Test(enabled = false)//todo
    public static void test2() {
        var s = "bbbbb";
        var correctResults = 1;
        var result = lengthOfLongestSubstring(s);
        Assert.assertEquals(correctResults, result);
    }

    @Test(enabled = false)//todo
    public static void test3() {
        var s = "pwwkew";
        var correctResults = 3;
        var result = lengthOfLongestSubstring(s);
        Assert.assertEquals(correctResults, result);
    }

}
