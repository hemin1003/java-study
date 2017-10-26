package com.minbo.javademo.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

import com.minbo.javademo.utils.ShareCodeUtil;

public class InsertData {

	public static void main(String[] args) {
		try {
			// 调用Class.forName()方法加载驱动程序
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("成功加载MySQL驱动！");

			String url = "jdbc:mysql://192.168.56.101:3306/cfdb?useUnicode=true&characterEncoding=UTF-8&useSSL=false&autoReconnect=true&allowMultiQueries=true"; // JDBC的URL
			Connection conn;

			conn = DriverManager.getConnection(url, "root", "123456");
			Statement stmt = conn.createStatement(); // 创建Statement对象
			System.out.println("成功连接到数据库！");

			System.out.println("==================");
			System.out.println("1. 先更新");

			long startTime=System.currentTimeMillis();   //获取开始时间
//			
//			for (int i = 0; i < 100; i++) {
//				new Thread(new Runnable() {
//					@Override
//					public void run() {
//						System.out.println("thread id=" + Thread.currentThread().getId() 
//								+ "thread name=" + Thread.currentThread().getName());
//						for (int i = 0; i < 1000 * 10000; i++) {
//							String phoneNum = getTelNum();
//							try {
//								stmt.execute("insert into test_phonenum(phoneNum) values('" + phoneNum + "')");
//							} catch (Exception e) {
//								System.err.println("thread id=" + Thread.currentThread().getId() 
//										+ "thread name=" + Thread.currentThread().getName() + "，重复phoneNum=" + phoneNum);
//							}
//						}
//					}
//				}).start();
//			}
			
			System.out.println("2. 再查询");
			String sql = "select * from test_phonenum"; // 要执行的SQL
			ResultSet rs = stmt.executeQuery(sql);// 创建数据对象
			System.out.println("phoneNum" + ", " + "createDate");
			while (rs.next()) {
				String phoneNum = rs.getString(1);
				String shareCode = ShareCodeUtil.toSerialCode(Long.valueOf(phoneNum));
				try {
					Statement stmt2 = conn.createStatement();
					stmt2.execute("insert into test_phonenum_sharecode(sharecode,phoneNum) "
							+ "values('" + shareCode 
							+ "','" + phoneNum + "')");
				} catch (Exception e) {
					System.err.println("thread id=" + Thread.currentThread().getId() 
							+ "thread name=" + Thread.currentThread().getName() 
							+ "，重复shareCode=" + shareCode + "，phoneNum=" + phoneNum);
				}
//				
			}
			System.out.println("==================");
			rs.close();
			stmt.close();
			conn.close();
//			
			long endTime=System.currentTimeMillis(); //获取结束时间
			System.out.println("程序运行时间： "+(endTime-startTime)/1000+" s");
			System.err.println("done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getNum(int start, int end) {
		return (int) (Math.random() * (end - start + 1) + start);
	}

//	/**
//	 * 返回手机号码
//	 */
//	private static String[] telFirst = "130,131,132,155,156,133,134,157,158,35,136,137,138,139,150,151,152,159,153"
//			.split(",");
//
//	private static String getTelNum() {
//		int index = getNum(0, telFirst.length - 1);
//		String first = telFirst[index];
//		String second = String.valueOf(getNum(1, 888) + 10000*(new Random().nextInt(100))).substring(1);
//		String thrid = String.valueOf(getNum(1, 9100) + 10000*(new Random().nextInt(15))).substring(1);
//		return first + second + thrid;
//	}

}
