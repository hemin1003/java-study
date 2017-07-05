package com.minbo.javademo.bytes;

public class Test6 {

	public static void main(String[] args) {
		String s1 = "7b0a0922636f6d6d616e64223a092245515549504d454e545f454e565f53454e534f52222c0a09226465766963654964223a092230343630222c0a0922747970654964223a09223339222c0a092273656e736f7254797065223a095b7b0a0909092273756254797065223a092268756d6964697479222c0a0909092276616c7565223a092235372e30222c0a090909226c6576656c223a092232222c0a09090922756e6974223a092230220a09097d5d2c0a092264617465223a0922323031372d30372d30352031353a35363a3439220a7d";
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