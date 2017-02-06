package com.minbo.javademo.bytes;

import java.io.ByteArrayInputStream;

public class Test8 {

	public static void main(String[] args) {
//		byte[] bytes = new byte[] { (byte) -50 };
//
//		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
//
//		int result = in.read();
//		System.out.println("无符号数: \t" + result);
//		System.out.println("2进制bit位: \t" + Integer.toBinaryString(result));

		 byte bytes = -50;
		
		 int result = bytes&0xff;
		 System.out.println("无符号数: \t"+result);
		 System.out.println("2进制bit位: \t"+Integer.toBinaryString(result));
		 System.out.println("2进制16进制: \t"+Integer.toHexString(result));
	}

}
