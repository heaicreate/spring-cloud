package com.example.springCloud.algorithm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Solution {


    @Test
    public void testAddTwoNumbers() {
        int[] l1s = {1, 2, 3, 4, 9};
        int[] l2s = {1, 2, 3};

        ListNode listNode = arraysToListNode(l1s);
        ListNode listNode2 = arraysToListNode(l2s);
        System.out.println(listNode);
        System.out.println(listNode2);
        System.out.println(addTwoNumbers(listNode, listNode2));
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        List<Integer> l11 = new ArrayList<>();
        List<Integer> l21 = new ArrayList<>();
        List<Integer> result = new ArrayList<>();
        while (l1 != null) {
            l11.add(l1.val);
            l1 = l1.nextNode;
        }

        while (l2 != null) {
            l21.add(l2.val);
            l2 = l2.nextNode;
        }

        int length = Math.max(l11.size(), l21.size());

        int sum = 0;
        for (int i = 0; i < length; i++) {
            int resultVal = 0;
            if (i < l11.size()) {
                resultVal += l11.get(i);
            }
            if (i < l21.size()) {
                resultVal += l21.get(i);
            }
            if (sum == 1) {
                resultVal += sum;
                sum = 0;
            }
            if (resultVal >= 10) {
                result.add(resultVal - 10);
                sum = 1;
            } else {
                result.add(resultVal);
            }
        }
        Integer[] test= result.toArray(new Integer[result.size()]);
        ListNode listNode=arraysToListForwardNode(test);
        return listNode;
    }


    public List<Integer> ListNodeToArrays(ListNode listNode) {
        List<Integer> result = new ArrayList<>();
        while (listNode != null) {
            result.add(listNode.val);
            listNode = listNode.nextNode;
        }
        return result;
    }

    /**
     * 数组正向转化链表
     *
     * @param l1s
     * @return
     */
    public ListNode arraysToListForwardNode(Integer[] l1s) {
        ListNode listNode = new ListNode(l1s[0]);
        ListNode listNodeTemp = listNode;
        int index = 1;
        while (index < l1s.length) {
            ListNode nextNode = new ListNode(l1s[index]);
            listNodeTemp.nextNode = nextNode;
            listNodeTemp = listNodeTemp.nextNode;
            index++;
        }
        return listNode;
    }


    public ListNode arraysToListNode(int[] l1s) {
        ListNode listNode = new ListNode(l1s[0]);
        int index = 1;
        while (index < l1s.length) {
            ListNode listNodeTemp = new ListNode(l1s[index]);
            listNodeTemp.nextNode = listNode;
            listNode = listNodeTemp;
            index++;
        }

        return listNode;
    }


    class ListNode {
        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    ", nextNode=" + nextNode +
                    '}';
        }

        public ListNode(Integer val) {
            this.val = val;
        }

        Integer val;
        ListNode nextNode;
    }
}
