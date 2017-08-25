package com.minbo.javademo.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class GetConnection {
	public static void main(String[] args) {
		try {
			// 调用Class.forName()方法加载驱动程序
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("成功加载MySQL驱动！");
		} catch (ClassNotFoundException e1) {
			System.out.println("找不到MySQL驱动!");
			e1.printStackTrace();
		}

		String url = "jdbc:mysql://192.168.56.101:3306/cfdb?useUnicode=true&characterEncoding=UTF-8&useSSL=false&autoReconnect=true&allowMultiQueries=true"; // JDBC的URL
		// 调用DriverManager对象的getConnection()方法，获得一个Connection对象
		Connection conn;
		try {
			conn = DriverManager.getConnection(url, "root", "123456");
			// 创建一个Statement对象
			Statement stmt = conn.createStatement(); // 创建Statement对象
			System.out.print("成功连接到数据库！");
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
