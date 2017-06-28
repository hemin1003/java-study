package com.minbo.javademo.hadoop.hdfs;

import java.io.IOException;  
import org.apache.hadoop.conf.Configuration;  
import org.apache.hadoop.fs.FileSystem;  
import org.apache.hadoop.fs.Path;  

public class CopyFromLocalFile {
	
	public static void main(String[] args) throws IOException {  
        Configuration conf = new Configuration();  
        FileSystem fs = FileSystem.get(conf);  
        Path src = new Path("/home/hadoop/word.txt");  
        Path dst = new Path("/user/hadoop/word.txt");  
        fs.copyFromLocalFile(src, dst);  
        fs.close();  
        
        System.out.println("done");
    }
}
