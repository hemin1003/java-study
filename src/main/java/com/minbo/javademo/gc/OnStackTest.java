package com.minbo.javademo.gc;

public class OnStackTest {

	/**
	 * alloc方法内分配了两个字节的内存空间
	 */
	public static void alloc() {
		byte[] b = new byte[2];
		b[0] = 1;
	}

	public static void main(String[] args) {
		long b = System.currentTimeMillis();

		// 分配 100000000 个 alloc 分配的内存空间
		for (int i = 0; i < 100000000; i++) {
			alloc();
		}

		long e = System.currentTimeMillis();
		System.out.println(e - b);
	}
}
