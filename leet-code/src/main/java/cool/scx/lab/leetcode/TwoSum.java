package cool.scx.lab.leetcode;

import java.util.HashMap;

/**
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target 的那 两个 整数，并返回它们的数组下标。
 * <p>
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
 * <p>
 * 你可以按任意顺序返回答案。
 * <p>
 *
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[0,1]
 * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
 * 示例 2：
 * <p>
 * 输入：nums = [3,2,4], target = 6
 * 输出：[1,2]
 * 示例 3：
 * <p>
 * 输入：nums = [3,3], target = 6
 * 输出：[0,1]
 *
 * @author scx567888
 * @version 1.0.0
 */
public class TwoSum {

    /**
     * <p>twoSum.</p>
     *
     * @param nums   an array of {@link int} objects
     * @param target a int
     * @return an array of {@link int} objects
     */
    public static int[] twoSum(int[] nums, int target) {
        var map = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            var nowValue = nums[i];
            var resultIndex = map.get(target - nowValue);
            if (resultIndex != null) {
                return new int[]{i, resultIndex};
            }
            map.put(nowValue, i);
        }
        return new int[]{};
    }

}
