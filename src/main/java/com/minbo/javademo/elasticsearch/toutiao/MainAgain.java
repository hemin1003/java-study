package com.minbo.javademo.elasticsearch.toutiao;

import org.apache.log4j.Logger;

public class MainAgain {
    
	protected static final Logger logger = Logger.getLogger(MainAgain.class);
	
	public static void main(String[] args) {
//		while(true) {
//			try {
//				logger.info("每十五分钟补偿数据一次...");
//				Thread.sleep(900 * 1000);
//			} catch (InterruptedException e1) {
//				e1.printStackTrace();
//			}
			System.out.println("------------------");
			System.out.println("------------------");
			// 2. 处理详情页面数据
			System.out.println();
			System.out.println("2. 处理详情页面数据...");
			
			ThreadBatchDetail detail = new ThreadBatchDetail("news_regimen");
			detail.start();
			
//			logger.info("跑前半部分标签...");
//			for (int i = 0; i < Tags.tagList.length/2; i++) {
//				ThreadBatchDetail detail = new ThreadBatchDetail(Tags.tagList[i].toString());
//				detail.start();
//			}
			
//			try {
//				logger.info("休息十秒");
//				Thread.sleep(10 * 1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			
//			logger.info("跑后半部分标签...");
//			for (int i = Tags.tagList.length/2; i < Tags.tagList.length; i++) {
//				ThreadBatchDetail detail = new ThreadBatchDetail(Tags.tagList[i].toString());
//				detail.start();
//			}
			
//		}
	}
}
