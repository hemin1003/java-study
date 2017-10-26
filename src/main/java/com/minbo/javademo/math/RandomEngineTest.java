package com.minbo.javademo.math;

import java.util.HashMap;
import java.util.Map;

/**
 * 实现随机数按概率显示
 * 
 * 再举多个候选数的例子。比如a概率为20%，b概率为30%，c概率为40%，d概率为10%，那么他们的概率值范围分别是： a[1,20] b[21,50]
 * c[51,90] d[91,100] 然后取一个[1,100]的随机数，落到谁的范围谁就是选中了。
 * 
 * 算法描述如下： 1.依次（顺序可随机）将各项按概率值从原点开始放在一维坐标上首尾相连，这样每一项对应一个取值区间
 * 2.在总区间范围内随机选取一个点，落在哪一项对应的区间就选中哪一项
 * 
 * @author Minbo
 * 
 * 测试（次数足够多时，各值出现的比例跟概率是基本一致的）
 *
 */
public class RandomEngineTest {
	public static void main(String[] args) {
		//构建概率值
		Map<String, Integer> keyChanceMap = new HashMap<String, Integer>();
		//50出现概率70%，100出现概率20%，200出现概率10%
		keyChanceMap.put("50", 70);
		keyChanceMap.put("100", 20);
		keyChanceMap.put("200", 10);
		
		for (int i = 0; i < 10; i++) {
			String value = RandomEngine.getRandomValue(keyChanceMap);
			System.out.println(value);
		}
	}
}
