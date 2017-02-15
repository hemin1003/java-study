package com.minbo.javademo.java8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ListTest {

	public static void main(String[] args) {
		List list = new ArrayList();
		list.add(2);
		list.add(3);
		list.add(1);
		
		list.sort(new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});
		
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}

}
