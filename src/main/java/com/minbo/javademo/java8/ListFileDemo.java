package com.minbo.javademo.java8;

import java.io.File;
import java.io.FileFilter;

public class ListFileDemo {
	/*
	 * 比较案例
	 */
	public static void main(String[] args) {
		//之前的写法
		File[] files = new File("E:\\kankan").listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isHidden();
			}
		});
		
		//JAVA 8写法
		File[] files2 = new File("E:\\kankan").listFiles(File::isHidden);
		
		
		for (int i = 0; i < files2.length; i++) {
			System.out.println(files2[i].getAbsolutePath());
		}
	}
}
