package com.minbo.javademo.bytes;

public class Test7 {

	/*
	 * Java的Int是32位的，且最高位是符号位，所以超过2的31次方的数字Java就认为是负数，
	 * 由于Java没有Unsigned所以只能用64位的long来表示更大的数字 
	 */
	public static void main(String[] args) {
		//Java中负数转无符号整数
		int value = -3; 
		long unsignedValue = value & Integer.MAX_VALUE; 
		unsignedValue |= 0x80000000L;
		
		System.out.println("unsignedValue=" + unsignedValue);
		
		Test5 t = new Test5();
		byte[] b3 = t.long2byte(unsignedValue);
		for (int i = 0; i < b3.length; i++) {
			System.out.print(b3[i] + " ");
		}
		
		System.out.println();
		
		long s = t.byte2long(b3);
		System.out.println("s=" + s);
		
	}

}
