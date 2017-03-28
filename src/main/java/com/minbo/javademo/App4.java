package com.minbo.javademo;

public class App4 {

	static int counter = 0;

	public static void main(String[] args) {
		for (int i = 0; i < 4; i++) {
			try {
				test(i);
			} catch (Exception e) {
				
			}
		}
		System.out.println(counter);
	}

	public static int test(int i) {
		try {
			int a = 2 / i;
			if (a > 1) {
				return a;
			}
		} catch (Exception e) {
			throw new RuntimeException();
		} finally {
			counter++;
		}
		return 0;
	}
}
