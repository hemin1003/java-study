package com.minbo.javademo.annotation.one;

@PeopleAnnotation(id = 8, name = "hemin")
public class Person {

	public void eat() {
		System.out.println("Person eating method");
	}

	@MethodAnnotation
	public void walk() {
		System.out.println("Person walking method");
	}
}
