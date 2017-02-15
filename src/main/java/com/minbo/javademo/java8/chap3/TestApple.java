package com.minbo.javademo.java8.chap3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import static java.util.Comparator.comparing;

public class TestApple {

	public static void main(String[] args) {
		List<Apple> inventory = new ArrayList<>();
		inventory.addAll(Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(80, "red")));

		//方式一
//		inventory.sort(new AppleComparator());
//		System.out.println(inventory);

		//方式二
//		inventory.sort(new Comparator<Apple>() {
//			public int compare(Apple a1, Apple a2) {
//				return a1.getWeight().compareTo(a2.getWeight());
//			}
//		});
//		System.out.println(inventory);
		
		//方式三
//		inventory.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));
//      System.out.println(inventory);
		
		//方式四
		//升序
//		inventory.sort(comparing(Apple::getWeight));
		//逆序
//		inventory.sort(comparing(Apple::getWeight).reversed());
		//比较器链
		inventory.sort(comparing(Apple::getWeight).thenComparing(Apple::getColor));
		System.out.println(inventory);
	}

	public static class Apple {
		private Integer weight = 0;
		private String color = "";

		public Apple(Integer weight, String color) {
			this.weight = weight;
			this.color = color;
		}

		public Integer getWeight() {
			return weight;
		}

		public void setWeight(Integer weight) {
			this.weight = weight;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public String toString() {
			return "Apple{" + "color='" + color + '\'' + ", weight=" + weight + '}';
		}
	}

	static class AppleComparator implements Comparator<Apple> {
		public int compare(Apple a1, Apple a2) {
			return a1.getWeight().compareTo(a2.getWeight());
		}
	}
}
