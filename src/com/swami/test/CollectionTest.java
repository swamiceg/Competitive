//$Id$
package com.swami.test;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class CollectionTest {

	public static void main(String args[]) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("samekey", "dsfdsf");
		map.put("samekey", "dsfdsf");
		
		HashMap<HashKey, String> keyTest = new HashMap<HashKey, String>();
		HashKey k = new HashKey(1000);
		HashKey k1 = new HashKey(1000);
		keyTest.put(k, "Swami");
		keyTest.put(k1, "Nathan");
		keyTest.get(k);
		
		for(Map.Entry<HashKey, String> me: keyTest.entrySet()) {
			System.out.println(me.getKey() + " : " + me.getValue());
		}
		
		List<String> ls = new ArrayList<String>();
		ls.add("swami");
		
		List<String> ll = new LinkedList<String>();
		ll.add("sw");
	
		
		List<String> custom = new SwamiList<String>();
		custom.add("Swami");
		custom.add("Nathan");
		for(String str: custom) {
			System.out.println(str);
		}
		
		Set<String> set = new HashSet<String>();
		set.add("swami");
		set = new TreeSet<String>();
		
		Map<HashKey, String> treeMap = new TreeMap<HashKey, String>();
		treeMap.put(k, "Swami");
		treeMap.put(k1, "Nathan");
		for(Map.Entry<HashKey, String> me: treeMap.entrySet()) {
			System.out.println(me.getKey() + " : " + me.getValue());
		}
		
	}
}

/**
 * When using your custom class as a Key in Hash based datastructures such as HashMap, HashSet, HashTable
 * Consider two things,
 * 1. Equals must be overridden to properly check two different objects of same type
 * 2. HashCode should be overridden to be in contract with equals comparision
 * 
 * If two objects are equal, then their hashcodes will be same. But the vice-versa is not true i.e., if hashcodes are equal that doesn'n mean that
 * the two objects are equal
 */
class HashKey implements Comparable<HashKey> {
	private Integer id;
	public HashKey(int id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == null || !(other instanceof HashKey)) {return false;}
		HashKey o = (HashKey)other;
		return o.id == this.id;
	}
	
	@Override
	public int hashCode() {
		return this.id;
	}

	@Override
	public int compareTo(HashKey o) {
		return this.id.compareTo(o.id);
	}
}


class SwamiList<E> extends AbstractList<E> implements List<E> {
	private int size;
	private Object[] data;
	
	public SwamiList(int initialCapacity) {
		if(initialCapacity == 0) {
			this.data = new Object[10];
		} else if(initialCapacity > 0) {
			this.data = new Object[initialCapacity];
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	public SwamiList() {
		this.data = new Object[10];
	}
	
	public boolean add(E ele) {
		checkCapacity();
		this.data[size++] = ele;
		return true;
	}
	
	private void checkCapacity() {
		if(data.length == size) {
			Object[] copy = new Object[data.length*2];
			System.arraycopy(data, 0, copy, 0, data.length);
			this.data = copy;
		}
	}

	@Override
	public E get(int index) {
		rangeCheck(index);
		return getData(index);
	}

	@SuppressWarnings("unchecked")
	private E getData(int index) {
		return (E)data[index];
	}

	@Override
	public int size() {
		return size;
	}
	
	
	private void rangeCheck(int index) {
		if(index >= size) {
			throw new IllegalArgumentException();
		}
	}
}
