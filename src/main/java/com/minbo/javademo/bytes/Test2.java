package com.minbo.javademo.bytes;

public class Test2 {
	
	public static void main(String[] args) {
		System.out.println("单字节计算方式：");
		System.out.println(); 
		
		String s = "1010";
		int i = Integer.parseInt(s, 2);
		System.out.println("二进制的字符串值s=" + s + " 转成十进制int的值i=" + i);
		
		System.out.println();
		
		String s2 = "12";
		int i2 = Integer.parseInt(s2, 8);
		System.out.println("八进制的字符串值s2=" + s2 + " 转成十进制int的值i2=" + i2);
		
		System.out.println();
		
		String s3 = "a";
		int i3 = Integer.parseInt(s3, 16);
		System.out.println("十六进制的字符串值s3=" + s3 + " 转成十进制int的值i3=" + i3);
	}
}
