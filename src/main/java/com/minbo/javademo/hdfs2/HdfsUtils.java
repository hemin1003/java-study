package com.minbo.javademo.hdfs2;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class HdfsUtils {

	/**
	 * 在指定位置新建一个文件，并写入字符
	 * @param file
	 * @param words
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static void WriteToHDFS(String file, String words) throws IOException, URISyntaxException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(file), conf);
		Path path = new Path(file);
		FSDataOutputStream out = fs.create(path); // 创建文件

		// 两个方法都用于文件写入，好像一般多使用后者
		// out.writeBytes(words);
		out.write(words.getBytes("UTF-8"));

		out.close();
		// 如果是要从输入流中写入，或是从一个文件写到另一个文件（此时用输入流打开已有内容的文件）
		// 可以使用如下IOUtils.copyBytes方法。
		// FSDataInputStream in = fs.open(new Path(args[0]));
		// IOUtils.copyBytes(in, out, 4096, true) //4096为一次复制块大小，true表示复制完成后关闭流

		System.out.println("WriteToHDFS() is done");
	}

	/**
	 * 读文件内容
	 * @param file
	 * @throws IOException
	 */
	public static void ReadFromHDFS(String file) throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(file), conf);
		Path path = new Path(file);
		FSDataInputStream in = fs.open(path);

		IOUtils.copyBytes(in, System.out, 4096, false);
		System.out.println("ReadFromHDFS() is done");
	}

	/**
	 * 读文件内容
	 * @param file
	 * @throws IOException
	 */
	public static byte[] ReadFromHDFS2(String file) throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(file), conf);
		Path path = new Path(file);
		FSDataInputStream in = fs.open(path);
		// 使用FSDataInoutStream的read方法会将文件内容读取到字节流中并返回
		FileStatus stat = fs.getFileStatus(path); // create the buffer byte[]
		byte[] buffer = new byte[Integer.parseInt(String.valueOf(stat.getLen()))];
		in.readFully(0, buffer);
		in.close();
		fs.close();

		System.out.println("ReadFromHDFS2() is done");

		return buffer;
	}

	/**
	 * 可删除文件，也可删除目录
	 * @param file
	 * @throws IOException
	 */
	public static void DeleteHDFSFile(String file) throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(file), conf);
		Path path = new Path(file);
		// 查看fs的delete API可以看到三个方法。deleteonExit实在退出JVM时删除，下面的方法是在指定为目录是递归删除
		fs.delete(path, true);
		fs.close();

		System.out.println("DeleteHDFSFile() is done");
	}

	/**
	 * 上传本地文件到hdfs中
	 * @param src
	 * @param dst
	 * @throws IOException
	 */
	public static void UploadLocalFileHDFS(String src, String dst) throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(dst), conf);
		Path pathDst = new Path(dst);
		Path pathSrc = new Path(src);

		fs.copyFromLocalFile(pathSrc, pathDst);
		fs.close();

		System.out.println("UploadLocalFileHDFS() is done");
	}

	/**
	 * 输出目录下所有文件名
	 * @param DirFile
	 * @throws IOException
	 */
	public static void ListDirAll(String DirFile) throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(DirFile), conf);
		Path path = new Path(DirFile);

		FileStatus[] status = fs.listStatus(path);
		// 方式一
		for (FileStatus f : status) {
			System.out.println(f.getPath().toString());
		}
		System.out.println("ListDirAll() is done");
	}
	
	/**
	 * 输出目录下所有文件名
	 * @param DirFile
	 * @throws IOException
	 */
	public static void ListDirAll2(String DirFile) throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(DirFile), conf);
		Path path = new Path(DirFile);

		FileStatus[] status = fs.listStatus(path);
		// 方式二
		Path[] listedPaths = FileUtil.stat2Paths(status);
		for (Path p : listedPaths) {
			System.out.println(p.toString());
		}

		System.out.println("ListDirAll2() is done");
	}
}
