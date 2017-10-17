package com.minbo.javademo.http;

import org.apache.log4j.Logger;

public class Test {
	
	protected static final Logger logger = Logger.getLogger(Test.class);

	public static void main(String[] args) throws Exception {
		 /* Post Request */
//        Map dataMap = new HashMap();
//        dataMap.put("username", "Nick Huang");
//        dataMap.put("blog", "IT");
        
//        System.out.println(new HttpRequestor().doPost("http://localhost:8080/OneHttpServer/", dataMap));
        
        /* Get Request */
//        System.out.println(new HttpRequestor().doGet("http://localhost:8080/OneHttpServer/"));
        
		logger.info("开始批量测试...");
        for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 50; j++) {
				new ThreadUrl().start();
			}
			logger.info("休眠一下...");
			Thread.sleep(5000);
		}
	}

}
