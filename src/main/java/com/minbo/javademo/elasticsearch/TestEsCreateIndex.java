package com.minbo.javademo.elasticsearch;

import java.io.IOException;
import java.net.InetAddress;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class TestEsCreateIndex {
	
	public static void main(String[] args) throws IOException {
		Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
		Client client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(GlobelUtils.IP), 9300));
		
		//1. 创建索引
		//索引名称必须为小写
		client.admin().indices().prepareCreate("productindex").execute().actionGet();
		
		XContentBuilder mapping = XContentFactory.jsonBuilder()
				.startObject()
					.startObject("properties")
						.startObject("title").field("type", "string").field("store", "yes").endObject()
						.startObject("description").field("type", "string").field("index", "not_analyzed").endObject()  
						.startObject("price").field("type", "double").endObject()
						.startObject("onSale").field("type", "boolean").endObject()
						.startObject("type").field("type", "integer").endObject()
						.startObject("createDate").field("type", "date").endObject()
					.endObject()
				.endObject();
		PutMappingRequest mappingRequest = Requests.putMappingRequest("productindex").type("productIndex").source(mapping); 
		client.admin().indices().putMapping(mappingRequest).actionGet();
		
		client.close();
		
		System.out.println("Done");
		
	}
}