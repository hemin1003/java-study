package com.minbo.javademo.elasticsearch;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.io.IOException;
import java.net.InetAddress;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;  

public class TestEsSearch {
	
	public static void main(String[] args) throws IOException {
		String ip = "192.168.56.101";
		
		Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
		Client client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), 9300));

		QueryBuilder qb1 = termQuery("price", 100); 
		SearchResponse response = client.prepareSearch("productindex")
		        .setQuery(qb1) 	// Query
		        .setFrom(0).setExplain(true)     
		        .execute()     
		        .actionGet(); 
		
		System.out.println("getTotalHits=" + response.getHits().getTotalHits());
		System.out.println("");
		
		SearchHits hits = response.getHits();  
		for (int i = 0; i < hits.getTotalHits(); i++) {
			System.out.println("----------------------");
			System.out.println("Index="+hits.getAt(i).getIndex());
			System.out.println("Type="+hits.getAt(i).getType());
			System.out.println("Id="+hits.getAt(i).getId());
			System.out.println("Version="+hits.getAt(i).getVersion());
		    System.out.println("createDate="+hits.getAt(i).getSource().get("createDate"));
		}    
		
		client.close();
	}
	
}
