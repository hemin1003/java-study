package com.minbo.javademo.gson;

import com.google.gson.Gson;

//http://www.cnblogs.com/jiayongji/p/5297187.html
public class Test {

	public static void main(String[] args) {
		//注：这里也可以不使用转义字符，而用单引号：String jsonData = "{'name':'John', 'age':20}";
		String jsonData = "{\"name\":\"Minbo\"}";
		Gson gson = new Gson();
		Person person = gson.fromJson(jsonData, Person.class);
		System.out.println("name=" + person.getName());
		System.out.println("age=" + person.getAge().toString());
	}
}
