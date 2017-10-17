package com.minbo.javademo.http;

import org.apache.log4j.Logger;

public class ThreadUrl extends Thread{
	
	protected static final Logger logger = Logger.getLogger(ThreadUrl.class);

	@Override
	public void run() {
		try {
			logger.info(this.getId() + ", " + this.getName() + " 开始跑了...");
			long start = System.currentTimeMillis(); // 获取开始时间
			String cotent = new HttpRequestor().doGet("http://yc1616.cn/commons/_search?pretty");
			logger.info(cotent);
			long end = System.currentTimeMillis(); // 获取结束时间
			logger.info("运行总耗费时间： " + (end - start) + " ms");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
