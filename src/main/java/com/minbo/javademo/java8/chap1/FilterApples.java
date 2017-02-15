package com.minbo.javademo.java8.chap1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FilterApples {

	public static void main(String[] args) {
		List<Apple> inventory = new ArrayList<>();
		inventory.add(new Apple("red", 100));
		inventory.add(new Apple("green", 90));
		inventory.add(new Apple("yellow", 160));
		
		//之前方式，利用循环迭代
		List<Apple> tmp = FilterApples.filterGreenApple(inventory);
		for (Apple apple : tmp) {
			System.out.println(apple.toString());
		}
		
		//JAVA8 -> 传递代码
		//用法一
		List<Apple> tmp2 = filterApples(inventory, (Apple a) -> "red".equals(a.getColor()));
		for (Apple apple : tmp2) {
			System.out.println(apple.toString());
		}
		//用法二
		filterApples(inventory, (Apple a) -> a.getWeight()>150);
		//用法三
		filterApples(inventory, (Apple a) -> a.getWeight()<100 || "green".equals(a.getWeight()));
		
		
		//JAVA8 -> 方法引用
		List<Apple> tmp3 = FilterApples.filterApples(inventory, Apple::isHeavyApple);
		for (Apple apple : tmp3) {
			System.out.println(apple.toString());
		}
		
	}
	
	public static List<Apple> filterGreenApple(List<Apple> inventory){
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if("green".equals(apple.getColor())){
				result.add(apple);
			}
		}
		return result;
	}
	
	public static List<Apple> filterHeavyApple(List<Apple> inventory){
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if(apple.getWeight() > 150){
				result.add(apple);
			}
		}
		return result;
	}
	
	//JAVA8通过把条件代码作为参数传递进去，避免Filter方法出现重复的代码
	public static <T> List<T> filterApples(List<T> inventory, Predicate<T> p){
		List<T> result = new ArrayList<>();
		for (T e : inventory) {
			if(p.test(e)){ 
				result.add(e);
			}
		}
		return result;
	}
	
	static class Apple{
		private String color;
		private int weight;
		
		public Apple(String color, int weight){
			this.color = color;
			this.weight = weight;
		}
		
		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
		public int getWeight() {
			return weight;
		}
		public void setWeight(int weight) {
			this.weight = weight;
		}

		@Override
		public String toString() {
			return this.color + ", " + this.weight;
		}
		
		public static boolean isGreenApple(Apple apple){
			return "green".equals(apple.getColor());
		}
		
		public static boolean isHeavyApple(Apple apple){
			return apple.getWeight() > 150;
		}
	}
}
