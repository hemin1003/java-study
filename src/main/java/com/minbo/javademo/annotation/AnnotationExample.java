package com.minbo.javademo.annotation;

import java.util.ArrayList;
import java.util.List;

public class AnnotationExample {
	public static void main(String[] args) {
		
	}

	@Override
	@MethodInfo(author = "Pankaj110", comments = "toString() method", date = "Nov 17 2012", revision = 1)
	public String toString() {
		return "overriden toString method.";
	}
	
	@Deprecated
	@MethodInfo(comments = "deprecated method", date = "Nov 18 2012")
	public static void oldMethod(){
		
	}
	
	@SuppressWarnings(value = { "unchecked" })
	@MethodInfo(author = "Pankaj220", comments = "genericsTest() method", date = "Nov 19 2012", revision = 20)
	public static void genericsTest(){
		List l = new ArrayList();
        l.add("abc");
        oldMethod();
	}
}
