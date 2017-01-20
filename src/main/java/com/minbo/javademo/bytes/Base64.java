package com.minbo.javademo.bytes;

import java.math.BigInteger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64 {

	public static void main(String[] args) throws Exception {
		String s = "我爱你，艾臣";
		System.out.println("转换前：" + s);

		String encode = base64Encode(s.getBytes());
		System.out.println("转换后：" + encode);

		System.out.println("解码后：" + new String(base64Decode(encode)));
	}

	/**
	 * 将byte[]转为各种进制的字符串
	 * @param bytes byte[]
	 * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
	 * @return 转换后的字符串
	 */
	public static String binary(byte[] bytes, int radix) {
		return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
	}

	/**
	 * base 64 encode
	 * @param bytes 待编码的byte[]
	 * @return 编码后的base 64 code
	 */
	public static String base64Encode(byte[] bytes) {
		return new BASE64Encoder().encode(bytes);
	}

	/**
	 * base 64 decode
	 * @param base64Code 待解码的base 64 code
	 * @return 解码后的byte[]
	 * @throws Exception
	 */
	public static byte[] base64Decode(String base64Code) throws Exception {
		return base64Code == null ? null : new BASE64Decoder().decodeBuffer(base64Code);
	}

}
