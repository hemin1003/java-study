package com.minbo.javademo.consistenthashing;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 一致性hash代码
 */
public class Shared<T> {
	
	// 真实节点对应的虚拟节点数量
	private int length = 100;
	
	// 虚拟节点信息
	private TreeMap<Long, T> virtualNodes;
	
	// 真实节点信息
	private List<T> realNodes;

	public Shared(List<T> realNodes) {
		this.realNodes = realNodes;
		init();
	}

	public List<T> getReal() {
		return realNodes;
	}

	/**
	 * 初始化虚拟节点
	 */
	private void init() {
		virtualNodes = new TreeMap<Long, T>();
		for (int i = 0; i < realNodes.size(); i++) {
			for (int j = 0; j < length; j++) {
				virtualNodes.put(hash("aa" + i + j), realNodes.get(i));
			}
		}
	}

	/**
	 * 获取一个结点
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getNode(String key) {
		Long hashedKey = hash(key);
		// TODO judge null
		Entry en = virtualNodes.ceilingEntry(hashedKey);
		if (en == null) {
			return (T) virtualNodes.firstEntry().getValue();
		}
		return (T) en.getValue();
	}

	/**
	 * MurMurHash算法，是非加密HASH算法，性能很高，
	 * 比传统的CRC32,MD5，SHA-1（这两个算法都是加密HASH算法，复杂度本身就很高，带来的性能上的损害也不可避免）等HASH算法要快很多，
	 * 而且据说这个算法的碰撞率很低. http://murmurhash.googlepages.com/
	 */
	private Long hash(String key) {
		ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
		int seed = 0x1234ABCD;

		ByteOrder byteOrder = buf.order();
		buf.order(ByteOrder.LITTLE_ENDIAN);

		long m = 0xc6a4a7935bd1e995L;
		int r = 47;

		long h = seed ^ (buf.remaining() * m);

		long k;
		while (buf.remaining() >= 8) {
			k = buf.getLong();

			k *= m;
			k ^= k >>> r;
			k *= m;

			h ^= k;
			h *= m;
		}

		if (buf.remaining() > 0) {
			ByteBuffer finish = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
			// for big-endian version, do this first:
			// finish.position(8-buf.remaining());
			finish.put(buf).rewind();
			h ^= finish.getLong();
			h *= m;
		}

		h ^= h >>> r;
		h *= m;
		h ^= h >>> r;

		buf.order(byteOrder);
		return h;
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

	/**
	 * 测试方法
	 * 
	 * @param args
	 * @throws InterruptedException
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
		
		//一共10,000,000次 hash，基本算是较均匀投递到10个节点
		List<Node> nodeList = sh.getReal();
		for (Node node : nodeList) {
			System.out.println("node" + node.getName() + ":" + node.getCount());
		}

	}

}
