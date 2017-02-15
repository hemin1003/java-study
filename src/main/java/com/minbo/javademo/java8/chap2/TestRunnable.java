package com.minbo.javademo.java8.chap2;

public class TestRunnable {

	public static void main(String[] args) {
		Runnable r1 = () -> System.out.println("hello 1");
		Runnable r2 = new Runnable() {
			@Override
			public void run() {
				System.out.println("hello 2");
			}
		};
		
		TestRunnable.process(r1);
		TestRunnable.process(r2);
		TestRunnable.process(() -> System.out.println("hello 3"));
	}
	
	//行为参数化
	public static void process(Runnable r){
		r.run();
	}
}