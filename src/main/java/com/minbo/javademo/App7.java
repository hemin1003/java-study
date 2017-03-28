package com.minbo.javademo;

public class App7 
{
    public static void main( String[] args )
    {
        new test("zhangsan").start();
        new test("wangwu").start();
    }
    
    //有没有可能出现交叉打印？
    static class test extends Thread {
    	String name;
    	public test(String name){
    		this.name = name;    	
    	}
		public void run() {
			System.out.println(name);
			System.out.println(name);
		}
	}
}
