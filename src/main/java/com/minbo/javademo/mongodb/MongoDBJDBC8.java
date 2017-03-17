package com.minbo.javademo.mongodb;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

/**
 * delete用法
 */
public class MongoDBJDBC8 {

	public static void main(String[] args) {
		try {
			// 连接到 mongodb 服务
			MongoClient mongoClient = new MongoClient("localhost", 27017);

			// 连接到数据库
			MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
			System.out.println("Connect to database successfully");

			MongoCollection<Document> collection = mongoDatabase.getCollection("Person");
			System.out.println("集合 Person 选择成功");

			// 删除符合条件的第一个文档
			collection.deleteOne(Filters.eq("name", "test"));
			// 删除所有符合条件的文档
			collection.deleteMany(Filters.eq("age", 40));
			
			// 检索查看结果
			FindIterable<Document> findIterable = collection.find();
			MongoCursor<Document> mongoCursor = findIterable.iterator();
			while (mongoCursor.hasNext()) {
				System.out.println(mongoCursor.next());
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}