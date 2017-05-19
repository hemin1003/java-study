package com.minbo.javademo.bytes;

public class Test6 {

	public static void main(String[] args) {
		String s1 = "7b0a0922636f6d6d616e64223a092245515549504d454e545f45"
				+ "4e565f53454e534f52222c0a09226465766963654964223a0922363"
				+ "33538222c0a0922747970654964223a09223339222c0a092273656e7"
				+ "36f7254797065223a095b7b0a0909092273756254797065223a09226c"
				+ "756d696e616e6365222c0a0909092276616c7565223a0922322e30222c0a"
				+ "090909226c6576656c223a092230222c0a09090922756e6974223a092230220"
				+ "a09097d5d2c0a092264617465223a0922323031372d30352d31392031353a34303a3030220a7d";
		System.out.println(hexStringToString(s1));
	}

	/**
	 * 16进制字符串转换为字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String hexStringToString(String s) {
		if (s == null || s.equals("")) {
			return null;
		}
		s = s.replace(" ", "");
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "utf-8");
			new String();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}

	/**
	 * 字符串转换为16进制字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String stringToHexString(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return str;
	}
}