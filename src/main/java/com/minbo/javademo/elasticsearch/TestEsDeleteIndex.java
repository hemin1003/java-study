package com.minbo.javademo.elasticsearch;

import java.io.IOException;
import java.net.InetAddress;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class TestEsDeleteIndex {
	
	public static void main(String[] args) throws IOException {
		Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
		Client client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(GlobelUtils.IP), 9300));

		DeleteResponse response = client.prepareDelete("productindex", "productType", "2").execute().actionGet();
		
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
