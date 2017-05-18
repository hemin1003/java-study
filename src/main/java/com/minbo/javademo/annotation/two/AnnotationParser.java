package com.minbo.javademo.annotation.two;

import java.lang.reflect.Method;

public class AnnotationParser {

	public static void main(String[] args) {
		Class cls = AnnotationTest.class;
		for (Method method : cls.getMethods()) {
			MetdInfo info = method.getAnnotation(MetdInfo.class);
			if (info != null) {
				System.out.println("method name: " + method.getName());
				System.out.println("method author: " + info.author());
				System.out.println("method date: " + info.date());
				System.out.println("method version: " + info.version());
			}
		}
	}
}
