package com.minbo.javademo.hdfs2;

import java.io.IOException;
import java.net.URISyntaxException;

public class Test {

	public static void main(String[] args) throws IOException, URISyntaxException {
		//设置hadoop安装路径，如果配置了环境变量则不需要了
		System.setProperty("hadoop.home.dir", "F:\\hadoop\\hadoop-2.7.3");
		
		// 显示目录下所有文件
		// HdfsUtils.ListDirAll("hdfs://192.168.0.144:9000/user/hadoop");
		// HdfsUtils.ListDirAll2("hdfs://192.168.0.144:9000/user/hadoop");

		String fileWrite = "hdfs://192.168.0.144:9000/user/hadoop/hm.txt";

		// 1. 写操作
		// String words = "This words is to write into file!\n";
		// HdfsUtils.WriteToHDFS(fileWrite, words);

		// 2. 读操作，方式一
		 HdfsUtils.ReadFromHDFS(fileWrite);

		// 2. 读操作，方式二
		// byte[] buffer = HdfsUtils.ReadFromHDFS2(fileWrite);
		// System.out.println("buffer.length = " + buffer.length);
		// String content = new String(buffer);
		// System.out.println("content = " + content);

		// 3. 删除操作
		// HdfsUtils.DeleteHDFSFile(fileWrite);

		// 本地上传文件到HDFS
		// String LocalFile = "file:///home//hadoop//hm.txt";
		// String LocalFile = "c://hm.txt";
		// HdfsUtils.UploadLocalFileHDFS(LocalFile, fileWrite);
	}

}
