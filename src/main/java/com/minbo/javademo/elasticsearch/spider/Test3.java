package com.minbo.javademo.elasticsearch.spider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test3 {

	public static void main(String[] args) {
		String a="https://m.toutiao.com/i6465174828285428238/info/";
		String regEx="[^0-9]";   
		Pattern p = Pattern.compile(regEx);   
		Matcher m = p.matcher(a);   
		System.out.println( m.replaceAll("").trim());
	}

}
