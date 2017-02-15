package com.minbo.javademo.java8.chap3;

public class TestVariable {
	public static int test = 200;

	public static void main(String[] args) {
		int portNumber = 100;
		Runnable r = () -> {test=2;System.out.println(portNumber);System.out.println(test);};
		r.run();
		//portNumber = 101;
	}

}
