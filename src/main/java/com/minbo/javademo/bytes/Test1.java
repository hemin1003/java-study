package com.minbo.javademo.bytes;

public class Test1 {

	public static void main(String[] args) {
		System.out.println("双字节计算方式：");
		System.out.println();
		
		String s = "10";
		System.out.println("十进制值");
		System.out.println("字符串值=" + s);
		System.out.println();
		
		System.out.println("int转成二进制的字符串值");
		String i = Integer.toBinaryString(Integer.valueOf(s));
		System.out.println(i);
		
		System.out.println();
		
		System.out.println("int转成八进制的字符串值");
		String i2 = Integer.toOctalString(Integer.valueOf(s));
		System.out.println(i2);
		
		System.out.println();
		
		System.out.println("int转成十六进制的字符串值");
		String i3 = Integer.toHexString(Integer.valueOf(s));
		System.out.println(i3);
	}
}
