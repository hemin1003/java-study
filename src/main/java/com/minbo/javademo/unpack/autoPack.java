package com.minbo.javademo.unpack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class autoPack {

	private static String PWD = "";
	private static String apkFile = "";
	
	public static void main(String[] args) throws Exception {
		if(args.length<4) {
			System.out.println("请指明运行参数");
			System.exit(0);
		}
		
		String oldChannel = args[0];
		String oldVersionCode = args[1];
		String oldVersionName = args[2];
		PWD = args[3];
		
		long startTime = System.currentTimeMillis();
		System.out.println("启动打包程序");
		System.out.println();
		Runtime.getRuntime().exec("rm -rf package/*.*");
		
		try {
			String desc = "desc.txt";
			File f = new File(desc);
			if (f.exists()) {
				FileReader fr = new FileReader(desc);
				BufferedReader br = new BufferedReader(fr); // 建立BufferedReader对象，并实例化为br
				String line = br.readLine(); // 从文件读取一行字符串
				// 判断读取到的字符串是否不为空
				while (line != null) {
					process(line, oldChannel, oldVersionCode, oldVersionName);
					line = br.readLine(); // 从文件中继续读取一行数据
				}
				br.close(); // 关闭BufferedReader对象
				fr.close(); // 关闭文件
			}

		} catch (IOException e) {
			throw e;
		}
		
		System.out.println();
		System.out.println("打包程序运行结束");
		
		//删除目录
		Runtime.getRuntime().exec("rm -rf " + apkFile);
		long endTime = System.currentTimeMillis();
		System.out.println("总运行时间：" + (endTime - startTime)/1000 + " s");
		
		System.exit(0);
	}
	
	public static void process(String desc, String oldChannel, 
			String oldVersionCode, String oldVersionName) throws Exception {
		System.out.println("--------------------");
		System.out.println("--------------------");
		System.out.println("准备开始打包");
		System.out.println();
		
		String[] str = desc.split(",");
		String newChannel = str[0];
		String newVersionCode = str[1];
		String newVersionName = str[2];
		
		System.out.println("旧版本信息：oldChannel=" + oldChannel + ", oldVersionCode=" + oldVersionCode 
				+ ", oldVersionName=" + oldVersionName);
		System.out.println("新版本信息：newChannel=" + newChannel + ", newVersionCode=" + newVersionCode 
				+ ", newVersionName=" + newVersionName);
		
		System.out.println();
		System.out.println("第一步：解压apk包");
		long startTime = System.currentTimeMillis();
		//解压apk包
		String akpName = "";
		File file = new File(".");
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File file2 : files) {
				String name = file2.getName();
				if (file2.getName().contains(".apk")) {
					String command = "java -jar apktool.jar d -f " + name + " --frame-path framework/";
					Process process = Runtime.getRuntime().exec(command);
					int flag = process.waitFor();
					akpName = name.replaceAll(".apk", "");
					System.out.println("解压apk包完成，akpName=" + akpName + "，flag=" + flag);
					if(flag!=0) {
						System.out.println("操作失败");
					}
					apkFile = akpName;
				}
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("运行时间：" + (endTime - startTime)/1000 + " s");
		
		System.out.println();
		System.out.println("第二步：处理两个配置文件");
		long startTime2 = System.currentTimeMillis();
		//处理AndroidManifest.xml文件
		String androidManifest = akpName + "/AndroidManifest.xml";
		String content = getFileContent(androidManifest);
		String newContent = content.replaceAll(oldChannel, newChannel);
		File file2 = new File(androidManifest);
		if (file2.exists()) {
			boolean result = file2.delete();
			if (result) {
				boolean create = file2.createNewFile();
				if(create) {
					writeFileContent(akpName + "/" + file2.getName(), newContent);
				}
			}
		}
		
		//处理apktool.yml文件
		String apktool = akpName + "/apktool.yml";
		String content2 = getFileContent(apktool);
		String newContent2 = content2.replaceAll("versionCode: '" + oldVersionCode +"'", "versionCode: '" + newVersionCode + "'");
		newContent2 = newContent2.replaceAll("versionName: " + oldVersionName, "versionName: " + newVersionName);
		File file3 = new File(apktool);
		if (file3.exists()) {
			boolean result = file3.delete();
			if (result) {
				boolean create = file3.createNewFile();
				if(create) {
					writeFileContent(akpName + "/" + file3.getName(), newContent2);
				}
			}
		}
		long endTime2 = System.currentTimeMillis();
		System.out.println("运行时间：" + (endTime2 - startTime2)/1000 + " s");
		
		String unSignFile = akpName + "-" + newChannel + ".apk";
		String signFile = akpName + "-" + newChannel + "_signed.apk";
		System.out.println();
		System.out.println("第三步：打非签名包");
		packUnSigned(akpName, unSignFile);
		
		System.out.println();
		System.out.println("第四步：打签名包");
		packSigned(unSignFile, signFile);
	}
	
	//打非签名包
	public static void packUnSigned(String akpName, String unSignFile) throws Exception {
		System.out.println("unSignFile=" + unSignFile);
		long startTime = System.currentTimeMillis();
		String command = "java -jar apktool.jar b " + akpName + " -o package/" + unSignFile;
		Process process = Runtime.getRuntime().exec(command);
		int flag = process.waitFor();
		System.out.println("打非签名包完成，newApkName=" + unSignFile + "，flag=" + flag);
		if(flag!=0) {
			System.out.println("操作失败");
		}
		long endTime = System.currentTimeMillis();
		System.out.println("运行时间：" + (endTime - startTime)/1000 + " s");
		
		Thread.sleep(150);
	}
	
	//打签名包
	public static void packSigned(String unSignFile, String signFile) throws Exception {
		System.out.println("signFile=" + signFile);
		System.out.println("unSignFile=" + unSignFile);
		long startTime = System.currentTimeMillis();
		String command = "jarsigner -verbose -keystore yttreading.keystore -signedjar package/" + signFile 
				+ " package/" + unSignFile + " yttreading.keystore -storepass " + PWD;
		Runtime.getRuntime().exec(command);
		System.out.println("打签名包完成，newApkName=" + signFile);
		long endTime = System.currentTimeMillis();
		System.out.println("运行时间：" + (endTime - startTime)/1000 + " s");
		
		Thread.sleep(300);
		Runtime.getRuntime().exec("rm -rf package/" + unSignFile);
	}
	
	/**
	 * 读取文件的内容 读取指定文件的内容
	 * @param path 为要读取文件的绝对路径
	 * @return 以行读取文件后的内容。
	 * @since 1.0
	 */
	public static final String getFileContent(String path) throws IOException {
		String filecontent = "";
		try {
			File f = new File(path);
			if (f.exists()) {
				FileReader fr = new FileReader(path);
				BufferedReader br = new BufferedReader(fr); // 建立BufferedReader对象，并实例化为br
				String line = br.readLine(); // 从文件读取一行字符串
				// 判断读取到的字符串是否不为空
				while (line != null) {
					filecontent += line + "\n";
					line = br.readLine(); // 从文件中继续读取一行数据
				}
				br.close(); // 关闭BufferedReader对象
				fr.close(); // 关闭文件
			}

		} catch (IOException e) {
			throw e;
		}
		return filecontent;
	}
	
	 /**
     * 向文件中写入内容
     * @param filepath 文件路径与名称
     * @param newstr  写入的内容
     */
    public static boolean writeFileContent(String filepath,String newstr) throws IOException{
        Boolean bool = false;
        String filein = newstr+"\r\n";//新写入的行，换行
        String temp  = "";
        
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos  = null;
        PrintWriter pw = null;
        try {
            File file = new File(filepath);//文件路径(包括文件名称)
            //将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();
            
            //文件原有内容
            for(int i=0;(temp =br.readLine())!=null;i++){
                buffer.append(temp);
                // 行与行之间的分隔符 相当于“\n”
                buffer = buffer.append(System.getProperty("line.separator"));
            }
            buffer.append(filein);
            
            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buffer.toString().toCharArray());
            pw.flush();
            bool = true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally {
            //不要忘记关闭
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return bool;
    }
}
