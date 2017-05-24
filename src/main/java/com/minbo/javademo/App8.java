package com.minbo.javademo;


public class App8 
{
    public static void main( String[] args )
    {
        int i = 12;
//        i = i++;
        System.out.println(i*=i+=i-=i);
//        System.out.println(i);
    }
}
