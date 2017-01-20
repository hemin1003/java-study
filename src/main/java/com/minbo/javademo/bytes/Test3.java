package com.minbo.javademo.bytes;

public class Test3 {

	public static void main(String[] args) {
		System.out.println("单字节计算方式：");
		System.out.println(); 
		
		int i = 10;
		
		String s = Integer.toString(i, 2);
		System.out.println("十进制的int值i=" + i + " 转成二进制对应的字符串值s=" + s);
		System.out.println(); 
		
		String s1 = Integer.toString(i, 10);
		System.out.println("十进制的int值i=" + i + " 转成十进制对应的字符串值s1=" + s1);
		System.out.println(); 
		
		String s2 = Integer.toString(i, 8);
		System.out.println("十进制的int值i=" + i + " 转成八进制对应的字符串值s2=" + s2);
		System.out.println(); 
		
		String s3 = Integer.toString(i, 16);
		System.out.println("十进制的int值i=" + i + " 转成十六进制对应的字符串值s3=" + s3);
		System.out.println(); 
		
	}
}
