package com.minbo.javademo.consistenthashing;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

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
}
