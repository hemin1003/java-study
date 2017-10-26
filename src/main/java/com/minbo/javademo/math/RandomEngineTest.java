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
		Map<String, Integer> keyChanceMap = new HashMap<String, Integer>();
		keyChanceMap.put("50", 50);
		keyChanceMap.put("100", 30);
		keyChanceMap.put("200", 20);

		
		for (int i = 0; i < 10; i++) {
			Map<String, Integer> count = new HashMap<String, Integer>();
			// print
			String key = RandomEngine.chanceSelect(keyChanceMap);
			if (count.containsKey(key)) {
				count.put(key, count.get(key) + 1);
			} else {
				count.put(key, 1);
			}
			System.out.println(key);
		}
	}
}
