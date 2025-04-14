package com.jason.sorting;

import java.util.*;

public class StudentNumberTest {

    public static void main(String[] args) {
        int[] arr = {4, 7, 2, 9, 1, 0};

        System.out.println("Bubble Sort (Step-by-Step):");
        bubbleSort(arr.clone());

        System.out.println("\nSelection Sort (Step-by-Step):");
        selectionSort(arr.clone());

        System.out.println("\nInsertion Sort (Step-by-Step):");
        insertionSort(arr.clone());

        System.out.println("\nCounting Sort (Step-by-Step):");
        countingSort(arr.clone());

        System.out.println("\nMerge Sort (Step-by-Step):");
        mergeSort(arr.clone());
    }

    public static void benchmark(String sortName, int[] sizes, Sorter sorter) {
        System.out.printf("%-15s", sortName);
        for (int size : sizes) {
            long totalTime = 0;
            for (int i = 0; i < 10; i++) {
                int[] array = generateRandomArray(size);
                int[] copy = Arrays.copyOf(array, array.length);
                long start = System.nanoTime();
                sorter.sort(copy);
                long end = System.nanoTime();
                totalTime += (end - start);
            }
            double avgMillis = totalTime / 10.0 / 1_000_000.0;
            System.out.printf("%-10.3f", avgMillis);
        }
        System.out.println();
    }

    public static int[] generateRandomArray(int n) {
        Random rand = new Random();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = rand.nextInt(1000);
        }
        return array;
    }

    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
            System.out.println("Pass " + (i + 1) + ": " + Arrays.toString(arr));
        }
    }

    public static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = temp;
            System.out.println("Step " + (i + 1) + ": " + Arrays.toString(arr));
        }
    }

    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
            System.out.println("Step " + i + ": " + Arrays.toString(arr));
        }
    }

    public static void countingSort(int[] arr) {
        System.out.println("Original: " + Arrays.toString(arr));
        if (arr.length == 0) return;
        int max = Arrays.stream(arr).max().orElse(0);
        int[] count = new int[max + 1];
        for (int num : arr) count[num]++;
        int index = 0;
        for (int i = 0; i < count.length; i++) {
            while (count[i]-- > 0) {
                arr[index++] = i;
                System.out.println("Step (placing " + i + "): " + Arrays.toString(arr));
            }
        }
    }

    public static void mergeSort(int[] arr) {
        System.out.println("Original: " + Arrays.toString(arr));
        mergeSortTrace(arr, 0);
    }

    private static void mergeSortTrace(int[] arr, int depth) {
        if (arr.length < 2) return;
        int mid = arr.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);

        mergeSortTrace(left, depth + 1);
        mergeSortTrace(right, depth + 1);

        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            arr[k++] = (left[i] <= right[j]) ? left[i++] : right[j++];
        }
        while (i < left.length) arr[k++] = left[i++];
        while (j < right.length) arr[k++] = right[j++];

        System.out.println("Merge at depth " + depth + ": " + Arrays.toString(arr));
    }
}

