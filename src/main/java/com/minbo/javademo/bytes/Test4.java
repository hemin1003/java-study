package com.minbo.javademo.bytes;

public class Test4 {

	public static void main(String[] args) {
		System.out.println("java byte转二进制字符串");
		
		// 11111111111111111111111111111110 双字节
		// 11111110 单字节1111 1111 & 0xAA = 
		byte b = -1;   //1010 1010
		b = (byte) (b & 0xAA);
		System.out.println(b);
		String s = Integer.toBinaryString((b & 0xFF) + 0x100).substring(1);
		System.out.println(s);
		
		System.out.println();
		
		System.out.println("将byte[]转化十六进制的字符串");
		byte[] b2 = new byte[]{-24, -76, -70, -26, -107, -113};
		System.out.println(Test4.bytes2HexString(b2));
	}

	/**
	 * 将byte[]转化十六进制的字符串
	 */
	public static String bytes2HexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = "0" + hex;
			}
			ret += hex.toUpperCase();
		}
		return ret;
	}
}
