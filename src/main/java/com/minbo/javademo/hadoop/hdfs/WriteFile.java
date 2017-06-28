package com.minbo.javademo.hadoop.hdfs;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class WriteFile {

	public static void main(String[] args) throws IOException {
		// 设置hadoop安装路径
		System.setProperty("hadoop.home.dir", "F:\\hadoop\\hadoop-2.7.3");

		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		Path path = new Path("/user/hadoop/hm2.txt");
		FSDataOutputStream out = fs.create(path);

		// out.writeUTF("da jia hao,cai shi zhen de hao!");
		// out.writeChars("abcdefg");
		out.write("minbo".getBytes());

		fs.close();

		System.out.println("done");
	}

}
