package com.minbo.javademo.hadoop.hdfs;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;

public class FindFileAll {
	
	public static void main(String[] args) throws IOException {
		getHDFSNode();
	}

	/**
	 * HDFS集群上所有节点名称信息
	 */
	public static void getHDFSNode() throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);

		DistributedFileSystem dfs = (DistributedFileSystem) fs;
		DatanodeInfo[] dataNodeStats = dfs.getDataNodeStats();

		for (int i = 0; i < dataNodeStats.length; i++) {
			System.out.println("DataNode_" + i + "_Node:" + dataNodeStats[i].getHostName());
		}

	}
}
