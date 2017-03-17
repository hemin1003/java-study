package com.minbo.javademo.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class Test {

	public static void main(String[] args) {
		// try {
		// // 连接到 mongodb 服务
		// MongoClient mongoClient = new MongoClient("localhost", 27017);
		//
		// // 连接到数据库
		// MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
		// System.out.println("Connect to database successfully");
		//
		// } catch (Exception e) {
		// System.err.println(e.getClass().getName() + ": " + e.getMessage());
		// }

		try {
			// 连接到 mongodb 服务
			MongoClient mongoClient = new MongoClient("localhost", 27017);

			// 连接到数据库
			MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
			System.out.println("Connect to database successfully");

			MongoCollection<org.bson.Document> collection = mongoDatabase.getCollection("Person");
			System.out.println("集合 Person 选择成功");

			// 检索所有文档
			/**
			 * 1. 获取迭代器FindIterable<Document> 2. 获取游标MongoCursor<Document> 3.
			 * 通过游标遍历检索出的文档集合
			 */
			FindIterable<org.bson.Document> findIterable = collection.find();
			MongoCursor<org.bson.Document> mongoCursor = findIterable.iterator();
			while (mongoCursor.hasNext()) {
				System.out.println(mongoCursor.next());
			}

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}
