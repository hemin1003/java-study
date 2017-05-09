package com.minbo.javademo.consistenthashing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SharedTest {

	/**
	 * 测试方法
	 */
	public static void main(String[] args) throws InterruptedException {
		List<Node> ndList = new ArrayList<Node>();
		int i = 0;
		while (true) {
			ndList.add(new Node(i));
			if (i++ == 9)
				break;
		}

		final Shared<Node> sh = new Shared<Node>(ndList);
		ExecutorService es = Executors.newCachedThreadPool();
		final CountDownLatch cdl = new CountDownLatch(1000);
		// 线程池起了1000个线程，每个线程hash10000次，模拟一万次数据hash
		for (int j = 0; j < 1000; j++) {
			es.execute(new Runnable() {
				@Override
				public void run() {
					// Random rd = new Random(1100);
					for (int k = 0; k < 10000; k++) {
						sh.getNode(String.valueOf(Math.random())).inc();
					}
					cdl.countDown();
				}
			});
		}

		// 等待所有线程结束
		cdl.await();

		// 一共10,000,000次 hash，基本算是较均匀投递到10个节点
		List<Node> nodeList = sh.getReal();
		for (Node node : nodeList) {
			System.out.println("node" + node.getName() + ":" + node.getCount());
		}
	}

	/**
	 * 测试内部类
	 */
	static public class Node {
		private int name;
		private int count = 0;

		public Node() {

		}

		public Node(int i) {
			this.name = i;
		}

		public int getName() {
			return name;
		}

		public void setName(int name) {
			this.name = name;
		}

		public int getCount() {
			return count;
		}

		// 同步方法，防止并发
		synchronized public void inc() {
			count++;
		}
	}

}
