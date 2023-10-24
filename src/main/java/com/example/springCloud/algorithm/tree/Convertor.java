package com.example.springCloud.algorithm.tree;

public class Convertor {
    // 将数组转换为完全二叉树
    public static TreeNode arrayToCompleteBinaryTree(int[] arr) {
        return arrayToCompleteBinaryTree(arr, 0);
    }

    private static TreeNode arrayToCompleteBinaryTree(int[] arr, int index) {
        if (index >= arr.length) {
            return null;
        }

        TreeNode root = new TreeNode(arr[index]);
        root.left = arrayToCompleteBinaryTree(arr, 2 * index + 1);
        root.right = arrayToCompleteBinaryTree(arr, 2 * index + 2);

        return root;
    }

    // 将完全二叉树转换为数组
    public static int[] completeBinaryTreeToArray(TreeNode root) {
        int height = getHeight(root);
        // 完全二叉树的节点数最大为满二叉树节点数 数组值为默认值0表示无该节点
        int[] arr = new int[(int) Math.pow(2, height) - 1];
        completeBinaryTreeToArray(root, arr, 0);
        return arr;
    }

    private static void completeBinaryTreeToArray(TreeNode root, int[] arr, int index) {
        if (root == null) {
            return;
        }

        arr[index] = root.val;
        completeBinaryTreeToArray(root.left, arr, 2 * index + 1);
        completeBinaryTreeToArray(root.right, arr, 2 * index + 2);
    }

    private static int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(getHeight(root.left), getHeight(root.right));
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6};

        TreeNode root = arrayToCompleteBinaryTree(arr);


        System.out.println(root);
        int[] result = completeBinaryTreeToArray(root);
        for (int num : result) {
            System.out.print(num + " ");
        }
    }


}
