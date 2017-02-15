package com.minbo.javademo.java8;

public class RunnableTest {

	public static void main(String[] args) {
		/*
		 * 方式1：JAVA8
		 * Lambda表达式允许直接以内联的形式为函数式接口的抽象方法提供实现，
		 * 并把整个表达式作为函数式接口的实例。具体来说，是函数式接口一个具体实现的实例
		*/
		Thread t1 = new Thread(() -> System.out.println("hello world 111, " + Thread.currentThread().getName()));
		
		/*
		 * 方式2：内部类也可以做同样的事情，只是比较笨拙：需要提供一个实现，然后再直接内联将它实例化
		*/
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("hello world 222, " + Thread.currentThread().getName());
			}
		});
		                                                                                                        
		t1.start();
		t2.start();
		
		System.out.println(Thread.currentThread().getName());
	}
}
