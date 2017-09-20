package com.minbo.javademo.elasticsearch.spider;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.minbo.javademo.elasticsearch.GlobelUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

/**
 * 今日头条-文章详情抓取解析
 */
public class ToutiaoProcessor3 implements PageProcessor {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	private Site site = Site.me();//.setRetryTimes(3).setSleepTime(1000)
//			.setHttpProxy(new HttpHost("171.38.1.110", 8118));

    @Override
    public void process(Page page) {
    		String json = page.getRawText();
    		logger.info("爬虫后数据解析.......>>>");
        	logger.info("第一层解析...");
    		logger.info("success=" + new JsonPathSelector("$.success").select(json));
    		List<String> data = new JsonPathSelector("$.data").selectList(json);
    		logger.info("data.size()=" + data.size());
    		logger.info("============");
    		logger.info("第二层解析...");
    		for (int i = 0; i < data.size(); i++) {
    			logger.info("---------------");
    			logger.info("第" + (i+1) + "条：" + data.get(i));
    			String result = data.get(i);
    			String url = new JsonPathSelector("$.url").select(result);
    			logger.info("title=" + new JsonPathSelector("$.title").select(result));
    			logger.info("url=" + url);
    			String content = new JsonPathSelector("$.content").select(result);
    			logger.info("content=" + content);
    			String group_id = ToutiaoProcessor3.getId(url);
    			logger.info("id=" + group_id);
    			
    			try {
				TestEsAddData2.detailPage(group_id, content, client);;
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
    		}
    }

    @Override   
    public Site getSite() {
    		site.addHeader("Accept", "*/*");
    		site.addHeader("Accept-Encoding", "gzip, deflate, br");
    		site.addHeader("Accept-Language", "en-US,en;q=0.8,zh-CN;q=0.6,zh;q=0.4,zh-TW;q=0.2");
    		site.addHeader("Connection", "keep-alive");
    		site.addHeader("Cookie", "UM_distinctid=15d4e6b236b8c1-0212d4d6465428-30677808-13c680-15d4e6b236ccc1; uuid=\"w:6eeae48e765542f8816b14bd1a8803f2\"; sso_login_status=0; utm_source=toutiao; W2atIF=1; bottom-banner-hide-status=true; tt_webid=6464335891330024973; __utma=252651093.1935118179.1505121696.1505198559.1505198559.1; __utmc=252651093; __utmz=252651093.1505198559.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); tt_track_id=b4300ddb07cd24b2e473b6ebf33f8fbc; csrftoken=490b809baf80308e71e827d7f578d2fb; _ba=BA0.2-20170912-51225-LXO6Nly1dtclVPwkr0pO; _ga=GA1.2.1935118179.1505121696; _gid=GA1.2.1882035805.1505215622; tt_webid=6464861854974871054");
    		site.addHeader("Host", "m.toutiao.com");
    		site.addHeader("Referer", "https://m.toutiao.com/");
    		site.addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Mobile Safari/537.36");
        return site;
    }
    
    public static Client client;
    static {
		String ip = GlobelUtils.IP;
		Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
		try {
			client = new PreBuiltTransportClient(settings)
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), 9300));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

    public static void processDetailInfo() throws UnknownHostException {
		long start=System.currentTimeMillis();   //获取开始时间
		System.out.println("======================");
		System.out.println("开始获取列表数据进行处理");
		
		//指定字段进行搜索
		QueryBuilder qb1 = termQuery("domain", "news_society");
		
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
        int size = 1000;
		int from = 0;
//		//默认返回数据为10条
		SearchResponse response = client.prepareSearch("commons").setTypes("webpage")
		        .setQuery(qb1) 	// Query
		        .setFrom(from).setSize(size).setExplain(true)     
		        .execute()     
		        .actionGet();
        
		SearchHits hits = response.getHits();  
		System.out.println("总符合记录数：count=" + hits.getTotalHits());
		String [] strArray = new String [(int) hits.getTotalHits()];
		for (int i = 0; i < hits.getTotalHits(); i++) {
			System.out.println("===================================================");
			System.out.println("Index="+hits.getAt(i).getIndex());
			System.out.println("Type="+hits.getAt(i).getType());
			System.out.println("Id="+hits.getAt(i).getId());
			System.out.println("Version="+hits.getAt(i).getVersion());
			System.out.println("----------------content----------------，i=" + (i+1));
			Webpage webPageTmp = ToutiaoProcessor3.getMainInfo(hits.getAt(i).getSource().get("id").toString());
			if(webPageTmp != null &&  null != webPageTmp.getContent()) {
				System.out.println("已存在记录，跳过处理。webPageTmp = " + webPageTmp.toString());
				continue;
			}
			System.out.println("id="+hits.getAt(i).getSource().get("id"));
			System.out.println("title="+hits.getAt(i).getSource().get("title"));
			System.out.println("url="+hits.getAt(i).getSource().get("url"));
			System.out.println("domain="+hits.getAt(i).getSource().get("domain"));
			System.out.println("category="+hits.getAt(i).getSource().get("category"));
			System.out.println("publishTime="+hits.getAt(i).getSource().get("publishTime"));
			
			String url = "https://m.toutiao.com/i" + hits.getAt(i).getSource().get("id") + "/info/";
			System.out.println("----------------download url---------------->>>" + url);
			strArray[i] = url;
		}
		System.out.println("================即将爬虫url列表==============");
		System.out.println("------------------");
		for (String string : strArray) {
			System.out.println(string);
		}
		System.out.println("------------------");
		
		Spider.create(new ToutiaoProcessor3()).addUrl(strArray).thread(1).run();
		
		long end=System.currentTimeMillis(); //获取结束时间
		System.out.println("获得数据完成");
		System.out.println("程序运行时间： "+(end-start)+" ms");
		client.close();
    }
    
    public static String getId(String url) {
//    		String url="https://m.toutiao.com/i6465174828285428238/info/";
		String regEx="[^0-9]";   
		Pattern p = Pattern.compile(regEx);   
		Matcher m = p.matcher(url);   
		return m.replaceAll("").trim();
    }
    
    public static Webpage getMainInfo(String id) throws UnknownHostException {
		Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
		Client client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(GlobelUtils.IP), 9300));
	
		long start=System.currentTimeMillis();   //获取开始时间
		System.out.println("查询数据是否存在....");
		
		//指定字段进行搜索
		QueryBuilder qb1 = termQuery("id", id);
	    int size = 1;
		int from = 0;
		SearchResponse response = client.prepareSearch("commons").setTypes("webpage")
		        .setQuery(qb1) 	// Query
		        .setFrom(from).setSize(size).setExplain(true)     
		        .execute()     
		        .actionGet();
		SearchHits hits = response.getHits();
		Webpage webPage = new Webpage();
		if(hits.getTotalHits()<=0) {
			System.out.println("不存在，新处理....");
			return webPage;
		}
		for (int i = 0; i < 1; i++) {
			System.out.println("===================================================");
			System.out.println("Index="+hits.getAt(i).getIndex());
			System.out.println("Type="+hits.getAt(i).getType());
			System.out.println("Id="+hits.getAt(i).getId());
			System.out.println("Version="+hits.getAt(i).getVersion());
			
			System.out.println("----------------content----------------，i=" + (i+1));
			System.out.println("title="+hits.getAt(i).getSource().get("title"));
			System.out.println("url="+hits.getAt(i).getSource().get("url"));
			System.out.println("domain="+hits.getAt(i).getSource().get("domain"));
			System.out.println("spiderUUID="+hits.getAt(i).getSource().get("spiderUUID"));
			System.out.println("spiderInfoId="+hits.getAt(i).getSource().get("spiderInfoId"));
			System.out.println("category="+hits.getAt(i).getSource().get("category"));
			System.out.println("gatherTime="+hits.getAt(i).getSource().get("gatherTime"));
			System.out.println("id="+hits.getAt(i).getSource().get("id"));
			System.out.println("publishTime="+hits.getAt(i).getSource().get("publishTime"));
			System.out.println("dynamicFields="+hits.getAt(i).getSource().get("dynamicFields"));
			System.out.println("processTime="+hits.getAt(i).getSource().get("processTime"));
			
//			System.out.println("content="+hits.getAt(i).getSource().get("content"));
//			System.out.println("keywords="+hits.getAt(i).getSource().get("keywords"));
//			System.out.println("summary="+hits.getAt(i).getSource().get("summary"));
//			System.out.println("namedEntity="+hits.getAt(i).getSource().get("namedEntity"));
			
			webPage.setTitle((String) hits.getAt(i).getSource().get("title"));
			webPage.setUrl((String) hits.getAt(i).getSource().get("url"));
			webPage.setDomain((String) hits.getAt(i).getSource().get("domain"));
			webPage.setSpiderUUID((String) hits.getAt(i).getSource().get("spiderUUID"));
			webPage.setSpiderInfoId((String) hits.getAt(i).getSource().get("spiderInfoId"));
			webPage.setCategory((String) hits.getAt(i).getSource().get("category"));
			webPage.setGathertime(Long.valueOf(hits.getAt(i).getSource().get("gatherTime").toString()));
			webPage.setId((String) hits.getAt(i).getSource().get("id"));
			webPage.setPublishTime(Long.valueOf(hits.getAt(i).getSource().get("publishTime").toString()));
			webPage.setDynamicFields((Map<String, Object>) hits.getAt(i).getSource().get("dynamicFields"));
			webPage.setProcessTime(Long.valueOf(hits.getAt(i).getSource().get("processTime").toString()));
			
		}
		System.out.println("webPage=" + webPage.toString());
		System.out.println("存在，先获得明细，再添加详情内容....");
		System.out.println("------------------");
		long end=System.currentTimeMillis(); //获取结束时间
		System.out.println("获得数据完成");
		System.out.println("程序运行时间： "+(end-start)+" ms");
		return webPage;
	}
    
}
