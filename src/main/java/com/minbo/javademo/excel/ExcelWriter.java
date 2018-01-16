package com.minbo.javademo.excel;
//package com.sunyard.common.utils;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.util.Calendar;
//
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFDataFormat;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//
//public class ExcelWriter {
//	private static String NUMBER_FORMAT = "#,##0.00";
//	private static String DATE_FORMAT = "m/d/yy;";
//	private OutputStream out = null;
//	private HSSFWorkbook workbook = null;
//	private HSSFSheet sheet = null;
//	private HSSFRow row = null;
//
//	public ExcelWriter(OutputStream out) {
//		this.out = out;
//		this.workbook = new HSSFWorkbook();
//		this.sheet = workbook.createSheet();
//	}
//
//	/**
//	 * 导出Excel文件
//	 */
//	public void export() throws IOException {
//		try {
//			workbook.write(out);
//			out.flush();
//			out.close();
//		} catch (FileNotFoundException e) {
//			throw new IOException("生成导出Excel文件出错!");
//		} catch (IOException e) {
//			throw new IOException("写入Excel文件出错!");
//		}
//	}
//
//	/**
//	 * 增加一行
//	 */
//	public void createRow(int index) {
//		this.row = this.sheet.createRow(index);
//	}
//
//	/**
//	 * 获取单元格的值
//	 * 
//	 * @param index
//	 *            列号
//	 */
//	public String getCell(int index) {
//		HSSFCell cell = this.row.getCell((short) index);
//		String strExcelCell = "";
//		if (cell != null) // add this condition
//		{
//			// judge
//			switch (cell.getCellType()) {
//			case HSSFCell.CELL_TYPE_FORMULA:
//				strExcelCell = "FORMULA ";
//				break;
//			case HSSFCell.CELL_TYPE_NUMERIC: {
//				strExcelCell = String.valueOf(cell.getNumericCellValue());
//			}
//				break;
//			case HSSFCell.CELL_TYPE_STRING:
//				strExcelCell = cell.getStringCellValue();
//				break;
//			case HSSFCell.CELL_TYPE_BLANK:
//				strExcelCell = "";
//				break;
//			default:
//				strExcelCell = "";
//				break;
//			}
//		}
//		return strExcelCell;
//	}
//
//	/**
//	 * 设置单元格
//	 * 
//	 * @param index
//	 *            列号
//	 * @param value
//	 *            单元格填充值
//	 */
//	public void setCell(int index, int value) {
//		HSSFCell cell = this.row.createCell((short) index);
//		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//		cell.setCellValue(value);
//	}
//
//	/**
//	 * 设置单元格
//	 * 
//	 * @param index
//	 *            列号
//	 * @param value
//	 *            单元格填充值
//	 */
//	public void setCell(int index, double value) {
//		HSSFCell cell = this.row.createCell((short) index);
//		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//		cell.setCellValue(value);
//		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
//		HSSFDataFormat format = workbook.createDataFormat();
//		cellStyle.setDataFormat(format.getFormat(NUMBER_FORMAT)); // 设置cell样式为定制的浮点数格式
//		cell.setCellStyle(cellStyle); // 设置该cell浮点数的显示格式
//	}
//
//	/**
//	 * 设置单元格
//	 * 
//	 * @param index
//	 *            列号
//	 * @param value
//	 *            单元格填充值
//	 */
//	public void setCell(int index, String value) {
//		HSSFCell cell = this.row.createCell((short) index);
//		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//		cell.setCellValue(value);
//	}
//
//	/**
//	 * 设置单元格
//	 * 
//	 * @param index
//	 *            列号
//	 * @param value
//	 *            单元格填充值
//	 */
//	public void setCell(int index, Long value) {
//		HSSFCell cell = this.row.createCell((short) index);
//		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//		cell.setCellValue(value);
//	}
//	/**
//	 * 设置单元格
//	 * 
//	 * @param index
//	 *            列号
//	 * @param value
//	 *            单元格填充值
//	 */
//	public void setCell(int index, Calendar value) {
//		HSSFCell cell = this.row.createCell((short) index);
//		cell.setCellValue(value.getTime());
//		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
//		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat(DATE_FORMAT)); // 设置cell样式为定制的日期格式
//		cell.setCellStyle(cellStyle); // 设置该cell日期的显示格式
//	}
//}
