package com.minbo.javademo.elasticsearch.toutiao;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.minbo.javademo.utils.DateUtil;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

/**
 * 今日头条-文章列表数据抓取解析
 */
public class ToutiaoMain implements PageProcessor {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private Site site = Site.me().setCharset("UTF-8").setRetryTimes(2);
//			.setRetryTimes(3).setSleepTime(1000)
//			.setHttpProxy(new HttpHost("222.139.193.17", 8118));
	
	// 今日头条，爬虫新闻分类

//	private static String tag = "__all__"; // 推荐1
	// private static String tag = "video"; //视频2
	public static String TAG = "news_hot"; //热点3						###		特别关注	
	
//	 public static String TAG = "news_society"; //社会4					###
//	public static String TAG = "news_entertainment"; //娱乐5 				###
//	public static String TAG = "news_military"; //军事6					###
//	public static String TAG = "news_tech"; //科技7						###
//	public static String TAG = "news_car"; //汽车8						###
//	public static String TAG = "news_sports"; //体育9						###
	// private static String tag = "news_finance"; //财经10
	// private static String tag = "news_world"; //国际11
//	public static String TAG = "news_fashion"; //时尚12					###
//	public static String TAG = "news_game"; //游戏13						###
//	public static String TAG = "news_baby"; //育儿14						###
//	public static String TAG = "news_travel"; //旅游15					###
//	public static String TAG = "news_discovery"; //探索16					###		特别关注
	
//	public static String TAG = "news_regimen"; //养生17					###		特别关注
//	 public static String TAG = "news_history"; //历史18					###
//	 public static String TAG = "news_food"; //美食19						###
//	 public static String TAG = "news_story"; //故事20					###
	
	// private static String tag = "news_essay"; //美文21

	@Override
	public void process(Page page) {
		long start = System.currentTimeMillis();
		String json = page.getRawText();
		logger.info("======================================================");
		logger.info("==================解析列表数据 start=====================");
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
			String article_tag = new JsonPathSelector("$.tag").select(result);
			// 跳过广告
			if (!article_tag.equals("ad")) {
				String id = new JsonPathSelector("$.item_id").select(result);	
				String item_id = new JsonPathSelector("$.item_id").select(result);
				long publish_time = Long.valueOf(new JsonPathSelector("$.publish_time").select(result));
				String title = new JsonPathSelector("$.title").select(result);
				String source = new JsonPathSelector("$.source").select(result);
				String article_url = new JsonPathSelector("$.article_url").select(result);
				
				logger.info("article_url=" + article_url);
				List<String> image_list = new JsonPathSelector("$.image_list").selectList(result);
				StringBuffer imgsBuf = new StringBuffer();
				imgsBuf.append("[");  
				String imgs = "";
				for (int j = 0; j < image_list.size(); j++) {
					String result2 = image_list.get(j);
					String url = new JsonPathSelector("$.url").select(result2);
					imgs += url + ",";
				}
				if(!imgs.equals("")) {
					imgs = imgs.substring(0, imgs.length()-1);
				}
				imgsBuf.append(imgs);
				imgsBuf.append("]");
				
				Map<String, Object> dynamicFields = new HashMap<>();
				dynamicFields.put("imgs", imgsBuf.toString());
				long cost = System.currentTimeMillis() - start;
				
				Map<String, String> spiderMap = getSpirderInfo(article_tag);
				
				MyWebpage webPage = new MyWebpage();
				webPage.setTitle(title);
				webPage.setUrl(article_url);
				webPage.setDomain(TAG.equals("news_hot")?"news_hot":article_tag);		//如果为热点数据，则单独热点为一个类型
				webPage.setSpiderUUID(spiderMap.get("spiderUUID"));
				webPage.setSpiderInfoId(spiderMap.get("spiderInfoId"));
				webPage.setCategory(source);
				webPage.setGathertime(DateUtil.dateToStamp(DateUtil.getCurrentLongDateTime())*1000);
				webPage.setId(id);
				webPage.setItemId(item_id);
				webPage.setPublishTime(publish_time*1000);
				webPage.setDynamicFields(dynamicFields);
				webPage.setProcessTime(cost);
				
				webPage.setContent("");
				webPage.setKeywords(null);
				webPage.setSummary(null);
				webPage.setNamedEntity(null);
				
				logger.info("webPage=" + webPage.toString());
				
				logger.info("==================解析列表数据 end=====================");
				logger.info("======================================================");
				try {
					logger.info("===================================================");
					logger.info("==================开始列表数据入库====================");
					ProcessData pData = new ProcessData();
					pData.mainPage(webPage);
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
				"UM_distinctid=15d4e6b236b8c1-0212d4d6465428-30677808-13c680-15d4e6b236ccc1; uuid=\"w:6eeae48e765542f8816b14bd1a8803f2\"; sso_login_status=0; tt_webid=6464335891330024973; __utma=252651093.1935118179.1505121696.1505198559.1505198559.1; __utmz=252651093.1505198559.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); tt_track_id=b4300ddb07cd24b2e473b6ebf33f8fbc; tt_webid=6464861854974871054; W2atIF=1; csrftoken=490b809baf80308e71e827d7f578d2fb; __tasessionId=isfmcv2uq1507529285740; _ga=GA1.2.1935118179.1505121696; _gid=GA1.2.2066579823.1507529277; _ba=BA0.2-20170912-51225-LXO6Nly1dtclVPwkr0pO; bottom-banner-hide-status=true");
		site.addHeader("Host", "m.toutiao.com");
		site.addHeader("Referer", "https://m.toutiao.com/");
		site.addHeader("User-Agent",
				"Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");
		return site;
	}

	public static long getStamp() {
		return System.currentTimeMillis() / 1000;
	}
	
	/**
	 * 将时间戳转换为日期
	 */
	public static Date dateToStamp(long timeStamp) {
		Timestamp t = new Timestamp(getStamp()*1000);  
        Date d = new Date(t.getTime());
        return d;
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
		if(tagTmp.equals("news_hot")) {					//热点
			map.put("spiderUUID", "aaaafc39-6ff7-4a95-8024-b85d99350000");	//自定义生成的
			map.put("spiderInfoId", "AV8LRW-d8-owkfOcKCN8");
			
		}else if(tagTmp.equals("news_society")) {		//社会
			map.put("spiderUUID", "aaaafc39-6ff7-4a95-8024-b85d99350001");	//自定义生成的
			map.put("spiderInfoId", "AV7_qd95b6Sm4fkv7DKG");
			
		}else if(tagTmp.equals("news_entertainment")) {	//娱乐
			map.put("spiderUUID", "aaaafc39-6ff7-4a95-8024-b85d99350002");	//自定义生成的
			map.put("spiderInfoId", "AV8LC59X8-owkfOcKCNb");
			
		}else if(tagTmp.equals("news_entertainment")) {	//军事
			map.put("spiderUUID", "aaaafc39-6ff7-4a95-8024-b85d99350003");	//自定义生成的
			map.put("spiderInfoId", "AV8LGD3k8-owkfOcKCNf");
			
		}else if(tagTmp.equals("news_tech")) {			//科技
			map.put("spiderUUID", "aaaafc39-6ff7-4a95-8024-b85d99350004");	//自定义生成的
			map.put("spiderInfoId", "AV8LJoWR8-owkfOcKCNo");
			
		}else if(tagTmp.equals("news_car")) {			//汽车
			map.put("spiderUUID", "aaaafc39-6ff7-4a95-8024-b85d99350005");	//自定义生成的
			map.put("spiderInfoId", "AV8LKgls8-owkfOcKCNq");
			
		}else if(tagTmp.equals("news_sports")) {			//体育
			map.put("spiderUUID", "aaaafc39-6ff7-4a95-8024-b85d99350006");	//自定义生成的
			map.put("spiderInfoId", "AV8LOwe38-owkfOcKCNz");
			
		}else if(tagTmp.equals("news_fashion")) {		//时尚
			map.put("spiderUUID", "aaaafc39-6ff7-4a95-8024-b85d99350007");	//自定义生成的
			map.put("spiderInfoId", "AV8LPOL38-owkfOcKCN1");
			
		}else if(tagTmp.equals("news_game")) {			//游戏
			map.put("spiderUUID", "aaaafc39-6ff7-4a95-8024-b85d99350008");	//自定义生成的
			map.put("spiderInfoId", "AV8LQQWc8-owkfOcKCN6");
			
		}else if(tagTmp.equals("news_baby")) {			//育儿
			map.put("spiderUUID", "aaaafc39-6ff7-4a95-8024-b85d99350009");	//自定义生成的
			map.put("spiderInfoId", "AV8LQmrd8-owkfOcKCN7");
			
		}else if(tagTmp.equals("news_travel")) {			//旅游
			map.put("spiderUUID", "aaaafc39-6ff7-4a95-8024-b85d99350010");	//自定义生成的
			map.put("spiderInfoId", "AV8LTYr78-owkfOcKCOC");
			
		}else if(tagTmp.equals("news_discovery")) {		//探索
			map.put("spiderUUID", "aaaafc39-6ff7-4a95-8024-b85d99350011");	//自定义生成的
			map.put("spiderInfoId", "AV8LTwe-8-owkfOcKCOD");
			
		}else if(tagTmp.equals("news_regimen")) {		//养生
			map.put("spiderUUID", "aaaafc39-6ff7-4a95-8024-b85d99350012");	//自定义生成的
			map.put("spiderInfoId", "AV8LUB3P8-owkfOcKCOE");
			
		}else if(tagTmp.equals("news_history")) {		//历史
			map.put("spiderUUID", "aaaafc39-6ff7-4a95-8024-b85d99350013");	//自定义生成的
			map.put("spiderInfoId", "AV8LUVHg8-owkfOcKCOF");
			
		}else if(tagTmp.equals("news_food")) {			//美食
			map.put("spiderUUID", "aaaafc39-6ff7-4a95-8024-b85d99350014");	//自定义生成的
			map.put("spiderInfoId", "AV8LUk4T8-owkfOcKCOG");
			
		}else if(tagTmp.equals("news_story")) {			//故事
			map.put("spiderUUID", "aaaafc39-6ff7-4a95-8024-b85d99350015");	//自定义生成的
			map.put("spiderInfoId", "AV8LU0J38-owkfOcKCOH");
			
		}
		
		return map;
	}
	
	public static void main(String[] args) {
		long start=System.currentTimeMillis();   //获取开始时间
		
		int flag = 0;
		while(true) {
			//跑两次
			if(flag>1) {	
				System.out.println("跑批次数已打上限，终止运行");
				break;
			}
			flag++;
			//1. 处理列表数据
			System.out.println();
			System.out.println("tag=" + TAG + "，1. 处理列表数据...");
			int count = 10;
			String [] strArray = new String [count];
			for (int i = 0; i < count; i++) {
				Date date = DateUtils.addMinutes(dateToStamp(System.currentTimeMillis() / 1000), new Random().nextInt(200) * -2 - 1);
				long stamp = date.getTime()/1000;
				String url = "https://m.toutiao.com/list/?tag=" + TAG + "&ac=wap&count=20"
						+ "&format=json_raw&as=A155C93D0E5B678&cp=59DE5B26A7E8EE1&max_behot_time=" + stamp;
				strArray[i] = url;
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
//			strArray[0] = "https://m.toutiao.com/list/?tag=news_society&ac=wap&count=20&format=json_raw&as=A115B9AD6B2127E&cp=59DB6142679E9E1&min_behot_time=0";
//			strArray[1] = "https://m.toutiao.com/list/?tag=news_society&ac=wap&count=20&format=json_raw&as=A1E539BD7B612EC&cp=59DBE172DE5C2E1&max_behot_time=1507528455";
//			strArray[2] = "https://m.toutiao.com/list/?tag=news_society&ac=wap&count=20&format=json_raw&as=A16509DD8B81328&cp=59DBE133D258EE1&max_behot_time=1507527729";
//			strArray[3] = "https://m.toutiao.com/list/?tag=news_society&ac=wap&count=20&format=json_raw&as=A1A5C9DD3B91394&cp=59DBF153E9D42E1&max_behot_time=1507526829";
//			strArray[4] = "https://m.toutiao.com/list/?tag=news_society&ac=wap&count=20&format=json_raw&as=A1E529AD4B613D5&cp=59DBB1C3AD65BE1&max_behot_time=1507525928";
//			strArray[5] = "https://m.toutiao.com/list/?tag=news_society&ac=wap&count=20&format=json_raw&as=A1B509ADFB914D0&cp=59DB21C40DF05E1&max_behot_time=1507521128";
//			strArray[6] = "https://m.toutiao.com/list/?tag=news_society&ac=wap&count=20&format=json_raw&as=A1D5F9FD5B416EE&cp=59DB91861EFEFE1&max_behot_time=1507518728";
//			strArray[7] = "https://m.toutiao.com/list/?tag=news_society&ac=wap&count=20&format=json_raw&as=A185C9BDCB01765&cp=59DB611726558E1&max_behot_time=1507516328";
			
			System.out.println("================下载地址LIST_URL：列表==============");
			System.out.println("------------------");
			for (String string : strArray) {
				System.out.println(string);
			}
			System.out.println("------------------");
			System.out.println("开始爬虫网站....");
			Spider.create(new ToutiaoMain()).addUrl(strArray).thread(5).run();
			
			//休眠三秒
			System.out.println("休眠三秒");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("------------------");
			System.out.println("------------------");
			System.out.println("------------------");
			//2. 处理详情页面数据
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println("2. 处理详情页面数据...");
			try {
				ToutiaoDetail tDetail = new ToutiaoDetail();
				tDetail.processDetailInfo(TAG);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			
			try {
				System.out.println("----------");
				System.out.println("----------");
				System.out.println("休息5秒种.");
				System.out.println("----------");
				System.out.println("----------");
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		long end=System.currentTimeMillis(); //获取结束时间
		System.out.println("该批次运行总耗费时间： "+(end-start)+" ms");
	}
}
