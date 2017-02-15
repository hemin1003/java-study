package com.minbo.javademo.java8.chap3;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class TestConsumer {
	public static void main(String[] args) {
		TestConsumer.forEach(Arrays.asList(1, 2, 3, 4, 5), (Integer i) -> System.out.println(i));
	}

	//Consumer表示需要访问类型T的对象，并对其执行某些操作
	//定义了一个名叫accept的抽象方法，接受泛型T对象，没有返回void
	public static <T> void forEach(List<T> list, Consumer<T> c) {
		for (T t : list) {
			c.accept(t);
		}
	}
}
