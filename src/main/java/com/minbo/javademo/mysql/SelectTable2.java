package com.minbo.javademo.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SelectTable2 {

	public static void main(String[] args) {
		try {
//			// 调用Class.forName()方法加载驱动程序
//			Class.forName("com.mysql.jdbc.Driver");
//			System.out.println("成功加载MySQL驱动！");
//			String url = "jdbc:mysql://192.168.56.101:3306/cfdb?useUnicode=true&characterEncoding=UTF-8&useSSL=false&autoReconnect=true&allowMultiQueries=true"; // JDBC的URL
//			// 调用DriverManager对象的getConnection()方法，获得一个Connection对象
//			Connection conn = DriverManager.getConnection(url, "root", "123456");
//			// 创建一个Statement对象
//			Statement stmt = conn.createStatement(); // 创建Statement对象
//			System.out.println("成功连接到数据库！");
//			String sql = "select phoneNum, imei from htt_app_user"; // 要执行的SQL
//			ResultSet rs = stmt.executeQuery(sql);// 创建数据对象
//			int count = 0;
//			//测试数据
//			List<String> list = new ArrayList<>();
//			while (rs.next()) {
//				System.out.println(rs.getString(1) + "\t" + rs.getString(2));
//				list.add(rs.getString(2));
//				count++;
//			}
			
			HashMap<String, Object> sourceMap = new HashMap<>();
			//解析配置，形成Map键值
			String config = "1&[0,1,2,3,4,5,6,7,8]#3&[9]";
			String[] configStr = config.split("#");
			for (int i = 0; i < configStr.length; i++) {
				String[] str = configStr[i].split("&");
				String source = str[0];
				String rate = str[1].substring(1, str[1].length() - 1);
				String[] rateStr = rate.split(",");
				for (int j = 0; j < rateStr.length; j++) {
					sourceMap.put(rateStr[j], source);
				}
			}
			//遍历
			System.out.println("config: ");
			Iterator iter = sourceMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey();
				Object val = entry.getValue();
				System.out.println(key + "=" + val);
			}
			
//			int countOne = 0;
//			int countTwo = 0;
//			int countThree = 0;
//			System.out.println();
//			System.out.println("output: ");
//			for (String mId : list) {
//				int tmp = Math.abs(mId.hashCode() % 10);
//				String source = sourceMap.get(String.valueOf(tmp)).toString();
//				if(source.equals("1")) {
//					countOne++;
//				}else if(source.equals("2")) {
//					countTwo++;
//				}else if(source.equals("3")) {
//					countThree++;
//				}
//			}
//			System.out.println("百度数=" + countOne);
//			System.out.println("东方数=" + countTwo);
//			System.out.println("今日头条数=" + countThree);
//			
//			System.out.println(count);
//			rs.close();
//			stmt.close();
//			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}