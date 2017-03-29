package com.minbo.javademo.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class TestList {
	
	public static void main(String[] args) {
		List list = new ArrayList();
		for (int i = 0; i < 10; i++) {
			HashMap hm1 = new HashMap();
			hm1.put("rname", "整栋房1");
			hm1.put("roomId", "0001");
			list.add(hm1);

			HashMap hm2 = new HashMap();
			hm2.put("rname", "整栋房2");
			hm2.put("roomId", "0002");
			list.add(hm2);
		}
//		System.out.println(removeDuplicate(list));
		
		List temp = removeDuplicate(list);
		for (int i = 0; i < temp.size(); i++) {
			HashMap hm = (HashMap) temp.get(i);
			System.out.println(hm.get("rname"));
			System.out.println(hm.get("roomId"));
		}
	}

	// List内容去重
	public static List removeDuplicate(List list) {
		HashSet h = new HashSet(list);
		list.clear();
		list.addAll(h);
		return list;
	}
}