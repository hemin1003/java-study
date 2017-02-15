package com.minbo.javademo.java8.chap3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class TestFunction {

	public static void main(String[] args) {
		List<Integer> list = TestFunction.map(Arrays.asList("123","12","1"), (String s) -> s.length());
		for (Integer i : list) {
			System.out.println(i);
		}
	}
	
	//Function表示需要定义一个lambda，将输入对象的信息映射到输出
	//定义了一个叫做apply的方法，接收泛型T的对象，并返回一个泛型R的对象
	public static<T, R> List<R> map(List<T> list, Function<T, R> f){
		List<R> results = new ArrayList<>();
		for (T s : list) {
			results.add(f.apply(s));
		}
		return results;
	}
}
