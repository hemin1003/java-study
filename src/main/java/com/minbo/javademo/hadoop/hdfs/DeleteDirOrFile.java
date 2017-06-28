package com.minbo.javademo.hadoop.hdfs;

import java.io.IOException;  
import org.apache.hadoop.conf.Configuration;  
import org.apache.hadoop.fs.FileSystem;  
import org.apache.hadoop.fs.Path;  

public class DeleteDirOrFile {

	public static void main(String[] args) throws IOException {
		Configuration conf = new Configuration();  
        FileSystem fs = FileSystem.get(conf);  
          
        Path path = new Path("/user/local/");  
        fs.delete(path);  
        fs.close();
        
        System.out.println("done");
	}

}
