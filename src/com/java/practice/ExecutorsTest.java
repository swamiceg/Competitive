package com.java.practice;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExecutorsTest {

	public static void main(String args[]) {
		System.out.println("Executor service");
		
		Executor executor = Executors.newFixedThreadPool(4);
		
		ExecutorService es = null;
		ScheduledExecutorService ses = null;
		ThreadPoolExecutor tpe = new ThreadPoolExecutor(3, 3, 12, TimeUnit.MINUTES, new LinkedBlockingQueue<>());
		ScheduledThreadPoolExecutor stpe = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(11);
		
		es = Executors.newFixedThreadPool(2);
		Callable<String> callable = new Callable<String>() {
			private int count = 0;
			@Override
			public String call() throws Exception {
				String text = "Swami"+count;
				System.out.println("Running callable.. " + count++);
				TimeUnit.MILLISECONDS.sleep(1000);
				return text;
			}
		};
		//stpe.schedule(command, delay, unit)
		Future<String> ft1 = es.submit(callable);
		Future<String> ft2 = es.submit(callable);
		Future<String> ft3 = es.submit(callable);
		
		try {
			System.out.println(ft1.get());
			System.out.println(ft2.get());
			System.out.println(ft3.get());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		es.shutdown();
		
		LinkedBlockingQueue<String> lbq = new LinkedBlockingQueue<>();
		lbq.offer("swami");
		try {
			lbq.put("Swami");
			lbq.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Lock lock = new ReentrantLock();
		lock.lock();
		lock.newCondition();
		
		Timer timer = new Timer(true);
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
		}, 11, 11);
	}
	
}
