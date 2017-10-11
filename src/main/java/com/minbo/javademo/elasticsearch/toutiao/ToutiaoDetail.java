package com.minbo.javademo.elasticsearch.toutiao;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

import com.minbo.javademo.utils.StrUtil;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

/**
 * 今日头条-文章详情抓取解析
 */
public class ToutiaoDetail implements PageProcessor {
	
	public static Client client;
    static {
		String ip = IpUtils.IP;
		Settings settings = Settings.builder().put("cluster.name", "elasticsearch").
				put("client.transport.sniff", true).build();
		try {
			client = new PreBuiltTransportClient(settings)
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), 9300));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	private Site site = Site.me().setCharset("UTF-8").setRetryTimes(2);//.setSleepTime(1000)
//			.setHttpProxy(new HttpHost("171.38.1.110", 8118));

    @Override
    public void process(Page page) {
    		String json = page.getRawText();
    		logger.info("详情数据解析.......>>>");
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
    			String id = ToutiaoDetail.getId(page.getRequest().getUrl());
    			logger.info("id=" + id);
    			try {
    				ProcessData pData = new ProcessData();
    				pData.detailPage(id, content, client);;
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
    
    public void processDetailInfo() throws UnknownHostException {
		long start=System.currentTimeMillis();   //获取开始时间
		logger.info("=============开始获取列表数据进行处理 start=================");
		
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
        int size = 3000;
		int from = 0;
//		//默认返回数据为10条
		SearchResponse response = null;
		try {
			response = client.prepareSearch("commons").setTypes("webpage")
			        .setQuery(qb1) 	// Query
			        .setFrom(from).setSize(size).setExplain(true)     
			        .execute()     
			        .actionGet();
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		SearchHits hits = response.getHits();  
		logger.info("总列表数据总数：count=" + hits.getTotalHits());
		String [] strArray = new String [(int) hits.getTotalHits()];
		for (int i = 0; i < hits.getTotalHits(); i++) {
			ToutiaoDetail tDetail = new ToutiaoDetail();
			String id = hits.getAt(i).getSource().get("id").toString();
			MyWebpage webPage = tDetail.getMainInfo(id);
			if(webPage != null && !StrUtil.null2Str(webPage.getContent()).equals("")) {
//				logger.info("已存在记录，跳过处理。id = " + webPage.getId());
				continue;
			}else {
				String url = "https://m.toutiao.com/i" + hits.getAt(i).getSource().get("id") + "/info/";
				strArray[i] = url;
				logger.info("---------------");
				logger.info("id=" + id);
				logger.info("webPage=" + webPage);
				logger.info("不存在详情数据，添加抓取内容详情url=" + strArray[i]);
			}
		}
		logger.info("================即将爬虫url详情内容的url列表==============");
		int count = 0;
		for (String string : strArray) {
			if(!StrUtil.null2Str(string).equals("")) {
				count++;
			}
		}
		String [] strArrayTmp = new String [count];
		int index = 0;
		for (String string : strArray) {
			if(!StrUtil.null2Str(string).equals("")) {
				strArrayTmp[index] = string;
				index++;
			}
		}
		logger.info("------------------count=" + count);
		for (String string : strArrayTmp) {
			logger.info(string);
		}
		
		if(strArrayTmp.length > 0) {
			Spider.create(new ToutiaoDetail()).addUrl(strArrayTmp).thread(1).run();
		}
		
		long end=System.currentTimeMillis(); //获取结束时间
		logger.info("获得数据完成");
		logger.info("程序运行时间： "+(end-start)+" ms");
    }
    
    public static String getId(String url) {
		String regEx="[^0-9]";   
		Pattern p = Pattern.compile(regEx);   
		Matcher m = p.matcher(url);   
		return m.replaceAll("").trim();
    }
    
    public MyWebpage getMainInfo(String id) throws UnknownHostException {
		//指定字段进行搜索
		QueryBuilder qb1 = termQuery("id", id);
	    int size = 1;
		int from = 0;
		SearchResponse response = null;
		try {
			response = client.prepareSearch("commons").setTypes("webpage")
			        .setQuery(qb1) 	// Query
			        .setFrom(from).setSize(size).setExplain(true)     
			        .execute()     
			        .actionGet();
		} catch (Exception e) {
			logger.error("搜索异常：id=" + id + ", msg=" + e.getMessage(), e);
		}
		MyWebpage webPage = new MyWebpage();
		if(response == null) {
			logger.info("------------------");
			logger.info("查询不到，新处理....id=" + id);
			return webPage;
		}
		SearchHits hits = response.getHits();
		if(hits.getTotalHits()<=0) {
			logger.info("------------------");
			logger.info("不存在，新处理....id=" + id);
			return webPage;
		}
		for (int i = 0; i < 1; i++) {
			
			webPage.setTitle((String) hits.getAt(i).getSource().get("title"));
			webPage.setUrl((String) hits.getAt(i).getSource().get("url"));
			webPage.setDomain((String) hits.getAt(i).getSource().get("domain"));
			webPage.setSpiderUUID((String) hits.getAt(i).getSource().get("spiderUUID"));
			webPage.setSpiderInfoId((String) hits.getAt(i).getSource().get("spiderInfoId"));
			webPage.setCategory((String) hits.getAt(i).getSource().get("category"));
			webPage.setGathertime(Long.valueOf(hits.getAt(i).getSource().get("gatherTime").toString()));
			webPage.setId((String) hits.getAt(i).getSource().get("id"));
			webPage.setItemId((String) hits.getAt(i).getSource().get("itemId"));
			webPage.setPublishTime(Long.valueOf(hits.getAt(i).getSource().get("publishTime").toString()));
			webPage.setDynamicFields((Map<String, Object>) hits.getAt(i).getSource().get("dynamicFields"));
			webPage.setProcessTime(Long.valueOf(hits.getAt(i).getSource().get("processTime").toString()));
			
			webPage.setContent((String) hits.getAt(i).getSource().get("content"));
			webPage.setKeywords((List<String>) hits.getAt(i).getSource().get("keywords"));
			webPage.setSummary((List<String>) hits.getAt(i).getSource().get("summary"));
			webPage.setNamedEntity((Map<String, Set<String>>) hits.getAt(i).getSource().get("namedEntity"));
		}
		return webPage;
	}
    
    public static void main(String[] args) {
		System.out.println("------------------");
		System.out.println("------------------");
		//2. 处理详情页面数据
		System.out.println();
		System.out.println("2. 处理详情页面数据...");
		try {
			ToutiaoDetail tDetail = new ToutiaoDetail();
			tDetail.processDetailInfo();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
