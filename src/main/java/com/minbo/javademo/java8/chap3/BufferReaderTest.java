package com.minbo.javademo.java8.chap3;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * 应用环绕执行四个步骤
 * 1. 行为参数化
 * 2. 使用函数式接口传递行为
 * 3. 执行一个行为
 * 4. 传递lambda
 */
public class BufferReaderTest {

	public static void main(String[] args) throws Exception {

		String result = BufferReaderTest.processFile();
		System.out.println("result=" + result);

		String result2 = BufferReaderTest.processFile2((BufferedReader br) -> br.readLine());
		System.out.println("result2=" + result2);
		
		String result3 = BufferReaderTest.processFile2((BufferedReader br) -> br.readLine()+br.readLine());
		System.out.println("result3=" + result3);
		
	}

	public static String processFile() throws Exception {
		// 使用了JAVA7中的带资源的try语句，它已经简化了代码，不需要显示地关闭资源了
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\json1.txt"))) {
			return br.readLine();
		}
	}

	public static String processFile2(BufferReaderProcessor brp) throws Exception {
		// 使用了JAVA7中的带资源的try语句，它已经简化了代码，不需要显示地关闭资源了
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\json1.txt"))) {
			return brp.process(br);
		}
	}

}
