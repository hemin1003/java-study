package com.minbo.javademo.hdfs;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class ReadFile {
	public static void main(String[] args) throws IOException {
		System.setProperty("hadoop.home.dir", "F:\\hadoop\\hadoop-2.7.3");
		
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		Path path = new Path("/user/hadoop/hm.txt");
		if (fs.exists(path)) {
			FSDataInputStream in = fs.open(path);
			FileStatus status = fs.getFileStatus(path);
			byte[] buffer = new byte[Integer.parseInt(String.valueOf(status.getLen()))];
			in.readFully(0, buffer);
			in.close();
			fs.close();
			System.out.println(buffer.toString());
			String s = new String(buffer);
			System.out.println(s.toString());
		}
		System.out.println("done");
	}
}
