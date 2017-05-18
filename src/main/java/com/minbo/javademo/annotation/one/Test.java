package com.minbo.javademo.annotation.one;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test {

	public static void main(String[] args) 
			throws IllegalAccessException, IllegalArgumentException, 
			InvocationTargetException, InstantiationException {
		parse(com.minbo.javademo.annotation.one.Person.class);
//		parse(com.minbo.javademo.annotation.one.Son.class);
	}
	
	private static<T> void parse(Object obj) throws IllegalAccessException, IllegalArgumentException, 
		InvocationTargetException, InstantiationException{
		
		Class<T> cls = (Class<T>) obj;
		Method[] methods = cls.getMethods();
		for (Method method : methods) {
			if(method.isAnnotationPresent(MethodAnnotation.class)){
				method.invoke(cls.newInstance());
			}
		}
	}
}