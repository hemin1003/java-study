package com.minbo.javademo;


public class App9 
{
    public static void main( String[] args )
    {
        System.out.println("111");
        try {
        	System.out.println("222");
        	test();
        	System.out.println("333");
		} catch (Exception e) {
			System.out.println("444");
		}
        System.out.println("555");
    }
    
    public static void test(){
    	int i = 10;
    	int b = i/0;
    }
}
