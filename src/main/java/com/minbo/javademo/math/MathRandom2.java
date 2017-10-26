package com.minbo.javademo.math;

/**
 * JAVA生成随机数，并根据概率灵活生成
 * @author Minbo
 *
 */
public class MathRandom2 {
	
	/**
	 * 50出现的概率为%50
	 */
	public static double rate0 = 0.50;
	/**
	 * 100出现的概率为%30
	 */
	public static double rate1 = 0.30;
	/**
	 * 200出现的概率为%20
	 */
	public static double rate2 = 0.25;

	/**
	 * Math.random()产生一个double型的随机数，判断一下 例如0出现的概率为%50，则介于0到0.50中间的返回0
	 * 
	 * @return int
	 * 
	 */
	private int PercentageRandom() {
		double randomNumber;
		randomNumber = Math.random();
		if (randomNumber >= 0 && randomNumber <= rate0) {
			return 50;
		} else if (randomNumber >= rate0 / 100 && randomNumber <= rate0 + rate1) {
			return 100;
		} else if (randomNumber >= rate0 + rate1 && randomNumber <= rate0 + rate1 + rate2) {
			return 200;
		}
		return -1;
	}

	/**
	 * 测试主程序
	 * 
	 * @param agrs
	 */
	public static void main(String[] agrs) {
		MathRandom2 a = new MathRandom2();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				System.out.println(a.PercentageRandom());
			}
			System.out.println("==================");
			System.out.println("==================");
		}
		
	}
}
