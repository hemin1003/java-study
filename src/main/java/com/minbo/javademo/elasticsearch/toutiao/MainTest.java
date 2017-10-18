package com.minbo.javademo.elasticsearch.toutiao;

import org.apache.log4j.Logger;

/**
 * 程序入口
 * 
 * @author Minbo
 */
public class MainTest {
	
	protected static final Logger logger = Logger.getLogger(MainTest.class);

	// 并发请求数
	public static int CONCURRENCY_REQ_COUNT = 5;
	// 处理线程数
	public static int THREAD_COUNT = 5;

	public static void main(String[] args) {
		
//		while(true) {
			logger.info("======================");
			logger.info("======================");
			logger.info("开始跑批...");
			logger.info("======================");
			logger.info("======================");
			// 获取开始时间
			long start = System.currentTimeMillis();
			
			ThreadBatch mainClass = new ThreadBatch("news_story");
			mainClass.start();
			
//			logger.info("跑前半部分标签...");
//			for (int i = 0; i < Tags.tagList.length/2; i++) {
//				ThreadBatch mainClass = new ThreadBatch(Tags.tagList[i].toString());
//				mainClass.start();
//			}
//			
//			try {
//				logger.info("休息三十秒");
//				Thread.sleep(30 * 1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			
//			logger.info("跑后半部分标签...");
//			for (int i = Tags.tagList.length/2; i < Tags.tagList.length; i++) {
//				ThreadBatch mainClass = new ThreadBatch(Tags.tagList[i].toString());
//				mainClass.start();
//			}
			
			// 获取结束时间
			long end = System.currentTimeMillis();
			logger.info("======================");
			logger.info("======================");
			logger.info("程序总运行时间： " + (end - start) + " ms");
			logger.info("======================");
			logger.info("======================");
			
			try {
				logger.info("每二十分钟跑数据一次...");
				Thread.sleep(1200 * 1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
//		}
	}
}
