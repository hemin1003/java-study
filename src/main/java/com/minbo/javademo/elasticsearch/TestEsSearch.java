package com.minbo.javademo.elasticsearch;

import static org.elasticsearch.index.query.QueryBuilders.moreLikeThisQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.io.IOException;
import java.net.InetAddress;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;  

public class TestEsSearch {
	
	public static void main(String[] args) throws IOException {
		Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
		Client client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(GlobelUtils.IP), 9300));

		long start=System.currentTimeMillis();   //获取开始时间
		System.out.println("开始获取数据");
		
		//指定字段进行搜索
		QueryBuilder qb1 = termQuery("price", 11);
		
		//组合查询搜索
//		QueryBuilder qb2 = boolQuery()
//                .must(termQuery("onSale", "false"))     
//                .must(termQuery("content", "test4"))     
//                .mustNot(termQuery("content", "test2"))     
//                .should(termQuery("content", "test3"))
                ;  
		
        //过滤查询搜索
//        QueryBuilder qb3 = QueryBuilders.rangeQuery("price")
//        		.from(12).to(80);
//        
//        int size = 10;
//		int from = 0;
//		//默认返回数据为10条
//		SearchResponse response = client.prepareSearch("productindex")
//		        .setQuery(qb3) 	// Query
//		        .setFrom(from).setSize(size).setExplain(true)     
//		        .execute()     
//		        .actionGet(); 
        
        MoreLikeThisQueryBuilder.Item[] items = {new MoreLikeThisQueryBuilder.Item("productindex", "productType", "6C29B927483115E5A4CC1D31D77869")};
        String[] fileds = {"title"};
        SearchResponse response = client.prepareSearch("productindex")
                .setTypes("productType")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(moreLikeThisQuery(fileds, null, items)
                        .minTermFreq(1)
                        .maxQueryTerms(15))
                .setSize(10).setFrom(0)
                .execute()
                .actionGet();
        
		SearchHits hits = response.getHits();  
		System.out.println("总符合记录数：count=" + hits.getTotalHits());
		for (int i = 0; i < 10; i++) {
//			System.out.println("----------------------");
//			System.out.println("Index="+hits.getAt(i).getIndex());
//			System.out.println("Type="+hits.getAt(i).getType());
			System.out.println("Id="+hits.getAt(i).getId());
//			System.out.println("Version="+hits.getAt(i).getVersion());
			System.out.println("============content============，i=" + (i+1));
			System.out.println("title="+hits.getAt(i).getSource().get("title"));
			System.out.println("description="+hits.getAt(i).getSource().get("description"));
			System.out.println("price="+hits.getAt(i).getSource().get("price"));
			System.out.println("onSale="+hits.getAt(i).getSource().get("onSale"));
			System.out.println("type="+hits.getAt(i).getSource().get("type"));
		    System.out.println("createDate="+hits.getAt(i).getSource().get("createDate"));
		}
		long end=System.currentTimeMillis(); //获取结束时间
		System.out.println("获得数据完成");
		System.out.println("程序运行时间： "+(end-start)+" ms");
		client.close();
	}
	
}
