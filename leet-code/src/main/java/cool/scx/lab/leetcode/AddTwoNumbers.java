package cool.scx.lab.leetcode;


import cool.scx.lab.common.SingleLinkedListNode;

/**
 * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照逆序的方式存储的，并且每个节点只能存储一位数字。
 * <p>
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 * <p>
 * 你可以假设除了数字 0 之外，这两个数都不会以 0开头。
 * <p>
 * 输入：l1 = [2,4,3], l2 = [5,6,4]
 * 输出：[7,0,8]
 * 解释：342 + 465 = 807.
 * 示例 2：
 * <p>
 * 输入：l1 = [0], l2 = [0]
 * 输出：[0]
 * 示例 3：
 * <p>
 * 输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
 * 输出：[8,9,9,9,0,0,0,1]
 *
 * @author scx567888
 * @version 1.0.0
 */
public class AddTwoNumbers {

    /**
     * <p>addTwoNumbers.</p>
     *
     * @param l1 a {@link cool.scx.lab.common.SingleLinkedListNode} object
     * @param l2 a {@link cool.scx.lab.common.SingleLinkedListNode} object
     * @return a {@link cool.scx.lab.common.SingleLinkedListNode} object
     */
    public static SingleLinkedListNode<Integer> addTwoNumbers(SingleLinkedListNode<Integer> l1, SingleLinkedListNode<Integer> l2) {
        return _addTwoNumbers(l1, l2, 0);
    }

    /**
     * 递归操作
     *
     * @param l1 l1
     * @param l2 l2
     * @param b  上一次计算的溢出数 如 (7 + 5 = 12) 则溢出数为 1
     * @return 相加后的链表
     */
    public static SingleLinkedListNode<Integer> _addTwoNumbers(SingleLinkedListNode<Integer> l1, SingleLinkedListNode<Integer> l2, int b) {
        if (l1 == null && l2 == null && b == 0) {
            return null;
        }
        int val = b;
        if (l1 != null) {
            val = val + l1.val;
            l1 = l1.next;
        }
        if (l2 != null) {
            val = val + l2.val;
            l2 = l2.next;
        }
        return new SingleLinkedListNode<>(val % 10, _addTwoNumbers(l1, l2, val / 10));
    }

}
