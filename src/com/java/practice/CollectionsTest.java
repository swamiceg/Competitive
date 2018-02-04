package com.java.practice;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Properties;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingQueue;

import com.java.practice.ExceptionTest.Singleton;

public class CollectionsTest {

	public static void main(String args[]) {
		System.out.println("#### Collections ####");
		
		CollectionsTest inst = new CollectionsTest();
		
		int[] ar = new int[12];
		Integer[] aa = new Integer[12];
		for(Integer a: aa) {
			System.out.println(a);
		}
		
		ListIterator<String> li;
		
		List<String> lsStr = new ArrayList<>(13);
		lsStr.add("Swami");
		String[] strArr = new String[lsStr.size()]; 
		lsStr.toArray(strArr);
		for(String l: strArr){System.out.println(l);}
		
		lsStr.add(1, "swsw");
		lsStr.set(1, "dfd");
		System.out.println(lsStr);
		
		Set<String> set = new HashSet<>();
		set.add("Swami");
		set.add("Swami");
		System.out.println(set);
		
		System.out.println("Set test:");
		Set<Employee> empSet = new HashSet<>();
		empSet.add(new Employee(1, "swami"));
		empSet.add(new Employee(1, "nathan"));
		System.out.println(empSet);
		
		System.out.println("Hashmap test:");
		Map<Employee, Employee> hmap = new HashMap<>();
		hmap.put(new Employee(1, "Swami"), new Employee(1, "Swami"));
		hmap.put(new Employee(1, "Swami"), new Employee(1, "SwamiModified"));
		System.out.println(hmap + "\n");
		
		SortedSet<Employee> ss = new TreeSet<>();
		ss.add(new Employee(1, "sw"));
		Map<Employee, String> treeMap = new TreeMap<>();
		treeMap.put(new Employee(2, "Swami"), "Swami");
		treeMap.put(new Employee(1, "Swami"), "Swami");
		System.out.println(treeMap);
		
		System.out.println("Enum Map:");
		Map<Singleton, String> test = new HashMap<>();
		test.put(Singleton.INSTANCE, "Swami");
		test.put(Singleton.INSTANCE2, "Nathan");
		System.out.println(test);
		System.out.println(Singleton.INSTANCE.hashCode() + " == " + Singleton.INSTANCE2.hashCode());
		
		inst.queueing();
		inst.list();
		inst.set();
		inst.collections();
	}
	
	
	public void queueing() {
		Queue<Integer> queue = new ArrayDeque<>();
		queue.add(1);
		queue.add(2);
		
		System.out.println(queue.poll());
		for(Integer q: queue) {
			System.out.println(q);
		}
		
		Deque<Integer> deque = new ArrayDeque<>();
		deque.offer(1);
		deque.offer(2);
		System.out.println(deque.removeFirst());
		System.out.println(deque.removeLast());
		System.out.println(deque);
		deque.push(3);
		deque.push(4);
		System.out.println("Pop:" + deque.pop());
		
		PriorityQueue<Employee> pq = new PriorityQueue<>();
		pq.offer(new Employee(1, "Swami"));
		pq.offer(new Employee(2, "Nathan"));
		System.out.println("PQ Impl:");
		for(Employee emp: pq) {
			System.out.println(emp);
		}
	}
	
	public void list() {
		List<String> ls = new ArrayList<>();
		ls.add("Swa");
		ls.add("sdfds");
		String[] arr = new String[ls.size()];
		arr = ls.toArray(arr);
		
		System.out.println("Iterator test:");
		Iterator<String> iter = ls.iterator();
		while(iter.hasNext()) {
			System.out.println(iter.next());
		}
		System.out.println("After loop:");
		iter = ls.iterator();
		while(iter.hasNext()) {
			System.out.println(iter.next());
		}
		
		System.out.println("Printing in reverse with ListIterator:");	
		ListIterator<String> liter = ls.listIterator(ls.size());
		while(liter.hasPrevious()) {
			System.out.println(liter.previous());
		}
		
		List<String> s = Arrays.asList(arr);
		
		Employee emp = new Employee(1, "Swami");
		Employee[] earr = new Employee[]{emp};
		Employee[] darr = earr;
		
		Employee[] carr = new Employee[earr.length];
		for(int i = 0; i < earr.length; i++) {
			carr[i] = earr[i];
		}
		
		System.out.println("Employee unmod: " + earr[0]);
		darr[0].name = darr[0].name+"mod1";
		System.out.println("Employee mod1: " + earr[0]);
		carr[0].name = darr[0].name+"mod2";
		System.out.println("Employee mod1: " + earr[0]);
	}
	
	public void set() {
		LinkedHashSet<String> lhs = new LinkedHashSet<>();
		lhs.add("Order1");
		lhs.add("Order2");
		System.out.println(lhs);
		
		Map<String, Integer> linkedHashMap = new LinkedHashMap<>();
		
		EnumSet<Singleton> enumSet = EnumSet.noneOf(Singleton.class);
		enumSet.add(Singleton.INSTANCE);
		System.out.println(enumSet);
	}
	
	private void collections() {
		/*
		 * checkedCollection, checkedList, checkedSet etc., - Provides runtime type-safe view of the underlying collection
		 * synchronizedCollection, synchronizedList, synchronizedSet etc.,
		 * unmodifiabledCollection, unmodifiableList etc.,
		 * sort, min, max
		 */
		Objects.requireNonNull("");
		
		Set<String> set = Collections.singleton(new String("swami"));
		System.out.println("Set class:" + set.getClass());
		String[] strArr = new String[12];
		System.out.println("StrArr class:" + strArr.getClass().getComponentType());
		System.out.println("StrArr class componenttype:" + strArr.getClass().getComponentType());
		
		String[] copy = Arrays.copyOf(new String[]{"Swami", "Nathan"}, 12);
		for(String s: copy) {System.out.print(s+" ");}
		System.out.println();
		
		Collections.checkedSet(set, String.class);
		Collections.synchronizedSet(set);
		Collections.unmodifiableSet(set);
		
		Properties props = new Properties();
		props.put(1, "Swami");
		System.out.println(props.get(1));

	}
	
	static class Employee implements Comparable<Employee> {
		private int id;
		private String name;
		
		public Employee(int id, String name) {
			this.id = id;
			this.name = name;
		}
		
		@Override
		public boolean equals(Object o) {
			if(o == null || !(o instanceof Employee)) {return false;}
			Employee a = (Employee) o;
			return a.id == this.id;
		}
		
		@Override
		public int hashCode() {
			return this.id;
		}
		
		@Override
		public String toString() {
			return this.id + ":" + this.name;
		}

		//@Override
		public int compareTo(Employee o) {
			return this.id - o.id;
		}
	}
}
