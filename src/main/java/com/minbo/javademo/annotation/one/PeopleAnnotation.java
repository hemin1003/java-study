package com.minbo.javademo.annotation.one;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface PeopleAnnotation {
	int id() default 1;
	String name() default "minbo he";
}
