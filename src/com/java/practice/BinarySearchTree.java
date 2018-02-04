//$Id$
package com.java.practice;

import java.util.LinkedList;
import java.util.Stack;

public class BinarySearchTree {
	private TreeNode root = null;
	
	public void insert(int val) {
		TreeNode node = new TreeNode(val);
		if(root == null) {
			root = node;
			return;
		}
		
		TreeNode curr = root;
		while(curr != null) {
			if(curr.ele == val) {
				throw new IllegalArgumentException("Duplicate Value");
			} else if(val > curr.ele) {
				if(curr.right == null) {
					curr.right = node;
					break;
				} else {
					curr = curr.right;
				}
			} else {
				if(curr.left == null) {
					curr.left = node;
					break;
				} else {
					curr = curr.left;
				}
			}
		}
	}
	
	public TreeNode deleteNode(int val) {
		return deleteNode(root, val);
	}
	
	private TreeNode deleteNode(TreeNode node, int key) {
		if(node == null) {
			return node;
		}
		
		if(key < node.ele) {
			node.left = deleteNode(node.left, key);
		} else if(key > node.ele) {
			node.right = deleteNode(node.right, key);
		} else {
			//Node to delete
			if(node.left == null) {
				return node.right;
			} else if(node.right == null) {
				return node.left;
			}
			
			//Both children exist
			//Find the min value in right subtree and exchange with the curr node
			TreeNode minNode = getMin(node.right);
			node.ele = minNode.ele;
			node.right = deleteNode(node.right, minNode.ele);
		}
		
		return node;
	}
	
	private TreeNode getMin(TreeNode node) {
		TreeNode min = node;
		while(node != null) {
			min = node;
			node = node.left;
		}
		return min;
	}
	
	public TreeNode search(int val) {
		if(root == null || root.ele == val) {
			return root;
		}
		TreeNode curr = root;
		while(curr != null) {
			if(curr.ele == val) {
				return curr;
			} else if(val > curr.ele) {
				curr = curr.right;
			} else {
				curr = curr.left;
			}
		}
		return null;
	}
	
	public void inorder() {
		inorder(root);
		System.out.println();
	}
	
	//uses stack
	public void depthFirstSearch() {
		Stack<TreeNode> stack = new Stack<>();
		stack.add(root);
		System.out.println("Depth First Search:");
		while(!stack.isEmpty()) {
			TreeNode node = stack.pop();
			System.out.print(node + ", ");
			if(node.right != null) {
				stack.push(node.right);
			}
			if(node.left != null) {
				stack.push(node.left);
			}
		}
		System.out.println();
	}
	
	//Breadth first search, uses queue
	public void levelOrderTraversal() {
		LinkedList<TreeNode> queue = new LinkedList<>();
		queue.offer(root);
		
		while(!queue.isEmpty()) {
			TreeNode node = queue.poll();
			System.out.println(node);
			if(node.left != null) {
				queue.offer(node.left);
			}
			if(node.right != null) {
				queue.offer(node.right);
			}
		}
	}
	
	public void printLevelOrder() {
		int height = getTreeHeight(root);
		for(int i = 1; i <= height; i++) {
			printNodesAtLevel(root, i);
			System.out.println();
		}
	}
	
	public void printNodesAtLevel(TreeNode node, int level) {
		if(level == 0) {
			return;
		}
		if(level == 1) {
			System.out.print(node.ele + ", ");
		} else {
			printNodesAtLevel(node.left, level-1);
			printNodesAtLevel(node.right, level-1);
		}
	}
	
	public int getTreeHeight(TreeNode node) {
		if(node == null) {
			return 0;
		} else {
			int lheight = getTreeHeight(node.left);
			int rheight = getTreeHeight(node.right);
			
			if(lheight > rheight) {
				return lheight + 1;
			} else {
				return rheight + 1;
			}
		}
	}
	
	private void inorder(TreeNode node) {
		if(node != null) {
			inorder(node.left);
			System.out.print(node+", ");
			inorder(node.right);
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Binary Search Tree!!");
		BinarySearchTree bst = new BinarySearchTree();
		bst.insert(50);
		bst.insert(30);
		bst.insert(20);
		bst.insert(40);
		bst.insert(70);
		bst.insert(60);
		bst.insert(80);
		
		System.out.println(bst.search(60));
		bst.inorder();
		bst.levelOrderTraversal();
		System.out.println("Level order:");
		bst.printLevelOrder();
		bst.depthFirstSearch();
		
		System.out.println("Tree height: " + bst.getTreeHeight(bst.root));
		bst.deleteNode(30);
		bst.deleteNode(70);
		bst.deleteNode(70);
		bst.inorder();
	}
	
	class TreeNode {
		public int ele;
		public TreeNode left, right;
		public TreeNode(int v) {
			this.ele = v;
		}
		
		public String toString() {
			return String.valueOf(ele);
		}
	}
}
