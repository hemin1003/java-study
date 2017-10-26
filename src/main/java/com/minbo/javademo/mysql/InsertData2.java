package com.minbo.javademo.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Random;

import com.hankcs.hanlp.dependency.nnparser.parser_dll;
import com.minbo.javademo.inviteCode.RandomNumberGenerator;
import com.minbo.javademo.utils.ShareCodeUtil;

public class InsertData2 {

	public static void main(String[] args) {
		try {
			// 调用Class.forName()方法加载驱动程序
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("成功加载MySQL驱动！");

			String url = "jdbc:mysql://192.168.56.101:3306/cfdb?useUnicode=true&characterEncoding=UTF-8&useSSL=false&autoReconnect=true&allowMultiQueries=true"; // JDBC的URL
			Connection conn;

			conn = DriverManager.getConnection(url, "root", "123456");
			System.out.println("成功连接到数据库！");
			long startTime = System.currentTimeMillis(); // 获取结束时间
			
			int error3 = 0;
			int error2 = 0;
			int error = 0;
			int count = 0;
			PreparedStatement ps = conn.prepareStatement("insert into test_phonenum_sharecode(sharecode,phoneNum) values(?,?)");
			for (int a = 0; a < 50000; a++) {
				String shareCode = RandomNumberGenerator.generateNumber();
				String phoneNum = getTelNum();
				try {
//					Statement stmt2 = conn.createStatement();
//					stmt2.execute("insert into test_phonenum_sharecode(sharecode,phoneNum) "
//							+ "values('" + shareCode 
//							+ "','" + phoneNum + "')");
					
					ps.setString(1, shareCode);
					ps.setString(2, phoneNum);
					ps.execute();
					
					count++;
				} catch (Exception e) {
					error++;
//					shareCode = RandomNumberGenerator.generateNumber();
//					try {
////						Statement stmt2 = conn.createStatement();
////						stmt2.execute("insert into test_phonenum_sharecode(sharecode,phoneNum) "
////								+ "values('" + shareCode 
////								+ "','" + phoneNum + "')");
//						
//						ps.setString(1, shareCode);
//						ps.setString(2, phoneNum);
//						ps.execute();
//					}catch (Exception e1) {
//						error2++;
//						
//						shareCode = RandomNumberGenerator.generateNumber();
//						try {
////							Statement stmt2 = conn.createStatement();
////							stmt2.execute("insert into test_phonenum_sharecode(sharecode,phoneNum) "
////									+ "values('" + shareCode 
////									+ "','" + phoneNum + "')");
//							
//							ps.setString(1, shareCode);
//							ps.setString(2, phoneNum);
//							ps.execute();
//						}catch (Exception e2) {
//							error3++;
//						}
//						
//					}
				}
			}
			
			System.out.println("成功条数count=" + count);
			System.out.println("第一次重复条数error=" + error);
//			System.out.println("第二次重复失败条数error2=" + error2);
//			System.out.println("第三次重复失败条数error3=" + error3);

//			for (int i = 0; i < 100; i++) {
//				new Thread(new Runnable() {
//					@Override
//					public void run() {
//						try {
//							int count = 0;
//							PreparedStatement ps = conn.prepareStatement("insert into test_phonenum_sharecode(sharecode,phoneNum) values(?,?)");
//							for (int a = 0; a < 100; a++) {
//								conn.setAutoCommit(false); 
//								for (int i = 0; i < 1000; i++) {
//									String shareCode = RandomNumberGenerator.generateNumber();
//									String phoneNum = getTelNum();
//									ps.setString(1, shareCode);
//									ps.setString(2, phoneNum);
//									ps.addBatch();
//									count++;
//									System.out.println(count);
//								}
//								ps.executeBatch();
////								ps.executeLargeBatch();
//								conn.commit();
//							}
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
//				}).start();
//			}

			long endTime = System.currentTimeMillis(); // 获取结束时间
			System.out.println("程序运行时间： " + (endTime - startTime) / 1000 + " s");
			System.err.println("done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getNum(int start, int end) {
		return (int) (Math.random() * (end - start + 1) + start);
	}

	// /**
	// * 返回手机号码
	// */
	private static String[] telFirst = "130,131,132,155,156,133,134,157,158,35,136,137,138,139,150,151,152,159,153"
			.split(",");

	private static String getTelNum() {
		int index = getNum(0, telFirst.length - 1);
		String first = telFirst[index];
		String second = String.valueOf(getNum(1, 888) + 10000 * (new Random().nextInt(100))).substring(1);
		String thrid = String.valueOf(getNum(1, 9100) + 10000 * (new Random().nextInt(15))).substring(1);
		return first + second + thrid;
	}

}
