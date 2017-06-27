package com.minbo.javademo.hdfs;

import java.io.IOException;  
import org.apache.hadoop.conf.Configuration;  
import org.apache.hadoop.fs.FileStatus;  
import org.apache.hadoop.fs.FileSystem;  
import org.apache.hadoop.fs.Path;  

public class GetAllChildFile {
	static Configuration conf = new Configuration();  
    
    public static void main(String[] args)throws IOException {  
        FileSystem fs = FileSystem.get(conf);  
        Path path = new Path("/user/hadoop");  
        getFile(path,fs);  
        //fs.close();  
        
        System.out.println("done");
    }  
      
    public static void getFile(Path path,FileSystem fs) throws IOException {  
          
        FileStatus[] fileStatus = fs.listStatus(path);  
        for(int i=0;i<fileStatus.length;i++){  
            if(fileStatus[i].isDir()){  
                Path p = new Path(fileStatus[i].getPath().toString());  
                getFile(p,fs);  
            }else{  
                System.out.println(fileStatus[i].getPath().toString());  
            }  
        }  
    }  
}
