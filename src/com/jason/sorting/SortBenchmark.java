package com.jason.sorting;

import java.util.*;

public class SortBenchmark {

    public static void main(String[] args) {
        // here's the input sizes to test with
        int[] sizes = {100, 250, 500, 750, 1000, 1250, 2500, 3750, 5000, 6250, 7500, 8750, 10000};

        // here I'm just printing the header row
        System.out.printf("%-15s", "Size");
        for (int size : sizes) {
            System.out.printf("%-10d", size);
        }
        System.out.println();

        // Running the benchmarks for each algorithm
        benchmark("Bubble Sort", sizes, SortBenchmark::bubbleSort);
        benchmark("Selection Sort", sizes, SortBenchmark::selectionSort);
        benchmark("Insertion Sort", sizes, SortBenchmark::insertionSort);
        benchmark("Counting Sort", sizes, SortBenchmark::countingSort);
        benchmark("Merge Sort", sizes, SortBenchmark::mergeSort);
    }

    /**
     * This benchmarks an algorithm over multiple input sizes,
     * each one tested 10 times with random arrays and the average time is calculated.
     */
    public static void benchmark(String sortName, int[] sizes, Sorter sorter) {
        System.out.printf("%-15s", sortName);
        for (int size : sizes) {
            long totalTime = 0;

            // repeat 10 times for averaging
            for (int i = 0; i < 10; i++) {
                int[] array = generateRandomArray(size); // creates a fresh array
                int[] copy = Arrays.copyOf(array, array.length); // copies for same input
                long start = System.nanoTime();
                sorter.sort(copy); // sort using algorithm
                long end = System.nanoTime();
                totalTime += (end - start); // total time taken
            }

            // here I convert nanoseconds to milliseconds and print the average
            double avgMillis = totalTime / 10.0 / 1_000_000.0;
            System.out.printf("%-10.3f", avgMillis);
        }
        System.out.println();
    }

    //This generates an array of random ints between 0 and 99
    public static int[] generateRandomArray(int n) {
        Random rand = new Random();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = rand.nextInt(1000);
        }
        return array;
    }
// Here is the actual algorithms, done modularly

    // bubble Sort
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
        }
    }

    //selection Sort
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
        }
    }


    //insertion Sort
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }
    }


    //counting Sort
    public static void countingSort(int[] arr) {
        if (arr.length == 0) return;
        int max = Arrays.stream(arr).max().orElse(0);
        int[] count = new int[max + 1];
        for (int num : arr) count[num]++;
        int index = 0;
        for (int i = 0; i < count.length; i++) {
            while (count[i]-- > 0) arr[index++] = i;
        }
    }


    //Merge Sort
    public static void mergeSort(int[] arr) {
        if (arr.length < 2) return;
        int mid = arr.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);
        mergeSort(left);
        mergeSort(right);
        merge(arr, left, right);
    }

    //second method for merge sort
    private static void merge(int[] arr, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            arr[k++] = (left[i] <= right[j]) ? left[i++] : right[j++];
        }
        while (i < left.length) arr[k++] = left[i++];
        while (j < right.length) arr[k++] = right[j++];
    }
}