package com.minbo.javademo.excel;

import java.io.File;
import java.io.IOException;

public class ExcelReaderTest {
	public static void main(String[] args) {
		File file = new File("/Users/Minbo/Downloads/2018-01-15_误杀处理.xls");
		ExcelReader readExcel = new ExcelReader(file);
		try {
			readExcel.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
		readExcel.setSheetNum(0); // 设置读取索引为0的工作表
		// 总行数
		int count = readExcel.getRowCount();
		System.out.println(count);
		for (int i = 0; i <= count; i++) {
			String[] rows = readExcel.readExcelLine(i);
//			for (int j = 0; j < rows.length; j++) {
//				System.out.print(rows[j] + "\t");
//			}
			System.out.println("'"+rows[0]+"',");
		}
	}
}
