package com.minbo.javademo.bytes;

public class Test6 {

	public static void main(String[] args) {
		String s1 = "贺敏";
		byte[] b1 = s1.getBytes();
		for (byte b : b1) {
			System.out.println(b);
		}
		
		System.out.println();
		
		byte[] b2 = new byte[]{-24, -76, -70, -26, -107, -113};
		String s = new String(b2);
		System.out.println(s);
	}
	
}