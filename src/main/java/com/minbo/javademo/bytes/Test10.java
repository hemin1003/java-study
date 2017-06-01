package com.minbo.javademo.bytes;

public class Test10 {

	public static void main(String[] args) {
		String s1 = "2017-06-01 08:49:30";
		byte[] b1 = s1.getBytes();
		for (byte b : b1) {
			System.out.print(b + " ");
		}
		System.out.println();

//		byte[] b2 = new byte[]{-24, -76, -70, -26, -107, -113};
		byte[] b2 = b1;
		String s = new String(b2);
		System.out.println(s);
	}
}