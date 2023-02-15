package com.example.springCloud.algorithm;

import org.junit.Test;

import java.util.Arrays;

public class Bubbling {

    @Test
    public void bubbling_sort_test() {
        int[] nums = {100, 6, 8, 10, 5, 2, 3, 1};
        quick_sort(0, nums.length - 1, nums);
        System.out.println(Arrays.toString(nums));
//        System.out.println(Arrays.toString(bubbling_sort(nums)));
    }


    /**
     * 冒泡排序
     *
     * @param nums
     * @return
     */
    public int[] bubbling_sort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = 1; j < nums.length - i; j++) {
                int temp = nums[j - 1];
                if (temp >= nums[j]) {
                    nums[j - 1] = nums[j];
                    nums[j] = temp;
                }

            }
        }
        return nums;
    }


    /**
     * 快速排序
     *
     * @param leftIndex
     * @param rightIndex
     * @param nums
     */
    public void quick_sort(int leftIndex, int rightIndex, int[] nums) {
        if (leftIndex > rightIndex) {
            return;
        }
        int left = leftIndex;
        int right = rightIndex;
        int temp = nums[left];
        while (left < right) {
            while (left < right && nums[right] >= temp) {
                right--;
            }
            nums[left] = nums[right];
            while (left < right && nums[left] <= temp) {
                left++;
            }
            nums[right] = nums[left];
        }
        nums[left] = temp;
        quick_sort(leftIndex, left - 1, nums);
        quick_sort(left + 1, rightIndex, nums);
    }
}
