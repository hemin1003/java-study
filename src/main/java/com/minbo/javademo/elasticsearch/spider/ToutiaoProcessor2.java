package com.minbo.javademo.elasticsearch.spider;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.jce.Random;
import com.minbo.javademo.utils.DateUtil;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;
import us.codecraft.webmagic.thread.CountableThreadPool;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 今日头条-文章列表数据抓取解析
 */
public class ToutiaoProcessor2 implements PageProcessor {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private Site site = Site.me();
//			.setRetryTimes(3).setSleepTime(1000)
//			.setHttpProxy(new HttpHost("222.139.193.17", 8118));
	
	// 今日头条，爬虫新闻分类

//	private static String tag = "__all__"; // 推荐1
	// private static String tag = "video"; //视频2
	// private static String tag = "news_hot"; //热点3
	 private static String tag = "news_society"; //社会4
	// private static String tag = "news_entertainment"; //娱乐5 
	// private static String tag = "news_military"; //军事6
	// private static String tag = "news_tech"; //科技7
	// private static String tag = "news_car"; //汽车8
	// private static String tag = "news_sports"; //体育9
	// private static String tag = "news_finance"; //财经10
	// private static String tag = "news_world"; //国际11
	// private static String tag = "news_fashion"; //时尚12
	// private static String tag = "news_game"; //游戏13
	// private static String tag = "news_baby"; //育儿14
	// private static String tag = "news_travel"; //旅游15
	// private static String tag = "news_discovery"; //探索16
	// private static String tag = "news_regimen"; //养生17
	// private static String tag = "news_history"; //历史18
	// private static String tag = "news_food"; //美食19
	// private static String tag = "news_story"; //故事20
	// private static String tag = "news_essay"; //美文21

	@Override
	public void process(Page page) {
		long start = System.currentTimeMillis();
		String json = page.getRawText();
		logger.info("第一层解析...");
		logger.info("return_count=" + new JsonPathSelector("$.return_count").select(json));
		logger.info("has_more=" + new JsonPathSelector("$.has_more").select(json));
		logger.info("page_id=" + new JsonPathSelector("$.page_id").select(json));
		List<String> data = new JsonPathSelector("$.data").selectList(json);
		logger.info("data.size()=" + data.size());
		logger.info("============");
		logger.info("第二层解析...");
		for (int i = 0; i < data.size(); i++) {
			logger.info("===========================");
			logger.info("第" + (i + 1) + "条：" + data.get(i));
			String result = data.get(i);
			String article_type = new JsonPathSelector("$.article_type").select(result);
			String article_tag = new JsonPathSelector("$.tag").select(result);
			// 跳过广告，暂时只抓取“社会”分类
			if (!article_type.equals("1") && article_tag.equals(tag)) {
				String group_id = new JsonPathSelector("$.group_id").select(result);
				logger.info("group_id=" + group_id);
				long publish_time = Long.valueOf(new JsonPathSelector("$.publish_time").select(result));
				logger.info("publish_time=" + publish_time);
				String title = new JsonPathSelector("$.title").select(result);
				logger.info("title=" + title);
				String source = new JsonPathSelector("$.source").select(result);
				logger.info("source=" + source);
				logger.info("comment_count=" + new JsonPathSelector("$.comment_count").select(result));
				logger.info("tag=" + article_tag);
				String article_url = new JsonPathSelector("$.article_url").select(result);
				logger.info("article_url=" + article_url);
				
				logger.info("-------------------------");
				List<String> image_list = new JsonPathSelector("$.image_list").selectList(result);
				logger.info("image_list=" + image_list.size());
				StringBuffer imgsBuf = new StringBuffer();
				imgsBuf.append("[");  
				String imgs = "";
				for (int j = 0; j < image_list.size(); j++) {
					String result2 = image_list.get(j);
					String url = new JsonPathSelector("$.url").select(result2);
					logger.info("url" + (j+1) + "=" + url);
					imgs += url + ",";
				}
				if(!imgs.equals("")) {
					imgs = imgs.substring(0, imgs.length()-1);
				}
				imgsBuf.append(imgs);
				imgsBuf.append("]");
				logger.info("imgsBuf=" + imgsBuf.toString());
				
				Map<String, Object> dynamicFields = new HashMap<>();
				dynamicFields.put("imgs", imgsBuf.toString());
				long cost = System.currentTimeMillis() - start;
				
				Map<String, String> spiderMap = getSpirderInfo(article_tag);
				
				Webpage webPage = new Webpage();
				webPage.setTitle(title);
				webPage.setUrl(article_url);
				webPage.setDomain(article_tag);
				webPage.setSpiderUUID(spiderMap.get("spiderUUID"));
				webPage.setSpiderInfoId(spiderMap.get("spiderInfoId"));
				webPage.setCategory(source);
				webPage.setGathertime(DateUtil.dateToStamp(DateUtil.getCurrentLongDateTime())*1000);
				webPage.setId(group_id);
				webPage.setPublishTime(publish_time*1000);
				webPage.setDynamicFields(dynamicFields);
				webPage.setProcessTime(cost);
				
				webPage.setContent("");
				webPage.setKeywords(null);
				webPage.setSummary(null);
				webPage.setNamedEntity(null);
				
				logger.info("webPage=" + webPage.toString());
				
				try {
					logger.info("==================开始爬虫列表入库====================");
					TestEsAddData2.mainPage(webPage);
				} catch (IOException | ParseException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public Site getSite() {
		site.addHeader("Accept", "*/*");
		site.addHeader("Accept-Encoding", "gzip, deflate, br");
		site.addHeader("Accept-Language", "en-US,en;q=0.8,zh-CN;q=0.6,zh;q=0.4,zh-TW;q=0.2");
		site.addHeader("Connection", "keep-alive");
		site.addHeader("Cookie",
				"UM_distinctid=15d4e6b236b8c1-0212d4d6465428-30677808-13c680-15d4e6b236ccc1; uuid=\"w:6eeae48e765542f8816b14bd1a8803f2\"; sso_login_status=0; utm_source=toutiao; W2atIF=1; bottom-banner-hide-status=true; tt_webid=6464335891330024973; __utma=252651093.1935118179.1505121696.1505198559.1505198559.1; __utmc=252651093; __utmz=252651093.1505198559.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); tt_track_id=b4300ddb07cd24b2e473b6ebf33f8fbc; csrftoken=490b809baf80308e71e827d7f578d2fb; _ba=BA0.2-20170912-51225-LXO6Nly1dtclVPwkr0pO; _ga=GA1.2.1935118179.1505121696; _gid=GA1.2.1882035805.1505215622; tt_webid=6464861854974871054");
		site.addHeader("Host", "m.toutiao.com");
		site.addHeader("Referer", "https://m.toutiao.com/");
		site.addHeader("User-Agent",
				"Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Mobile Safari/537.36");
		return site;
	}

	public static void main(String[] args) {
		//1. 处理列表数据
		System.out.println();
		System.out.println("1. 处理列表数据...");
		int count = 1;
		String [] strArray = new String [count];
		for (int i = 0; i < count; i++) {
			String url = "https://m.toutiao.com/list/?tag=" + tag + "&ac=wap&count=" + new java.util.Random().nextInt(20)
					+ "&format=json_raw&as=A105392BB74C226&cp=59B78CE2C2868E1&max_behot_time=" + getStamp();
			strArray[i] = url;
			try {
				Thread.sleep(80);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("================下载地址LIST_URL：列表==============");
		System.out.println("------------------");
		for (String string : strArray) {
			System.out.println(string);
		}
		System.out.println("------------------");
		System.out.println("开始爬虫网站....");
		Spider.create(new ToutiaoProcessor2()).addUrl(strArray).thread(5).run();
		
		//休眠三秒
		System.out.println("休眠三秒");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//2. 处理详情页面数据
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("2. 处理详情页面数据...");
		try {
			ToutiaoProcessor3.processDetailInfo();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public static long getStamp() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 将时间戳转换为时间
	 */
	public static String stampToDate(long timeStamp) {
		String result = null;
		Date date = new Date(timeStamp * 1000);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		result = sd.format(date);
		return result;
	}
	
	/**
	 * 根据tag获取爬虫模板信息
	 * @param tagTmp
	 * @return
	 */
	public static Map<String, String> getSpirderInfo(String tagTmp) {
		Map<String, String> map = new HashMap<>();
		if(tagTmp.equals("news_society")) {
			map.put("spiderUUID", "aaaafc39-6ff7-4a95-8024-b85d99350001");
			map.put("spiderInfoId", "AV6ZI-TEE7C-scy0W2qu");
		}
		return map;
	}
	
}
