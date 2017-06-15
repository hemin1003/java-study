package com.minbo.javademo.elk;

import org.apache.log4j.Logger;

public class ElkLog4jTest {

	private static final Logger logger = Logger.getLogger(ElkLog4jTest.class);

	public static void main(String[] args) {
		logger.debug("This is a debug message of minbo!");
		logger.info("This is info message!");
		logger.warn("This is a warn message!");
		logger.error("This is error message!");

		try {
			System.out.println(5 / 0);
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
