package com.minbo.javademo.java8.chap3;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class TestConference {

	public static void main(String[] args) {
		List<String> list = Arrays.asList("a", "aa", "aaa");
		//方式一
		list.sort((s1,s2) -> s1.compareToIgnoreCase(s2));
		//方式二
		list.sort(String::compareToIgnoreCase);
		
		//方式三
		Supplier<Apple> s1 = Apple::new;
		Apple a1 = s1.get();
		a1.add();
		
		//方式四
		Function<Integer, Apple> s2 = Apple::new;
		Apple a2 = s2.apply(100);
		a2.add();
	}
}
