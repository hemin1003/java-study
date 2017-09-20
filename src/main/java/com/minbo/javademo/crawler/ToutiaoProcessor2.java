package com.minbo.javademo.crawler;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;
import us.codecraft.webmagic.thread.CountableThreadPool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 今日头条-文章列表数据抓取解析
 */
public class ToutiaoProcessor2 implements PageProcessor {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000)
			.setHttpProxy(new HttpHost("171.38.1.110", 8118));
	
	// 今日头条，爬虫新闻分类

	private static String tag = "__all__"; // 推荐1
	// private static String tag = "video"; //视频2
	// private static String tag = "news_hot"; //热点3
	// private static String tag = "news_society"; //社会4
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

	private static int count = 20;
	private long max_behot_time = getStamp();
	private String LIST_URL = "https://m.toutiao.com/list/?tag=" + tag + "&ac=wap&count=" + count
			+ "&format=json_raw&as=A105392BB74C226&cp=59B78CE2C2868E1&max_behot_time=" + max_behot_time;

	@Override
	public void process(Page page) {
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
			logger.info("---------------");
			logger.info("第" + (i + 1) + "条：" + data.get(i));
			String result = data.get(i);
			String article_type = new JsonPathSelector("$.article_type").select(result);
			// 跳过广告
			if (!article_type.equals("1")) {
				logger.info("title=" + new JsonPathSelector("$.title").select(result));
				logger.info("source=" + new JsonPathSelector("$.source").select(result));
				logger.info("comment_count=" + new JsonPathSelector("$.comment_count").select(result));
				logger.info("tag=" + new JsonPathSelector("$.tag").select(result));
				logger.info("article_url=" + new JsonPathSelector("$.article_url").select(result));
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
		for (int i = 0; i < 3; i++) {
			ToutiaoProcessor2 tt = new ToutiaoProcessor2();
			Spider.create(new ToutiaoProcessor2()).addUrl(tt.LIST_URL).thread(1).run();
		}
		
	}

	public long getStamp() {
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
}
