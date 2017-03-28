package com.minbo.javademo.test;

public class TestFinally {

	public static int aMethod(int i) throws Exception {
		try {
			return i / 10;
		} catch (Exception ex) {
			throw new Exception("exception in a Method");
		} finally {
			System.out.printf("finally ");
		}
	}

	public static void main(String[] args) {
		try {
			aMethod(0);
		} catch (Exception ex) {
			System.out.printf("exception in main");
		}
		System.out.printf("finished ");
	}
	
//	1、finally块一定会执行，无论是否try…catch。
//	2、finally前有return，会先执行return语句，并保存下来，再执行finally块，最后return。
//	3、finally前有return、finally块中也有return，先执行前面的return，保存下来，再执行finally的return，覆盖之前的结果，并返回。
}
