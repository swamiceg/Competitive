package com.java.practice;

import java.util.Arrays;

public class Sorting {

	public static void main(String[] args) {
		System.out.println("Sorting!!!");
		doSort(new int[]{5, 4, 3, 2, 1, 12, 23, 10, 0, 6});
	}
	
	public static void doSort(int[] arr) {
		doBubbleSort(Arrays.copyOf(arr, arr.length));
		doSelectionSort(Arrays.copyOf(arr, arr.length));
		doInsertionSort(Arrays.copyOf(arr, arr.length));
		doMergeSort(Arrays.copyOf(arr, arr.length));
		//doIterMergeSort(Arrays.copyOf(arr, arr.length));
		doQuickSort(Arrays.copyOf(arr, arr.length));
		doHeapSort(Arrays.copyOf(arr, arr.length));
	}
	
	public static void doBubbleSort(int[] arr) {
		System.out.println("Bubble Sort:");
		for(int i = arr.length-1; i > 0; i--) {
			boolean swap = false;
			for(int j = 0; j < i; j++) {
				if(arr[j] > arr[j+1]) {
					arr[j+1] = arr[j] + arr[j+1];
					arr[j] = arr[j+1] - arr[j];
					arr[j+1] = arr[j+1] - arr[j];
					swap = true;
				}
			}
			if(!swap) { //array sorted
				break;
			}
		}
		printArr(arr);
	}
	
	public static void doSelectionSort(int[] arr) {
		System.out.println("Selection Sort:");
		for(int i = 0; i < arr.length-1; i++) {
			int min = i;
			for(int j = i+1; j < arr.length; j++) {
				if(arr[j] < arr[min]) {
					min = j;
				}
			}
			if(min != i) {
				arr[min] = arr[min] + arr[i];
				arr[i] = arr[min] - arr[i];
				arr[min] = arr[min] - arr[i];
			}
		}
		printArr(arr);
	}
	
	public static void doInsertionSort(int[] arr) {
		System.out.println("Insertion Sort:");
		for(int i = 1; i < arr.length; i++) {
			int j = i-1; int ele = arr[i];
			while(j >= 0 && arr[j] > ele) {
				arr[j+1] = arr[j];
				j--;
			}
			arr[j+1] = ele;
		}
		printArr(arr);
	}
	
	public static void doMergeSort(int[] arr) {
		System.out.println("Merge Sort:");
		mergeSort(arr, 0, arr.length-1);
		printArr(arr);
	}
	
	private static void mergeSort(int[] arr, int l, int r) {	
		if(l < r) {
			int m = (l+r)/2;
			mergeSort(arr, l, m);
			mergeSort(arr, m+1, r);
			mergeArr(arr, l, m, r);
		}
	}
	
	private static void mergeArr(int[] arr, int l, int m, int r) {
		int[] temp1 = Arrays.copyOfRange(arr, l, m+1);
		int[] temp2 = Arrays.copyOfRange(arr, m+1, r+1);
		int i = 0, j = 0, k = l;
		while(i < temp1.length && j < temp2.length) {
			if(temp1[i] < temp2[j]) {
				arr[k++] = temp1[i];
				i++;
			} else {
				arr[k++] = temp2[j];
				j++;
			}
		}
		while(i < temp1.length) {
			arr[k++] = temp1[i++];
		}
		while(j < temp2.length) {
			arr[k++] = temp2[j++];
		}
	}
	
	public static void doIterMergeSort(int[] arr) {
		System.out.println("Iterative merge sort:");
		int curr_size;
		int left_start;
		int n = arr.length;
		System.out.println(n);
		for (curr_size = 1; curr_size <= n-1; curr_size = 2*curr_size) {
			for (left_start = 0; left_start < n-1; left_start += 2*curr_size) {
				int mid = left_start + curr_size - 1;

				int right_end = Math.min(mid+1+mid-left_start, n-1);//Math.min(left_start + 2*curr_size - 1, n-1);
				System.out.printf("Merging arr %d %d %d\n", left_start, mid, right_end);
				mergeArr(arr, left_start, mid, right_end);
			}
		}
		printArr(arr);
	}
	
	public static void doQuickSort(int[] arr) {
		System.out.println("Quick Sort:");
		quickSort(arr, 0, arr.length-1);
		printArr(arr);
	}
	
	private static void quickSort(int[] arr, int l, int r) {
		if(l < r) {
			int p = partition(arr, l, r);
			
			quickSort(arr, l, p-1);
			quickSort(arr, p+1, r);
		}
	}
	
	private static int partition(int[] arr, int l, int r) {
		int pivot = arr[r]; //last element as a pivot element
		int i = l-1;
		for(int j = l; j <= r-1; j++) {
			if(arr[j] <= pivot) {
				i++;
				int temp = arr[j];
				arr[j] = arr[i];
				arr[i] = temp;
			}
		}
		int temp = arr[i+1];
		arr[i+1] = pivot; //This is the proper position for the pivot element
		arr[r] = temp;
		return i+1;
	}
	
	public static void doHeapSort(int[] arr) {
		for(int i = arr.length/2-1; i >=0; i--) {
			doHeapify(arr, arr.length, i);
		}
		
		for(int i = arr.length-1; i >= 0; i--) {
			swap(arr, 0, i);
			doHeapify(arr, i, 0);
		}
		
		System.out.println("Heap Sort:");
		printArr(arr);
	}
	
	private static void doHeapify(int[] arr, int n, int index) {
		while(index < n) {
			int toSwap = index;
			if(left(index) < n && arr[left(index)] > arr[toSwap]) {
				toSwap = left(index);
			}
			if(right(index) < n && arr[right(index)] > arr[toSwap]) {
				toSwap = right(index);
			}
			if(toSwap != index) {
				swap(arr, toSwap, index);
				index = toSwap;
			} else {
				break;
			}
		}
	}
	
	private static void swap(int[] arr, int i, int j) {
		arr[i] = arr[i] + arr[j];
		arr[j] = arr[i] - arr[j];
		arr[i] = arr[i] - arr[j];
	}
	
	private static int left(int i) {
		return 2*i+1;
	}
	
	private static int right(int i) {
		return 2*i+2;
	}

	private static void printArr(int[] arr) {
		for(int i: arr) {
			System.out.print(i + ", ");
		}
		System.out.println();
	}
}
