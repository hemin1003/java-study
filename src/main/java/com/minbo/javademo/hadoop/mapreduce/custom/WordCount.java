package com.minbo.javademo.hadoop.mapreduce.custom;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCount {
	
	static class WordCountMapper extends Mapper<Object, Text, Text, IntWritable>{
		@Override
		protected void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			String[] words = value.toString().split(" ");
			for (String word : words) {
				context.write(new Text(word), new IntWritable(1));
			}
		}
	}
	
	static class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
		
		@Override
		protected void reduce(Text key, Iterable<IntWritable> values, Context context)
				throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable v : values) {
				sum += v.get();
			}
			context.write(key, new IntWritable(sum));
		}
	}

	public static void main(String[] args) throws Exception {
		// Window下运行设置
		System.setProperty("hadoop.home.dir", "F:\\hadoop\\hadoop-2.7.3");	//设置hadoop安装路径
		System.setProperty("HADOOP_USER_NAME", "hadoop");	//用户名
		
		Configuration conf = new Configuration();
		//新建一个job
		Job job = Job.getInstance(conf);
		//设置jar包所在路径
		job.setJarByClass(WordCount.class);
		//指定mapper和reducer类
		job.setMapperClass(WordCountMapper.class);
		job.setReducerClass(WordCountReducer.class);
		//指定maptask的输出类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		//指定reducetask的输出类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		//指定该mapreduce程序数据的输入输出路径
		Path inputPath = new Path("input");
		Path outputPath = new Path("output");
		FileInputFormat.setInputPaths(job, inputPath);
		FileOutputFormat.setOutputPath(job, outputPath);
		
		//最后提交任务
		boolean waitForCompletion = job.waitForCompletion(true);
		System.exit(waitForCompletion?0:1);
	}
}
