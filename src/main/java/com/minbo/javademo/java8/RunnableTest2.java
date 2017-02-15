package com.minbo.javademo.java8;

public class RunnableTest2 {

	public static void main(String[] args) {
		final int portNumber = 123;
		Runnable r = () -> System.out.println(portNumber);
		r.run();
	}
}
