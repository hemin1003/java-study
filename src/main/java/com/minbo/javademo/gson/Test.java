package com.minbo.javademo.gson;

import java.util.List;

import com.google.gson.Gson;

//http://www.cnblogs.com/jiayongji/p/5297187.html
public class Test {

	public static void main(String[] args) {
		//注：这里也可以不使用转义字符，而用单引号：String jsonData = "{'name':'John', 'age':20}";
//		String jsonData = "{\"name\":\"Minbo\"}";
//		Gson gson = new Gson();
//		Person person = gson.fromJson(jsonData, Person.class);
//		System.out.println("name=" + person.getName());
//		System.out.println("age=" + person.getAge().toString());
		
		String data = "[{\"day\":1,\"gold\":\"+50\"},{\"day\":2,\"gold\":\"+100\"},{\"day\":3,\"gold\":\"+100\"},{\"day\":4,\"gold\":\"+1500\"},{\"day\":5,\"gold\":\"+150\"},{\"day\":6,\"gold\":\"+200\"},{\"day\":7,\"gold\":\"+5000\"}]";
		Gson gson = new Gson();
		List<DayInfo> list = gson.fromJson(data, List.class);
		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) {
			DayInfo dayInfo = gson.fromJson(list.get(i).toString(), DayInfo.class);
			
		}
		
	}
}
