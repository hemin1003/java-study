package com.minbo.javademo.elasticsearch.toutiao;

/**
 * 今日头条，爬虫新闻分类tag
 * 
 * @author Minbo
 */
public class Tags {

//	 public static String TAG = "news_hot"; //热点3 ###
	// public static String TAG = "news_society"; //社会4 ###
	// public static String TAG = "news_entertainment"; //娱乐5 ###
	// public static String TAG = "news_military"; //军事6 ###
	// public static String TAG = "news_tech"; //科技7 ###
	// public static String TAG = "news_car"; //汽车8 ###

	// public static String TAG = "news_sports"; //体育9 ###
//	 public static String TAG = "news_finance"; //财经10 ###
	// public static String TAG = "news_fashion"; //时尚12 ###
	// public static String TAG = "news_game"; //游戏13 ###
	// public static String TAG = "news_baby"; //育儿14 ###
	// public static String TAG = "news_travel"; //旅游15 ###
//	public static String TAG = "news_discovery"; // 探索16 ###

	// public static String TAG = "news_regimen"; //养生17 ###
	// public static String TAG = "news_history"; //历史18 ###
	 public static String TAG = "news_food"; //美食19 ###
	// public static String TAG = "news_story"; //故事20 ###

	public static String[] tagList = new String[] { "news_hot", "news_society", "news_entertainment", "news_military",
			"news_tech", "news_car", "news_sports", "news_finance", "news_fashion", "news_game", "news_baby",
			"news_travel", "news_discovery", "news_regimen", "news_history", "news_food", "news_story" };
	
	public static String[] acList = new String[] {"A115B9AD6B2127E", "A1E539BD7B612EC", "A16509DD8B81328", "A1A5C9DD3B91394", 
			"A1E529AD4B613D5", "A1B509ADFB914D0", "A1D5F9FD5B416EE", "A185C9BDCB01765"};
	
	public static String[] cpList = new String[] {"59DB6142679E9E1", "59DBE172DE5C2E1", "59DBE133D258EE1", "59DBF153E9D42E1", 
			"59DBB1C3AD65BE1", "59DB21C40DF05E1", "59DB91861EFEFE1", "59DB611726558E1"};
	

//	strArray[0] = "https://m.toutiao.com/list/?tag=news_society&ac=wap&count=20&format=json_raw&as=A115B9AD6B2127E&cp=59DB6142679E9E1&min_behot_time=0";
//	strArray[1] = "https://m.toutiao.com/list/?tag=news_society&ac=wap&count=20&format=json_raw&as=A1E539BD7B612EC&cp=59DBE172DE5C2E1&max_behot_time=1507528455";
//	strArray[2] = "https://m.toutiao.com/list/?tag=news_society&ac=wap&count=20&format=json_raw&as=A16509DD8B81328&cp=59DBE133D258EE1&max_behot_time=1507527729";
//	strArray[3] = "https://m.toutiao.com/list/?tag=news_society&ac=wap&count=20&format=json_raw&as=A1A5C9DD3B91394&cp=59DBF153E9D42E1&max_behot_time=1507526829";
//	strArray[4] = "https://m.toutiao.com/list/?tag=news_society&ac=wap&count=20&format=json_raw&as=A1E529AD4B613D5&cp=59DBB1C3AD65BE1&max_behot_time=1507525928";
//	strArray[5] = "https://m.toutiao.com/list/?tag=news_society&ac=wap&count=20&format=json_raw&as=A1B509ADFB914D0&cp=59DB21C40DF05E1&max_behot_time=1507521128";
//	strArray[6] = "https://m.toutiao.com/list/?tag=news_society&ac=wap&count=20&format=json_raw&as=A1D5F9FD5B416EE&cp=59DB91861EFEFE1&max_behot_time=1507518728";
//	strArray[7] = "https://m.toutiao.com/list/?tag=news_society&ac=wap&count=20&format=json_raw&as=A185C9BDCB01765&cp=59DB611726558E1&max_behot_time=1507516328";

}
