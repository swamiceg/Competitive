package com.java.practice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class Threading {

	private static int count = 0;
	private static final Pattern pattern = Pattern.compile("^[^a-zA-Z]+");
	public static void main(String args[]) {
		System.out.println("### Threading ###");
		
		System.out.println(pattern.matcher("01 -  Lose Yourself.flac").find());
		
		Path  path = Paths.get("E:\\Music");
		File file = path.toFile();
		//listFiles(file);
		//System.out.println("Total files: " + count);
		
		Thread ct = Thread.currentThread();
		System.out.println("Main Thread: " + ct);
		
		DummyRunnable dr = new Threading().new DummyRunnable();
		dr.start();
		DummyThread dt = new Threading().new DummyThread();
		
		for(int i = 0; i < 5; i++) {
			try {
				System.out.println(ct.getName() + " sleeping. Counter: " + (i+1));
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			dr.th.join();
			System.out.println("Out of DR join");
			dt.join();
			System.out.println("Out of DR join");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Main Thread exiting...!!!");
		
		Threading inst = new Threading();
		Goods goods = inst.new Goods();
		Producer producer = inst.new Producer(goods);
		Consumer consumer = inst.new Consumer(goods);
		producer.start();
		consumer.start();
		
		//Group of threads working on some activities, may have to wait until all the other threads
		//have completed their task before proceeding further. CyclicBarrier can be used for that purpose.
		CyclicBarrier cb = new CyclicBarrier(2, new Runnable() {
			@Override
			public void run() {
				System.out.println("Cyclic barrier action execution...");
			}
		});
		new CBTest(cb).start();
		new CBTest(cb).start();
		
		CountDownLatch cdl = new CountDownLatch(5);
		new CDLTest(cdl).start();
		new CDLTest(cdl).start();
		try {
			System.out.println("Waiting for threads to call CDL");
			cdl.await();
			System.out.println("Out of CDL wait");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Exchanger<Object> exchanger = null;
		
		final Phaser phaser = new Phaser(1);
		new PhaserTest(phaser).start();
		new PhaserTest(phaser).start();
		new PhaserTest(phaser).start();
		
		int phase = phaser.arriveAndAwaitAdvance();
		System.out.println("Completed phase " + phase);
		phase = phaser.arriveAndAwaitAdvance();
		System.out.println("Completed phase " + phase);
		phase = phaser.arriveAndAwaitAdvance();
		System.out.println("Completed phase " + phase);}

	private static void listFiles(File file) {
		if(file.isFile()) {
			count++;
			String name = file.getName();
			if(pattern.matcher(name).find()) {
				String newName = name.replaceAll(pattern.pattern(), "");
				File newFile = new File(file.getParentFile(), newName);
				file.renameTo(newFile);
			}
		} else if(file.isDirectory()){
			for(File f: file.listFiles()) {
				listFiles(f);
			}
		}
	}
	
	class DummyThread extends Thread {
		public DummyThread() {
			super("DummyThread");
			start();
		}
		
		@Override
		public void run() {
			System.out.println("DummyThread starting.. " + getName());
			for(int i = 0; i < 5; i++) {
				try {
					System.out.println(getName() + " sleeping. Counter: " + (i+1));
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("DummyThread ending.. " + getName());
		}
	}
	
	class DummyRunnable implements Runnable {
		Thread th = null;
		public DummyRunnable() {
			th = new Thread(this, "DummyRunnable");
		}
		
		public void start() {th.start();}
		
		@Override
		public void run() {
			System.out.println("DummyRunnable starting.. " + th.getName());
			for(int i = 0; i < 5; i++) {
				try {
					System.out.println(th.getName() + " sleeping. Counter: " + (i+1));
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("DummyRunnable ending.. " + th.getName());
		}	
	}
	
	class Goods {
		private AtomicInteger item = new AtomicInteger(0);
		private volatile boolean produced = false;
		private final int max = 5;
		private final AtomicInteger MAX = new AtomicInteger(0);
		private int prodPointer = 0, consPointer = 0;
		LinkedList<String> items = new LinkedList<>();
		public Goods() {
			//this.MAX = new AtomicInteger(max);
		}
		
		public synchronized String get() {
			while(MAX.get() == 0) { //to handled spurious wakeup
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			String consumed = items.poll();//items.get(consPointer++%max);
			//produced = false;
			MAX.decrementAndGet();
			notify();
			return consumed;
		}
		
		public synchronized String put() {
			while(MAX.get() == max) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			String prod = incrAndGetItem();
			MAX.incrementAndGet();
			items.offer(prod);
			//items.add((prodPointer++)%max, prod);
			//produced = true;
			notify();
			return prod;
		}
		
		private String incrAndGetItem() {
			return "Item - " + item.incrementAndGet();
		}
	}
	
	class Producer implements Runnable {
		private Goods goods;
		private Thread th;
		public Producer(Goods goods) {
			this.goods = goods;
			this.th = new Thread(this, "Producer");
		}
		
		public void start() {
			th.start();
		}
		
		@Override
		public void run() {
			int count = 100;
			do {
				System.out.println("Produced: " + goods.put());
			} while(count-- > 0);
		}
	}
	
	class Consumer implements Runnable {
		private Goods goods;
		private Thread th;
		public Consumer(Goods goods) {
			this.goods = goods;
			this.th = new Thread(this, "Consumer");
		}
		
		public void start() {
			th.start();
		}
		
		@Override
		public void run() {
			int count = 100;
			do {
				System.out.println("Consumed: " + goods.get());
			} while(count-- > 0);
		}
	}
	
	static class CBTest implements Runnable {
		private CyclicBarrier cb;
		private Thread t;
		private static int count = 1;
		public CBTest(CyclicBarrier cb) {
			t = new Thread(this, "CyclicBarrier"+count++);
			this.cb = cb;
		}
		
		public void start() {	
			t.start();
		}
		
		@Override
		public void run() {
			try {
				System.out.println("Running CB Thread " + t.getName());
				Thread.sleep(1000);
				cb.await();
			} catch(InterruptedException | BrokenBarrierException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	static class CDLTest implements Runnable {
		private CountDownLatch cdl;
		private Thread t;
		public CDLTest(CountDownLatch cdl) {
			this.cdl = cdl;
			t = new Thread(this);
		}
		public void start() {t.start();}
		
		@Override
		public void run() {
			try {
				System.out.println("Running CDL Thread " + t.getName());
				Thread.sleep(1000);
				for(int i = 0; i < 5; i++)
					cdl.countDown();
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	static class PhaserTest implements Runnable {
		private Phaser phaser;
		private Thread t;
		public PhaserTest(Phaser phaser) {
			this.phaser = phaser;
			phaser.register();
			t = new Thread(this);
		}
		public void start() {t.start();}
		
		@Override
		public void run() {
			System.out.println("Starting phase 1");
			phaser.arriveAndAwaitAdvance();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Starting phase 2");
			phaser.arriveAndAwaitAdvance();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Starting phase 3");
			phaser.arriveAndAwaitAdvance();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Done!");
		}
	}
}
