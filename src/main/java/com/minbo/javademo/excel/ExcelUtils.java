//package com.minbo.javademo.excel;
//
//import java.awt.Image;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.Collection;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFDataFormat;
//import org.apache.poi.hssf.usermodel.HSSFDateUtil;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGEncodeParam;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;
//
//public class ExcelUtils {
//	// 操作日志
//	private static Logger logger = Logger.getLogger(ExcelUtils.class);
//
//	/**
//	 * 返回单个Excel所有的数据
//	 * 
//	 * @param request
//	 * @return List 返回行的记录 row = list.get(rowid) , column=row[column]
//	 */
//	public static List getAllExcelDataByRequest(HttpServletRequest request) {
//		try {
//			DiskFileItemFactory factory = new DiskFileItemFactory();
//			// 设置缓冲区大小，这里是4MB
//			factory.setSizeThreshold(4194304);
//			ServletFileUpload fu = new ServletFileUpload(factory);
//			// 设置最大文件尺寸，这里是10MB
//			fu.setSizeMax(10194304);
//			// 设置文件的头编码
//			request.setCharacterEncoding("UTF-8");
//			// 得到所有的文件：
//			List fileItems = fu.parseRequest(request);
//			Iterator it = fileItems.iterator();
//			if (it.hasNext()) {
//				FileItem fi = (FileItem) it.next();
//				// 获得文件名
//				String fileName = fi.getName();
//				if (fileName != null) {
//					// 文件类型
//					String extfile = fileName.substring(fileName.lastIndexOf("."));
//					return readExcel(fi.getInputStream(), extfile);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * excel解析
//	 * 
//	 * @param fileName
//	 *            文件名
//	 * @param fileType
//	 *            文件类型
//	 * @return
//	 * @throws Exception
//	 */
//	public static List<String[]> readExcel(InputStream inputStream, String fileType) throws Exception {
//		Workbook workbook = null;
//		List<String[]> list = new ArrayList<String[]>();
//		try {
//			if (fileType.equals(".xls")) {
//				workbook = new HSSFWorkbook(inputStream);
//			} else if (fileType.equals(".xlsx")) {
//				workbook = new XSSFWorkbook(inputStream);
//			} else {
//			}
//			// 创建sheet对象
//			Sheet sheet = workbook.getSheetAt(0);
//			if (sheet == null) {
//				return null;
//			}
//			int firstRowIndex = sheet.getFirstRowNum();// 首行
//			int lastRowIndex = sheet.getLastRowNum();// 尾行
//			// int totalRows = sheet.getPhysicalNumberOfRows();//总行数
//			int coloumNum = sheet.getRow(0).getPhysicalNumberOfCells();// 获得总列数
//			for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
//				Row row = sheet.getRow(rIndex);
//				if (row != null) {
//					int firstCellIndex = row.getFirstCellNum();
//					int lastCellIndex = row.getLastCellNum();
//					String[] s = new String[coloumNum];
//					for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {
//						Cell cell = row.getCell(cIndex);
//						if (cell != null) {
//							s[cIndex] = parseExcel(cell);
//						} else {
//							s[cIndex] = "";
//						}
//					}
//					list.add(s);
//				}
//			}
//		} catch (Exception e) {
//			throw e;
//		}
//		return list;
//	}
//
//	/**
//	 * excel数据格式处理
//	 * 
//	 * @param cell
//	 * @return
//	 */
//	public static String parseExcel(Cell cell) {
//		String result = new String();
//		switch (cell.getCellType()) {
//		case HSSFCell.CELL_TYPE_NUMERIC:// 数字类型
//			if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
//				SimpleDateFormat sdf = null;
//				if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
//					sdf = new SimpleDateFormat("h:mm");
//				} else if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("yyyy-MM-dd")) {
//					sdf = new SimpleDateFormat("yyyy-MM-dd");
//				} else {// 日期
//					sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				}
//				Date date = cell.getDateCellValue();
//				result = sdf.format(date);
//			} else {
//				double value = cell.getNumericCellValue();
//				CellStyle style = cell.getCellStyle();
//				DecimalFormat format = new DecimalFormat();
//				String temp = style.getDataFormatString();
//				// 单元格设置成常规
//				if (temp.equals("General")) {
//					format.applyPattern("#");
//				}
//				result = format.format(value);
//			}
//			break;
//		case HSSFCell.CELL_TYPE_STRING:// String类型
//			result = cell.getRichStringCellValue().toString();
//			break;
//		case HSSFCell.CELL_TYPE_BLANK:
//			result = "";
//		default:
//			result = "";
//			break;
//		}
//		return result;
//	}
//
//	public static String randomRename(String fileName, String dir) {
//		String[] split = fileName.split("\\.");// 将文件名已.的形式拆分
//		String extendFile = "." + split[split.length - 1].toLowerCase(); // 获文件的有效后缀
//
//		Random random = new Random();
//		int add = random.nextInt(1000000); // 产生随机数10000以内
//		String ret = add + extendFile;
//		while (isFileExist(ret, dir)) {
//			add = random.nextInt(1000000);
//			ret = fileName + add + extendFile;
//		}
//		File file = new File(dir + fileName);
//		File reFile = new File(dir + ret);
//		file.renameTo(reFile);
//		String name = reFile.getName();
//		file = null;
//		reFile = null;
//		return name;
//	}
//
//	/**
//	 * 删除文件
//	 * 
//	 * @param filePathAndName
//	 *            String 文件路径及名称 如c:/fqf.txt
//	 * @param fileContent
//	 *            String
//	 * @return boolean
//	 */
//	public static boolean delFile(String filePathAndName) {
//		File myDelFile = new java.io.File(filePathAndName);
//		if (!myDelFile.exists()) {
//			return true;
//		}
//		return myDelFile.delete();
//	}
//
//	/**
//	 * 上传文件
//	 * 
//	 * @param uploadFileName
//	 *            被上传的文件名称
//	 * @param savePath
//	 *            文件的保存路径
//	 * @param uploadFile
//	 *            被上传的文件
//	 * @return newFileName
//	 */
//	public static String upload(String uploadFileName, String savePath, File uploadFile) {
//		String newFileName = getRandomName(uploadFileName, savePath);
//		try {
//			FileOutputStream fos = new FileOutputStream(savePath + newFileName);
//			FileInputStream fis = new FileInputStream(uploadFile);
//			byte[] buffer = new byte[1024];
//			int len = 0;
//			while ((len = fis.read(buffer)) > 0) {
//				fos.write(buffer, 0, len);
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return newFileName;
//	}
//
//	/**
//	 * 根据路径创建一系列的目录
//	 * 
//	 * @param path
//	 */
//	public static void mkDirectory(String path) {
//		File file;
//		try {
//			file = new File(path);
//			if (!file.exists()) {
//				file.mkdirs();
//			}
//		} catch (RuntimeException e) {
//			e.printStackTrace();
//		} finally {
//			file = null;
//		}
//	}
//
//	/**
//	 * 将对象数组的每一个元素分别添加到指定集合中,调用Apache commons collections 中的方法
//	 * 
//	 * @param collection
//	 *            目标集合对象
//	 * @param arr
//	 *            对象数组
//	 */
//	public static void addToCollection(Collection collection, Object[] arr) {
//		if (null != collection && null != arr) {
//			CollectionUtils.addAll(collection, arr);
//		}
//	}
//
//	/**
//	 * 将字符串已多个分隔符拆分为数组,调用Apache commons lang 中的方法
//	 * 
//	 * <pre>
//	 *    Example:
//	 *     String[] arr = StringUtils.split("a b,c d,e-f", " ,");
//	 *     System.out.println(arr.length);//输出6
//	 * </pre>
//	 * 
//	 * @param str
//	 *            目标字符串
//	 * @param separatorChars
//	 *            分隔符字符串
//	 * @return 字符串数组
//	 */
//	public static String[] split(String str, String separatorChars) {
//		return StringUtils.split(str, separatorChars);
//	}
//
//	/**
//	 * 调用指定字段的setter方法
//	 * 
//	 * <pre>
//	 *    Example:
//	 *    User user = new User();
//	 *    MyUtils.invokeSetMethod("userName", user, new Object[] {"张三"});
//	 * </pre>
//	 * 
//	 * @param fieldName
//	 *            字段(属性)名称
//	 * @param invokeObj
//	 *            被调用方法的对象
//	 * @param args
//	 *            被调用方法的参数数组
//	 * @return 成功与否
//	 */
//	public static boolean invokeSetMethod(String fieldName, Object invokeObj, Object[] args) {
//		boolean flag = false;
//		Field[] fields = invokeObj.getClass().getDeclaredFields(); // 获得对象实体类中所有定义的字段
//		Method[] methods = invokeObj.getClass().getDeclaredMethods(); // 获得对象实体类中所有定义的方法
//		for (Field f : fields) {
//			String fname = f.getName();
//			if (fname.equals(fieldName)) {// 找到要更新的字段名
//				String mname = "set" + (fname.substring(0, 1).toUpperCase() + fname.substring(1));// 组建setter方法
//				for (Method m : methods) {
//					String name = m.getName();
//					if (mname.equals(name)) {
//						// 处理Integer参数
//						if (f.getType().getSimpleName().equalsIgnoreCase("integer") && args.length > 0) {
//							args[0] = Integer.valueOf(args[0].toString());
//						}
//						// 处理Boolean参数
//						if (f.getType().getSimpleName().equalsIgnoreCase("boolean") && args.length > 0) {
//							args[0] = Boolean.valueOf(args[0].toString());
//						}
//						try {
//							m.invoke(invokeObj, args);
//							flag = true;
//						} catch (IllegalArgumentException e) {
//							flag = false;
//							e.printStackTrace();
//						} catch (IllegalAccessException e) {
//							flag = false;
//							e.printStackTrace();
//						} catch (InvocationTargetException e) {
//							flag = false;
//							e.printStackTrace();
//						}
//					}
//				}
//			}
//		}
//		return flag;
//	}
//
//	/**
//	 * 判断文件是否存在
//	 * 
//	 * @param fileName
//	 * @param dir
//	 * @return
//	 */
//	public static boolean isFileExist(String fileName, String dir) {
//		File files = new File(dir + fileName);
//		return (files.exists()) ? true : false;
//	}
//
//	/**
//	 * 获得随机文件名,保证在同一个文件夹下不同名
//	 * 
//	 * @param fileName
//	 * @param dir
//	 * @return
//	 */
//	public static String getRandomName(String fileName, String dir) {
//		String[] split = fileName.split("\\.");// 将文件名已.的形式拆分
//		String extendFile = "." + split[split.length - 1].toLowerCase(); // 获文件的有效后缀
//
//		Random random = new Random();
//		int add = random.nextInt(1000000); // 产生随机数10000以内
//		String ret = add + extendFile;
//		while (isFileExist(ret, dir)) {
//			add = random.nextInt(1000000);
//			ret = fileName + add + extendFile;
//		}
//		return ret;
//	}
//
//	/**
//	 * 创建缩略图
//	 * 
//	 * @param file
//	 *            上传的文件流
//	 * @param height
//	 *            最小的尺寸
//	 * @throws IOException
//	 */
//	public static void createMiniPic(File file, float width, float height) throws IOException {
//		Image src = javax.imageio.ImageIO.read(file); // 构造Image对象
//		int old_w = src.getWidth(null); // 得到源图宽
//		int old_h = src.getHeight(null);
//		int new_w = 0;
//		int new_h = 0; // 得到源图长
//		float tempdouble;
//		if (old_w >= old_h) {
//			tempdouble = old_w / width;
//		} else {
//			tempdouble = old_h / height;
//		}
//
//		if (old_w >= width || old_h >= height) { // 如果文件小于锁略图的尺寸则复制即可
//			new_w = Math.round(old_w / tempdouble);
//			new_h = Math.round(old_h / tempdouble);// 计算新图长宽
//			while (new_w > width && new_h > height) {
//				if (new_w > width) {
//					tempdouble = new_w / width;
//					new_w = Math.round(new_w / tempdouble);
//					new_h = Math.round(new_h / tempdouble);
//				}
//				if (new_h > height) {
//					tempdouble = new_h / height;
//					new_w = Math.round(new_w / tempdouble);
//					new_h = Math.round(new_h / tempdouble);
//				}
//			}
//			BufferedImage tag = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
//			tag.getGraphics().drawImage(src, 0, 0, new_w, new_h, null); // 绘制缩小后的图
//			FileOutputStream newimage = new FileOutputStream(file); // 输出到文件流
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
//			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(tag);
//			param.setQuality((float) (100 / 100.0), true);// 设置图片质量,100最大,默认70
//			encoder.encode(tag, param);
//			encoder.encode(tag); // 将JPEG编码
//			newimage.close();
//		}
//	}
//
//	/**
//	 * 判断文件类型是否是合法的,就是判断allowTypes中是否包含contentType
//	 * 
//	 * @param contentType
//	 *            文件类型
//	 * @param allowTypes
//	 *            文件类型列表
//	 * @return 是否合法
//	 */
//	public static boolean isValid(String contentType, String[] allowTypes) {
//		if (null == contentType || "".equals(contentType)) {
//			return false;
//		}
//		for (String type : allowTypes) {
//			if (contentType.equals(type)) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	/**
//	 * 生成excel格式文件到输出流中
//	 * 
//	 * @param title
//	 *            excel表头
//	 * @param list
//	 *            数据内容
//	 * @param os
//	 *            输出流
//	 */
//	public static void createExcel(String[] title, String[] headFields, List<Object> list, OutputStream os) {
//		try {
//			ExcelWriter excelWriter = new ExcelWriter(os);
//			excelWriter.createRow(0);
//			for (int i = 0; i < title.length; i++) {
//				excelWriter.setCell(i, title[i]);
//			}
//			if (list != null) {
//				for (int i = 0; i < list.size(); i++) {
//					Map<String, Object> dataMap = (Map<String, Object>) list.get(i);
//					excelWriter.createRow(i + 1);
//					for (int j = 0; j < headFields.length; j++) {
//						Object value = dataMap.get(headFields[j]) == null ? "" : dataMap.get(headFields[j]);
//						if (value instanceof Long) {
//							excelWriter.setCell(j, (Long) value);
//						} else if (value instanceof Double) {
//							excelWriter.setCell(j, (Double) value);
//						} else if (value instanceof Calendar) {
//							excelWriter.setCell(j, (Calendar) value);
//						} else {
//							excelWriter.setCell(j, String.valueOf(value));
//						}
//
//					}
//				}
//			}
//			excelWriter.export();
//		} catch (IOException e) {
//			logger.error("写入文件异常:" + e.getMessage(), e);
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 获取文件名和Excel数据
//	 * 
//	 * @param request
//	 * @return
//	 * @author HanZhijun
//	 */
//	@SuppressWarnings("rawtypes")
//	public static Map<String, Object> getFileNameAndDataList(HttpServletRequest request) {
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//
//		try {
//			DiskFileItemFactory factory = new DiskFileItemFactory();
//			// 设置缓冲区大小，这里是4kb
//			factory.setSizeThreshold(4096);
//			ServletFileUpload fu = new ServletFileUpload(factory);
//			// 设置最大文件尺寸，这里是4MB
//			fu.setSizeMax(4194304);
//			// 得到所有的文件：
//			List fileItems = fu.parseRequest(request);
//			Iterator it = fileItems.iterator();
//			if (it.hasNext()) {
//				FileItem fi = (FileItem) it.next();
//				// 获得文件名
//				String fileName = fi.getName();
//				if (fileName != null) {
//					// 文件类型
//					String extfile = fileName.substring(fileName.indexOf("."));
//					List<String[]> dataList = readExcel(fi.getInputStream(), extfile);
//					logger.info("=======================读取xls文件==============================");
//					logger.info("xls文件记录数是：" + dataList.size());
//					Iterator<String[]> listIt = dataList.iterator();
//					while (listIt.hasNext()) {
//						String[] arr = listIt.next();
//						logger.info("xls数据是：" + Arrays.toString(arr));
//					}
//					logger.info("=====================================================");
//
//					resultMap.put("dataList", dataList);
//
//					// 获得文件绝对路径
//					fileName = getFileName(fileName);
//					logger.info("****************************************************");
//					logger.info("上传的文件名是：" + fileName);
//					logger.info("****************************************************");
//
//					resultMap.put("fileName", fileName);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return resultMap;
//	}
//
//	/**
//	 * 获取文件名 文件应包含xls和xlsx两种Excel文件
//	 * 
//	 * @param filePath
//	 *            文件的绝对路径
//	 * @return
//	 */
//	private static String getFileName(String filePath) {
//		String fileName = "";
//		// 最后一个"\"的位置
//		int last1 = filePath.lastIndexOf("\\");
//		// 最后一个"."的位置
//		int last2 = filePath.lastIndexOf(".");
//		fileName = filePath.substring(last1 + 1, last2);
//		return fileName;
//	}
//}