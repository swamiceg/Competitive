package com.java.practice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.java.practice.ExceptionTest.Singleton.Test;

public class ExceptionTest {

	public static void main(String args[]) throws Exception {
		System.out.println("Running!");
		new ExceptionTest().exception();
				
		RunnableClass rc = new RunnableClass(Singleton.INSTANCE);
		Thread t = new Thread(rc);
		Thread t1 = new Thread(rc);
		t.start();
		t1.start();
		
		
		//Enum in collection
		Set<Singleton> es = new HashSet<>();
		es.add(Singleton.INSTANCE);
		es.add(Singleton.INSTANCE);
		System.out.println(es);
		EnumSet<Singleton> enumSet = EnumSet.of(Singleton.INSTANCE);
		enumSet.add(Singleton.INSTANCE);
		System.out.println(enumSet);
		
		//Every class has an class object, which can be directly accessed as String.class, Long.class. This class object is static and is of type Class<?>
		System.out.println(String.class);
		System.out.println(new String().getClass());
		new ExceptionTest().check();
		
		System.out.println(Singleton.valueOf("INSTANCE").compareTo(Singleton.INSTANCE)); //ordinal comparision
		System.out.println(Test.HELLO);

	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD, ElementType.TYPE})
	@interface DummyAnnotation {
		String value();
	}
	
	@DummyAnnotation("Swami Annotate")
	public void check() throws Exception {
		List<String> ls = null;
		try {
			ls = getList("swami,split,nathan", String.class);
			System.out.println(ls);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Long l = new Long("123");
		System.out.println(Long.getLong("122b"));
		Long.toString(123);
		l.doubleValue();
		l.intValue();
		
		Boolean bool = true;
		Integer i = 100;
		wrapper(i);
		
		Class<?> c = this.getClass();
		Method method = c.getMethod("check");
		DummyAnnotation annot = method.getAnnotation(DummyAnnotation.class);
		System.out.println(annot.value());
	}
	
	private void wrapper(int s) {
		System.out.println(s);
	}
	
	public void exception() {
		File file = new File("src/com/java/practice/ExceptionTest.java");
		FileInputStream fis = null;
		try {
			System.out.println(file.getAbsolutePath());
			 fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		try(FileInputStream fis1 = new FileInputStream(file)) {
			
		} catch(IOException | ArithmeticException  e) {
			
		}
		
		
	}
	
	public enum Singleton {
		INSTANCE,
		INSTANCE2;
		Singleton() {
			Enum<Singleton> en;
		}
		
		public void printHash() {
			System.out.println("HC: " + hashCode());
		}
		
		private void dummy() {
			Singleton st = Singleton.INSTANCE;
			switch(st) {
				case INSTANCE: {
					System.out.println("Hurray!");
				}
			}
		}
		
		@Override
		public String toString() {
			valueOf("INSTANCE");
			return name().toLowerCase();
		}
		
		enum Test {
			HELLO;
		}
	}
	
	private static class RunnableClass implements Runnable {
		private Singleton stat;
		private RunnableClass(Singleton stat) {
			this.stat = stat;
		}
		
		@Override
		public void run() {
			int i = 0;
			do {
				stat.printHash();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while(++i < 1);
			
		}
		
		public static void statMethod() {
			
		}
	}
	
	
	public <T> List<T> getList(String str, Class<T> t) throws Exception {
		Constructor<T> cons = t.getConstructor(String.class);
		List<T> ls1 = new ArrayList<>();
		for(String s: str.split(",")) {
			ls1.add(cons.newInstance(s));
		}
		

		return ls1;
	}

 }

interface ImplementMe {
	public String VARIABLE = "Swami";
	
	public static void method() {
		
	}
	
}
