package com.minbo.javademo.classtest;

public class Test {

	public static void main(String[] args) {
		Person p1 = new Student();
		Student p2 = new Student();
		
		System.out.println(p1 == p2);
	}

}
