package com.minbo.javademo.elasticsearch;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class TestEsCreateIndex {
	
	public static void main(String[] args) throws IOException {
		String ip = "192.168.56.101";
		
		Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
		Client client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), 9300));
		
		//索引名称必须为小写
//		client.admin().indices().prepareCreate("productindex").execute().actionGet();
		
//		XContentBuilder mapping = XContentFactory.jsonBuilder()
//				.startObject()
//					.startObject("properties")
//						.startObject("title").field("type", "string").field("store", "yes").endObject()
//						.startObject("description").field("type", "string").field("index", "not_analyzed").endObject()  
//						.startObject("price").field("type", "double").endObject()
//						.startObject("onSale").field("type", "boolean").endObject()
//						.startObject("type").field("type", "integer").endObject()
//						.startObject("createDate").field("type", "date").endObject()
//					.endObject()
//				.endObject();
//		PutMappingRequest mappingRequest = Requests.putMappingRequest("productindex").type("productIndex").source(mapping); 
//		client.admin().indices().putMapping(mappingRequest).actionGet();
		
		XContentBuilder doc = XContentFactory.jsonBuilder()    
			      .startObject()         
			          .field("title", "this is a title!")    
			          .field("description", "descript what?")     
			          .field("price", 100)    
			          .field("onSale", true)    
			          .field("type", 1)    
			          .field("createDate", new Date())   
			     .endObject();
		
		IndexResponse response = client.prepareIndex("productindex", "productType", "1").setSource(doc).execute().actionGet();
		// Index name
		String _index = response.getIndex();
		System.out.println("_index = " +_index);
		
		// Type name
		String _type = response.getType();
		System.out.println("_type = " + _type);
		
		// Document ID (generated or not)
		String _id = response.getId();
		System.out.println("_id = " + _id);
		// Version (if it's the first time you index this document, you will get: 1)
		long _version = response.getVersion();
		System.out.println("_version = " + _version);
		
		// status has stored current instance statement.
		RestStatus status = response.status();
		System.out.println("status = " + status);
		
		client.close();
	}
	
}
