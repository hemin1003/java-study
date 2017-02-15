package com.minbo.javademo.java8.chap3;

public class Apple {
	
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
	
	public Apple(){
		
	}
	
	public Apple(int weight){
		this.weight = weight;
	}
	
	public void add(){
		System.out.println("add," + this.weight);
	}
}
