package com.minbo.javademo.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * insert用法
 */
public class MongoDBJDBC5 {
	public static void main(String args[]) {
		try {
			// 连接到 mongodb 服务
			MongoClient mongoClient = new MongoClient("localhost", 27017);

			// 连接到数据库
			MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
			System.out.println("Connect to database successfully");

			MongoCollection<Document> collection = mongoDatabase.getCollection("Person");
			System.out.println("集合 Person 选择成功");
			
			// 插入文档
			/**
			 * 1. 创建文档 org.bson.Document 参数为key-value的格式 
			 * 2. 创建文档集合List<Document>
			 * 3. 将文档集合插入数据库集合中 mongoCollection.insertMany(List<Document>)
			 * 
			 * 插入单个文档可以用 mongoCollection.insertOne(Document)
			 */
			Document document = new Document("name", "MongoDB").append("age", "60").append("address", "beijing")
					.append("sex", "female");
			List<Document> documents = new ArrayList<Document>();
			documents.add(document);
			collection.insertMany(documents);
			System.out.println("文档插入成功");
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}