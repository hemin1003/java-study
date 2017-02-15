package com.minbo.javademo.java8.chap3;

//函数式接口
@FunctionalInterface
public interface PeopleDao {
	
	//函数描述符
	public void addPeople();

	public default void deletePeople() {
		System.out.println("deletePeople");
	}
}
