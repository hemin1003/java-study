package com.minbo.javademo.ids;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 生成唯一订单号
 */
public class TestIds {

	public static void main(String[] args) {
		System.out.println(getFlowNo());
	}

	public static String getFlowNo() {
		StringBuffer barCode = new StringBuffer();
		String primaryKey = java.util.UUID.randomUUID().toString();
		primaryKey = primaryKey.replaceAll("-", "");

		BigInteger bi = new BigInteger(primaryKey, 16);
		barCode.append(bi.abs());
		barCode.insert(0, getDayDate("yyyyMMdd"));
		return barCode.toString().substring(0, 20);
	}

	public static String getDayDate(String formatStr) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new java.text.SimpleDateFormat(formatStr);
		String nowDate = sdf.format(cal.getTime());
		return nowDate;
	}
}
