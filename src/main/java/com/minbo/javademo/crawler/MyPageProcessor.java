package com.minbo.javademo.crawler;

import org.apache.http.HttpHost;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.ProxyPool;

public class MyPageProcessor implements PageProcessor {

//	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000)
//			.setHttpProxy(new HttpHost("115.56.187.18", 8118));

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000)
			.setHttpProxy(new HttpHost("222.139.193.17", 8118));
	
	@Override
	public void process(Page page) {
		page.addTargetRequests(page.getHtml().links().regex("(http://182.92.82.188:8081/yfax-webapi/hello)").all());
		page.putField("html", page.getHtml().toString());
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new MyPageProcessor()).addUrl("http://182.92.82.188:8081/yfax-webapi/hello").thread(1).run();
	}
}
