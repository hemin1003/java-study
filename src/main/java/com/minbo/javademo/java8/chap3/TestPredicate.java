package com.minbo.javademo.java8.chap3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class TestPredicate {

	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		list.add("1");
		list.add("12");
		list.add("123");
		
		//表达式也可以写成：s -> s.length()>2，可以省去标注参数类型，因为隐式的类型判断
		Predicate<String> p = (String s) -> s.length()>1;
		//谓词复合
		List<String> results = TestPredicate.filter(list, p.and(s -> s.length()>2));
		for (String s : results) {
			System.out.println(s);
		}
	}
	
	//Predicate表示一个涉及类型T的返回布尔表达式时，就可以使用这个接口。
	//定义了一个名叫test的抽象方法，接收泛型T对象，并返回一个boolean
	public static <T> List<T> filter(List<T> list, Predicate<T> p){
		List<T> results = new ArrayList<>();
		for (T t : list) {
			if(p.test(t)){
				results.add(t);
			}
		}
		return results;
	}
}
