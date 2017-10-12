package com.minbo.javademo.elasticsearch.toutiao;

import java.net.UnknownHostException;

import org.apache.log4j.Logger;


public class ThreadBatchDetail extends Thread {
	
	protected static final Logger logger = Logger.getLogger(ThreadBatchDetail.class);

	private String myTag;

	public String getMyTag() {
		return myTag;
	}

	public ThreadBatchDetail(String myTag) {
		this.myTag = myTag;
	}
	
	@Override
	public void run() {
		ToutiaoDetail tDetail = new ToutiaoDetail();
		try {
			tDetail.processDetailInfo(this.myTag);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
