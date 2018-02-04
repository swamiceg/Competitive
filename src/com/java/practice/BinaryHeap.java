//$Id$
package com.java.practice;

public class BinaryHeap { //Max Heap

	private static final int MAX_SIZE=20;
	private Node[] arr = new Node[20];
	private int size = 0;

	public static void main(String[] args) throws Exception {
		System.out.println("Binary Heap!!");
		
		BinaryHeap maxHeap = new BinaryHeap();
		maxHeap.insertNode(new Node(3));
		maxHeap.insertNode(new Node(2));
		maxHeap.insertNode(new Node(1));
		maxHeap.insertNode(new Node(15));
		maxHeap.insertNode(new Node(5));
		maxHeap.insertNode(new Node(4));
		maxHeap.insertNode(new Node(45));
		
		System.out.println(maxHeap.deleteNode());
		System.out.println(maxHeap.deleteNode());
		System.out.println(maxHeap.deleteNode());
		
		maxHeap.insertNode(new Node(100));
		maxHeap.printArr();
		System.out.println(maxHeap.deleteNode(3));
		maxHeap.printArr();
		
		System.out.println(maxHeap.deleteNode());
		System.out.println(maxHeap.deleteNode());
		System.out.println(maxHeap.deleteNode());
		maxHeap.printArr();
	}
	
	public void insertNode(Node node) {
		if(size == MAX_SIZE) {
			throw new StackOverflowError();
		}
		arr[size++] = node;
		int i = size-1;
		int parent = parent(i);
		while(i > 0 && arr[parent].ele < node.ele) {
			arr[i] = arr[parent];
			i = parent;
			parent = parent(i);
		}
		if(i != size-1) {
			arr[i] = node;
		}
	}
	
	public Node deleteNode() throws Exception {
		if(size == 0) {
			throw new Exception("Underflow");
		}
		Node ret = arr[0];
		arr[0] = arr[size-1];
		size--;
		
		//MaxHeapify
		int curr = 0;
		while(curr < size) {
			int toSwap = curr;
			if(left(curr) < size && arr[left(curr)].ele > arr[toSwap].ele) {
				toSwap = 2*curr+1;
			}
			if(right(curr) < size && arr[right(curr)].ele > arr[toSwap].ele) {
				toSwap = 2*curr+2;
			}
			if(toSwap != curr) {
				swap(toSwap, curr);
				curr = toSwap;
			} else {
				break;
			}
		}
		
		return ret;
	}
	
	public Node deleteNode(int i) throws Exception {
		if(i > size-1) {
			throw new Exception("Invalid index");
		}
		Node del = arr[i];
		int parent = parent(i);
		while(i > 0) {
			arr[i] = arr[parent];
			i = parent;
			parent = parent(i);
		}
		arr[0] = del;
	
		return deleteNode();
	}
	
	public Node getMax() {
		return arr[0];
	}
	
	private void swap(int i, int j) {
		Node temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
	private int parent(int index) {
		return (index-1)/2;
	}
	
	private int left(int index) {
		return 2*index+1;
	}
	
	private int right(int index) {
		return 2*index+2;
	}
	
	private void printArr() {
		for(int i = 0; i < size; i++) {
			System.out.print(arr[i] + ", ");
		}
		System.out.println();
	}
}

class Node {
	int ele;
	Node left;
	Node right;
	
	public Node(int v) {
		this.ele = v;
	}
	
	public String toString() {
		return String.valueOf(ele);
	}
}
