package cool.scx.lab.common;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 单项链表节点
 *
 * @param <T>
 * @author scx567888
 * @version 1.0.0
 */
public class SingleLinkedListNode<T> {

    public final T val;

    public final SingleLinkedListNode<T> next;

    /**
     * <p>Constructor for SingleLinkedListNode.</p>
     */
    public SingleLinkedListNode() {
        this(null);
    }

    /**
     * <p>Constructor for SingleLinkedListNode.</p>
     *
     * @param val a T object
     */
    public SingleLinkedListNode(T val) {
        this(val, null);
    }

    /**
     * <p>Constructor for SingleLinkedListNode.</p>
     *
     * @param val  a T object
     * @param next a {@link cool.scx.learn.common.SingleLinkedListNode} object
     */
    public SingleLinkedListNode(T val, SingleLinkedListNode<T> next) {
        this.val = val;
        this.next = next;
    }


    /**
     * 链表转数组 (基本类型 int)
     *
     * @param SingleLinkedListNode 链表
     * @return 数组
     */
    public static int[] toIntArray(final SingleLinkedListNode<Integer> SingleLinkedListNode) {
        return toList(SingleLinkedListNode).stream().mapToInt(c -> c).toArray();
    }

    /**
     * 链表转数组 (因为 java 残废的泛型所以这里没法返回泛型数组)
     *
     * @param SingleLinkedListNode 链表
     * @return Object 数组
     */
    public static Object[] toArray(final SingleLinkedListNode<?> SingleLinkedListNode) {
        return toList(SingleLinkedListNode).toArray();
    }

    /**
     * 链表转集合
     *
     * @param SingleLinkedListNode 链表
     * @param <T>                  a T class
     * @return 数组
     */
    public static <T> ArrayList<T> toList(final SingleLinkedListNode<T> SingleLinkedListNode) {
        var list = new ArrayList<T>();
        for (var x = SingleLinkedListNode; x != null; x = x.next) {
            list.add(x.val);
        }
        return list;
    }

    /**
     * 数组转链表
     *
     * @param array 数组
     * @param <T>   a T class
     * @return 链表
     */
    public static <T> SingleLinkedListNode<T> fromArray(final T[] array) {
        SingleLinkedListNode<T> tempSingleLinkedListNode = null;
        for (int i = array.length - 1; i >= 0; i--) {
            tempSingleLinkedListNode = new SingleLinkedListNode<>(array[i], tempSingleLinkedListNode);
        }
        return tempSingleLinkedListNode;
    }

    /**
     * 数组转链表 (同样因为 java 残废的泛型才有的方法)
     *
     * @param array 数组
     * @return 链表
     */
    public static SingleLinkedListNode<Integer> fromIntArray(final int[] array) {
        return fromArray(Arrays.stream(array).boxed().toArray(Integer[]::new));
    }

    /**
     * 数组转链表 (同样因为 java 残废的泛型才有的方法)
     *
     * @param array 数组
     * @return 链表
     */
    public static SingleLinkedListNode<Integer> fromIntArray2(final int[] array) {
        return fromArray2(Arrays.stream(array).boxed().toArray(Integer[]::new));
    }

    /**
     * 递归方式的数组转链表
     *
     * @param array 数组
     * @param <T>   a T class
     * @return 链表
     */
    public static <T> SingleLinkedListNode<T> fromArray2(final T[] array) {
        return _fromArray2(null, array, array.length - 1);
    }

    /**
     * 真正的递归方法
     *
     * @param next  下一个节点
     * @param array 数组
     * @param index 当前数组的索引
     * @param <T>   a T class
     * @return 链表
     */
    private static <T> SingleLinkedListNode<T> _fromArray2(SingleLinkedListNode<T> next, T[] array, int index) {
        var tempSingleLinkedListNode = new SingleLinkedListNode<>(array[index], next);
        if (index == 0) {
            return tempSingleLinkedListNode;
        } else {
            return _fromArray2(tempSingleLinkedListNode, array, index - 1);
        }
    }

}
