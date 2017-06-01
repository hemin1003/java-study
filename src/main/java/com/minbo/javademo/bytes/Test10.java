package com.minbo.javademo.bytes;

public class Test10 {

	public static void main(String[] args) {
		String s1 = "2017-06-01 09:09:09";
		byte[] b1 = s1.getBytes();
		for (byte b : b1) {
			System.out.print(b + ", ");
		}
		System.out.println();

		byte[] b3 = new byte[] { 0x55 };
		byte[] b2 = byteMerger(b3, b1);
		String s = new String(b2);
		System.out.println(s);

		System.out.println("===========");
		System.out.println(Test4.bytes2HexString(b2));
		System.out.println("===========");
		System.out.println(Test6.hexStringToString(Test4.bytes2HexString(b2)));
	}

	// java 合并两个byte数组
	public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
		byte[] byte_3 = new byte[byte_1.length + byte_2.length];
		System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
		System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
		return byte_3;
	}
}