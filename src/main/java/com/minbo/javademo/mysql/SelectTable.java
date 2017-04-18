package com.minbo.javademo.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SelectTable {

	public static void main(String[] args) {
		try {
			// 调用Class.forName()方法加载驱动程序
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("成功加载MySQL驱动！");

			String url = "jdbc:mysql://192.168.0.104:3316/aylson_dc_v1?useUnicode=true&autoReconnect=true&allowMultiQueries=true"; // JDBC的URL
			Connection conn;

			conn = DriverManager.getConnection(url, "root", "Aylson");
			Statement stmt = conn.createStatement(); // 创建Statement对象
			System.out.println("成功连接到数据库！");

			conn.setAutoCommit(false);
			System.out.println("==================");
			System.out.println("1. 先更新");
			stmt.execute("update sys_user set userName='666' where id=8");
			System.out.println("2. 再查询");
			String sql = "select * from sys_user where id=8"; // 要执行的SQL
			ResultSet rs = stmt.executeQuery(sql);// 创建数据对象
			System.out.println("id" + "\t" + "name" + "\t" + "pwd");
			while (rs.next()) {
				System.out.print(rs.getInt(1) + "\t");
				System.out.print(rs.getString(2) + "\t");
				System.out.print(rs.getString(3) + "\t");
				System.out.println();
			}
			System.out.println("==================");
			conn.commit();
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
