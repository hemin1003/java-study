package com.minbo.javademo.elasticsearch;

import java.net.InetAddress;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class TestEsClient {
	
	public static void main(String[] args) {
		try {
			String ip = "192.168.56.101";
			// 设置集群名称
			Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
			// 创建client
			TransportClient client = new PreBuiltTransportClient(settings)
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), 9300));
			// 搜索数据
			GetResponse response = client.prepareGet("blog", "article", "1").execute().actionGet();
			// 输出结果
			System.out.println(response.getSourceAsString());
			// 关闭client
			client.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
