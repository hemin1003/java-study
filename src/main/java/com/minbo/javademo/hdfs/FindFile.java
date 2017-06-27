package com.minbo.javademo.hdfs;

import java.io.IOException;  
import org.apache.hadoop.conf.Configuration;  
import org.apache.hadoop.fs.BlockLocation;  
import org.apache.hadoop.fs.FileStatus;  
import org.apache.hadoop.fs.FileSystem;  
import org.apache.hadoop.fs.Path;  

public class FindFile {
	public static void main(String[] args) throws IOException {   
        getFileLocal();
        
        System.out.println("done");
    }  
      
    /** 
     * 查找某个文件在HDFS集群的位置 
     */  
    public static void getFileLocal() throws IOException{  
        Configuration conf = new Configuration();  
        FileSystem fs = FileSystem.get(conf);  
        Path path = new Path("/user/hadoop/word.txt");  
          
        FileStatus status = fs.getFileStatus(path);  
        BlockLocation[] locations = fs.getFileBlockLocations(status, 0, status.getLen());  
          
        int length = locations.length;  
        for(int i=0;i<length;i++){  
            String[] hosts = locations[i].getHosts();  
            System.out.println("block_" + i + "_location:" + hosts[i]);  
        }  
    }  
}
