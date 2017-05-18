package com.minbo.javademo.annotation.one;

public class Son {

	public void eat() {
		System.out.println("Son eating method");
	}

	@MethodAnnotation
	public void walk() {
		System.out.println("Son walking method");
	}
}
