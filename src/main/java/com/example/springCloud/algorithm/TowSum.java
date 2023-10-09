package com.example.springCloud.algorithm;

import org.junit.Test;

import java.util.Arrays;

/**
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
 * <p>
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
 * <p>
 * 你可以按任意顺序返回答案。
 */
public class TowSum {

    @Test
    public void towSum_test() {
        int[] num = {1,2,3,2,7,11,15};
        int target = 9;
        System.out.println(Arrays.toString(towSum(num, target)));
    }


    public int[] towSum(int[] num, int target) {
        int[] result = new int[2];
        int left = 0;
        int right = 1;
        while (left < num.length) {
            if (right >= num.length) {
                left++;
                right = left + 1;
                continue;
            }
            int sum = num[left] + num[right];
            if (sum != target) {
                right++;
            }
            if (sum == target) {
                result[0] = left;
                result[1] = right;
                return result;
            }
        }
        return result;
    }

}
