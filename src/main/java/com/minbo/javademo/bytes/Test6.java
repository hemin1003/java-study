package com.minbo.javademo.bytes;

public class Test6 {

	public static void main(String[] args) {
		String s1 = "0x7b0a0922747970654964223a09223737222c0a0922636f6d6d616e64223a092245515549504d454e545f57494e444f5753222c0a09226465766963654964223a092230653338222c0a092270686f6e654e756d223a0922222c0a0922736571223a09223033222c0a0922737461747573223a09224353303033222c0a092264617465223a0922323031372d30372d30362031363a34373a3330220a7d";
		System.out.println(hexStringToString(s1.substring(2,s1.length())));
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