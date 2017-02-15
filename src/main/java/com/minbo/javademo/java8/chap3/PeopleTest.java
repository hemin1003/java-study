package com.minbo.javademo.java8.chap3;

public class PeopleTest {

	public static void main(String[] args) {
		PeopleDao p1 = new PeopleDao() {
			@Override
			public void addPeople() {
				System.out.println("add 1");
			}
		};

		PeopleDao p2 = () -> System.out.println("add 2");

		PeopleTest.process(p1);
		PeopleTest.process(p2);
		PeopleTest.process(() -> {
			System.out.println("add 3");
			System.out.println("123456");
		});
	}

	public static void process(PeopleDao p) {
		p.addPeople();
	}
}
