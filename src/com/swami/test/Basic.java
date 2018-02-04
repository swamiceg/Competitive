//$Id$
package com.swami.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Basic {
	private String fullName = "Swaminathan";
	
	public Basic(int val) {
		
	}
	
	public Basic() {}
	public static void main(String args[]) {
		byte a = -128;
		a = (byte)(a >>> 2);
		int b = 0x7f;
		System.out.println("Hurray!" + a);
		System.out.println(true && false);
		
		if(a == -33 & getBool()) {
			System.out.println("Inside if");
		}
		boolean bool = a > 0?(getBool()):true;
		
		Stack<Integer> stk = new Stack<>();
		System.out.println(stk.pop());
		stk.push(123);
		stk.push(456);
		System.out.println(stk.pop());
		System.out.println(stk.pop());
		System.out.println(stk.pop());
		
		java.util.Stack<Integer> stt = new java.util.Stack<Integer>();
		ArrayList<String> ls = new ArrayList<String>();
		
		InnerClass ic = new Basic(1).new InnerClass();
		ic.method();
		
		StaticNested sn = new StaticNested();
		sn.method();
		
		new com.swami.test.InnerClass(12);
		
		
		Map<String, String> map = new HashMap<String, String>();
		Set<Map.Entry<String, String>> entrySet = map.entrySet();
		for(Map.Entry<String, String> me: entrySet) {
			System.out.println(me.getKey() + " : " + me.getValue());
		}
	}
	
	private static boolean getBool() {
		System.out.println("Executed");
		return true;
	}
	
	protected void method() {
		System.out.println("Basic method");
	}
	
	//Non-static nested class is Inner class
	//Doesn't exist without existence of outer class object
	class InnerClass {
		private String name;
		public static final String age = "12";
		private void method() {
			this.name = fullName;
			Basic.this.method();
			System.out.println(Basic.this.fullName);
		}
		
		public void notPossible() {
			
		}
	}
	
	public static class StaticNested {
		private String name;
		
		public void method() {
			System.out.println("Static nested method: " + new Basic(1).fullName);
		}
		
		public static void method1() {
			
		}
	}
}

class InnerClass extends Basic {
	public InnerClass(int arg) {
		//super(1);
	}
	public void methid() {
		method();
	}
	
	public void method() {
		return;
	}
}

class Stack<T> {
	private final static Integer MAX = 100;
	private Object[] arr = null;
	private int curr = 0;
	
	public Stack() {
		arr = new Object[MAX];
	}
	
	public void push(T ele) {
		if(curr == MAX) {
			System.out.println("Overflow!");
			return;
		}
		arr[curr++] = ele;
	}
	
	public T pop() {
		if(curr == 0) {
			System.out.println("Underflow!");
			return null;
		}
		@SuppressWarnings("unchecked")
		T ele = (T)arr[--curr];
		return ele;
	}
}

class Human implements Animal, Mammal, Mammal.Another {
	//if the both interface have same method name, but with different type signature,
	//we don't have a problem as it will be method overloading
	//if they've same signature and same return type, then only one method will be implemented, more like method overriding,
	//if they've different return type, then it results in Compile time error. neither overloading nor overriding
	@Override
	public void method() {	
	}

	@Override
	public void test() {
		// TODO Auto-generated method stub
		
	}
	
//	@Override
//	public void method(int feed) {
//	}
	
	@Override
	public void def() {
		
	}
}

interface Animal {
	public final static String NAME ="Unknown";
	public void method();
	default void def() {
		
	}
}

interface Mammal {
	public void method();
	interface Another {
		public void test();
	}
	
	default void def() {
		Exception ex = new RuntimeException();
		Throwable t = ex;
	}
	
	public strictfp static void staticMethod() {
		
	}
}

class DummyStack<T> {
	
	private Object[] arr = null;
	private static final int MAX = 100;
	private int size = 0;
	public DummyStack() {
		arr = new Object[MAX];
	}
	
	public T pop() {
		if(size == 0) {
			throw new IllegalArgumentException("Underflow");
		}
		
		return (T)arr[size--];
	}
	
	public void push(T ele) {
		if(size == MAX-1) {
			throw new IllegalArgumentException("Overflow");
		}
		arr[size++] = ele;
	}
	
	public int size() {
		return size;
	}
}


