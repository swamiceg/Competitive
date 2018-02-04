package com.java.practice;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Generics {

	public static void main(String[] args) {
		System.out.println("#### Generics Test ####");
		
		Generic<String> g = new Generic<>("Swami");
		g.print();
		
		Coord<A> coord = new Coord<>(new A[]{new A(10, 20), new B(1, 2, 3)});
		System.out.println(coord);
		
		Coord<B> coordB = new Coord<>(new B[]{new B(1,23,34)});
		Coord.printXY(coord);
		Coord.printXYZ(coordB);
		
		
		//Any subtype of Number/Number itself can be stored in this list
		//But reading can be done only as Object, as any super type of Number can be Stored
		List<? super Number> ls = new ArrayList<Object>();
		ls.add(new Integer(1));
		ls.add(new Double(1));
		System.out.println(ls);
		
		Number num = (Number) ls.get(0);
		System.out.println(num);
		
		//We can't add anything to this list as the actual Object can be Number or any of its subtypes
		//But reading can be done as Number, as it is the Super type for any underlying Objects stored
		List<? extends Number> ls1 = new ArrayList<Integer>();
		ls1.add(null);
		System.out.println(ls1.get(0));
		
		MinMax<Integer> c = new ArrImpl<>(new Integer[]{1, 2, 3});
		System.out.println(c.min());
		System.out.println(c.max());
		
		if(c instanceof MinMax<?>) {}
		//if(c instanceof MinMax<Integer>) {} //Generic information will not be available during runtime, so this will generate compile time error
		
		GenericAmbiguity<String, Integer> ga = new GenericAmbiguity<>();
		ga.set("Swami");
		ga.set(121);
		
		GenericAmbiguity<Integer, Integer> ga1 = new GenericAmbiguity<>();
		//ga1.set(12); //This will become ambigous now
	}
}

//Generic class/Parameterized type
//Ensures type safety during compile time
class Generic<T> {
	T ob;
	public Generic(T t) {
		ob = t;
	}
	
	public T get() {
		return ob;
	}
	
	public void print() {
		System.out.println(String.format("Type: %s; Value: %s", ob.getClass().getSimpleName(), ob));
	}
	
	//type parameter in method
	//upper bound is specified as Number. Accepts only Number or subclasses of Number
	public static <E extends Number> List<E> getList(String str, Class<E> claz) {
		List<E> ls = new ArrayList<>();
		try {
			Constructor<E> cons = claz.getConstructor(String.class);
			E obj = cons.newInstance(str);
			ls.add(obj);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return ls;
	}
	
	public <E extends Serializable & Comparable<E>> void test(E e) {
		
	}
	
	public static <E extends Number> void print(E[] arr) {
		for(E e: arr) {
			System.out.println(e.intValue());
		}
	}
	
	public static <E extends Number> void printer(E e) {

	}
}

class Coord<T extends A> {
	T[] arr;
	public Coord(T[] arr) {
		this.arr = arr;
	}
	
	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		for(T t: arr) {
			buf.append(t.x + "->" + t.y);
			buf.append("\n");
		}
		return buf.toString();
	}
	
	public static void printXY(Coord<?> c) {
		for(int i = 0; i < c.arr.length; i++) {
			System.out.println(c.arr[i].x + "->" + c.arr[i].y);			
		}
	}
	
	public static void printXYZ(Coord<? extends B> c) {
		for(int i = 0; i < c.arr.length; i++) {
			System.out.println(c.arr[i].x + "->" + c.arr[i].y + "->" + c.arr[i].z);
		}
	}
}

class A {
	int x; 
	int y;
	public A(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

class B extends A{
	Integer z;
	
	public B(int x, int y, int z) {
		super(x, y);
		this.z = z;
	}
}

class C extends B {
	int a;
	public C(int x, int y, int z, int a) {
		super(x, y, z);
		this.a = a;
	}
}

class GenericAmbiguity<T, V extends Number> {
	T t; 
	V v;
	public void set(T t) {
		this.t = t;
	}
	
	public void set(V v) {
		this.v = v;
	}
	
	public T getT() {
		return t;
	}
	
	public V getV() {
		return v;
	}
}

class ArrImpl<T extends Comparable<T>> implements MinMax<T> {
	T[] t;
	
	public ArrImpl(T[] t) {
		this.t = t;
	}

	@Override
	public T min() {
		T min = t[0];
		for(int i = 1; i < t.length; i++) {
			if(t[i].compareTo(min) < 0) {min = t[i];}
		}
		return min;
	}
	
	@Override
	public T max() {
		T max = t[0];
		for(int i = 1; i < t.length; i++) {
			if(t[i].compareTo(max) > 0) {max = t[i];}
		}
		return max;
	}	
}

class IntegerImpl implements MinMax<Integer> {

	@Override
	public Integer min() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer max() {
		// TODO Auto-generated method stub
		return null;
	}
	
}

interface MinMax<T extends Comparable<T>> {
	T min();
	T max();
}
