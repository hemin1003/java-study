package com.minbo.javademo.elasticsearch.toutiao;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import us.codecraft.webmagic.Spider;

public class ThreadBatch extends Thread {
	
	protected static final Logger logger = Logger.getLogger(ThreadBatch.class);

	private String myTag;

	public String getMyTag() {
		return myTag;
	}

	public ThreadBatch(String myTag) {
		this.myTag = myTag;
	}

	@Override
	public void run() {
		long start=System.currentTimeMillis();   //获取开始时间
		
		//1. 处理列表数据
		logger.info("");
		logger.info("tag=" + this.myTag + "，1. 处理列表数据...");
		//并发请求次数
		int count = MainTest.CONCURRENCY_REQ_COUNT;
		String [] strArray = new String [count];
		for (int i = 0; i < count; i++) {
			Date date = DateUtils.addMinutes(Utils.dateToStamp(System.currentTimeMillis() / 1000), new Random().nextInt(200) * -2 - 1);
			long stamp = date.getTime()/1000;
			StringBuffer url = new StringBuffer();
			url.append("https://m.toutiao.com/list/?tag=" + this.myTag + "&ac=wap&count=20&format=json_raw"
					+ "&as=" + Tags.acList[new Random().nextInt(Tags.acList.length) ] 
					+ "&cp=" + Tags.cpList[new Random().nextInt(Tags.cpList.length) ] + "&");
			if(i== 0) {
				url.append("min_behot_time=0");
			}else {
				url.append("max_behot_time=" + stamp);
			}
			strArray[i] = url.toString();
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		logger.info("================下载地址LIST_URL：列表==============");
		logger.info("------------------");
		for (String string : strArray) {
			logger.info(string);
		}
		logger.info("------------------");
		logger.info("开始爬虫网站....");
		Spider.create(new ToutiaoMain()).addUrl(strArray).thread(MainTest.THREAD_COUNT).run();
		
		//休眠三秒
		logger.info("休眠三秒");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.info("------------------");
		logger.info("------------------");
		logger.info("------------------");
		//2. 处理详情页面数据
		logger.info("");
		logger.info("");
		logger.info("");
		logger.info("2. 处理详情页面数据...");
		
//		try {
//			ToutiaoDetail tDetail = new ToutiaoDetail();
//			tDetail.processDetailInfo(this.myTag);
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}
		
		try {
			logger.info("----------");
			logger.info("----------");
			logger.info("休息5秒种.");
			logger.info("----------");
			logger.info("----------");
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		long end=System.currentTimeMillis(); //获取结束时间
		logger.info("该批次运行总耗费时间： "+(end-start)+" ms");
	}
}
