package com.jason.sorting;

import java.util.*;

public class SortBenchmark {

    public static void main(String[] args) {
        new SortBenchmark().run();
    }

    //Here I'm starting the benchmarking by generating input arrays and timing all algorithms
    public void run() {
        int[] sizes = {100, 250, 500, 750, 1000, 1250, 2500, 3750, 5000, 6250, 7500, 8750, 10000};

        //generating arrays so each algorithm gets the same input
        Map<Integer, List<int[]>> dataset = new HashMap<>();
        for (int size : sizes) {
            List<int[]> arraysForSize = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                arraysForSize.add(generateRandomArray(size));
            }
            dataset.put(size, arraysForSize);
        }

        //the header row for size values
        System.out.printf("%-15s", "Size");
        for (int size : sizes) {
            System.out.printf("%-10d", size);
        }
        System.out.println();

        //running each sort with the same random inputs
        benchmark("Bubble Sort", sizes, SortBenchmark::bubbleSort, dataset);
        benchmark("Selection Sort", sizes, SortBenchmark::selectionSort, dataset);
        benchmark("Insertion Sort", sizes, SortBenchmark::insertionSort, dataset);
        benchmark("Counting Sort", sizes, SortBenchmark::countingSort, dataset);
        benchmark("Merge Sort", sizes, SortBenchmark::mergeSort, dataset);
    }

    public static void benchmark(String sortName, int[] sizes, Sorter sorter, Map<Integer, List<int[]>> dataset) {
        System.out.printf("%-15s", sortName);
        for (int size : sizes) {
            long totalTime = 0;

            List<int[]> testArrays = dataset.get(size);

            //execute and time each run
            for (int i = 0; i < 10; i++) {
                int[] copy = Arrays.copyOf(testArrays.get(i), size); //defensive copy
                long start = System.nanoTime();
                sorter.sort(copy);
                long end = System.nanoTime();
                totalTime += (end - start);
            }

            //here I'm converting nanoseconds to milliseconds and outputting the average
            double avgMillis = totalTime / 10.0 / 1_000_000.0;
            System.out.printf("%-10.3f", avgMillis);
        }
        System.out.println();
    }

    //generate a random array with ints between 0 and 999
    public static int[] generateRandomArray(int n) {
        Random rand = new Random();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = rand.nextInt(1000);
        }
        return array;
    }

    //This section is all the actual sorting algorithms
    //bubble sort
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

    //selection sort
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

    //Insertion sort
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

    //counting sort
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

    //merge sort
    public static void mergeSort(int[] arr) {
        if (arr.length < 2) return;
        int mid = arr.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);
        mergeSort(left);
        mergeSort(right);
        merge(arr, left, right);
    }

    // second function for merge sort
    private static void merge(int[] arr, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            arr[k++] = (left[i] <= right[j]) ? left[i++] : right[j++];
        }
        while (i < left.length) arr[k++] = left[i++];
        while (j < right.length) arr[k++] = right[j++];
    }
}
