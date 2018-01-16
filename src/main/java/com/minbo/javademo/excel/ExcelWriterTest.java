package com.minbo.javademo.excel;
//package com.sunyard.common.utils;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//public class ExcelWriterTest {
//	public static void main(String[] args) {
//		System.out.println(" 开始导出Excel文件 ");
//
//		File f = new File("C:\\temp.xls");
//
//		ExcelWriter e = null;
//
//		try {
//			e = new ExcelWriter(new FileOutputStream(f));
//		} catch (FileNotFoundException e1) {
//			e1.printStackTrace();
//		}
//
//		e.createRow(0);
//		e.setCell(0, "试题编码 ");
//		e.setCell(1, "题型");
//		e.setCell(2, "分值");
//		e.setCell(3, "难度");
//		e.setCell(4, "级别");
//		e.setCell(5, "知识点");
//		e.setCell(6, "知识点");
//		e.setCell(7, "知识点");
//		e.setCell(8, "日期");
//
//		e.createRow(1);
//		e.setCell(0, "t1");
//		e.setCell(1, 1);
//		e.setCell(2, 3.0);
//		e.setCell(3, 1);
//		e.setCell(4, "重要");
//		e.setCell(5, "专业");
//		e.setCell(6, "专业");
//		e.setCell(7, "专业");
//		e.setCell(8, "2010-12-03 00:00:00");
//		try {
//			e.export();
//			System.out.println(" 导出Excel文件[成功] ");
//		} catch (IOException ex) {
//			System.out.println(" 导出Excel文件[失败] ");
//			ex.printStackTrace();
//		}
//	}
//}
