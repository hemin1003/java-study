package com.minbo.javademo.elasticsearch.spider;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.minbo.javademo.elasticsearch.GlobelUtils;
import com.minbo.javademo.elasticsearch.nlp.HANLPExtractor;
import com.minbo.javademo.elasticsearch.nlp.NLPExtractor;
import com.minbo.javademo.utils.DateUtil;
import com.minbo.javademo.utils.UUID;
import com.sun.tools.javah.resources.l10n;

public class TestEsAddData2 {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

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

	private static final String HTML_PREFIX = "<!DOCTYPE html>\n" + "<html lang=\"en\">\n" + "    <head>\n"
			+ "        <meta name=\"apple-mobile-web-app-capable\" content=\"yes\"></meta>\n"
			+ "        <meta http-equiv=\"Cache-Control\" name=\"no-store\"></meta>\n"
			+ "        <meta content=\"telephone=no\" name=\"format-detection\"></meta>\n"
			+ "        <meta content=\"email=no\" name=\"format-detection\"></meta>\n"
			+ "        <meta charset=\"utf-8\"></meta>\n"
			+ "        <meta name=\"viewport\" content=\"target-densitydpi=device-dpi,width=decive-width,initial-scale=1,maximum-scale=1\"></meta>\n"
			+ "        <title></title>\n" + "        <style>\n" + "            html,body {\n"
			+ "                width: 100%;\n" + "                margin: 0;\n" + "                padding: 0;\n"
			+ "            }\n" + "            html, body,* {\n" + "                font-family: \"微软雅黑\";\n"
			+ "                line-height: 24px;\n" + "                font-size: 16px;\n" + "            }\n"
			+ "            h1 {\n" + "                font-size: 24px;\n" + "            }\n" + "            body {\n"
			+ "            }\n" + "            .text-center {\n" + "                text-align: center;\n"
			+ "            }\n" + "            ul,li {\n" + "                list-style: none;\n" + "            }\n"
			+ "            ul {\n" + "                margin: 0;\n" + "                padding: 0 10px;\n"
			+ "            }\n" + "            img { height: auto; width: auto\\9; width:100%; }\n"
			+ "        </style>\n" + "    </head>\n" + "<body> ";

	private static final String HTML_SUFFIX = " </body></html>";

	public static boolean mainPage(Webpage webPage) throws IOException, ParseException {
		
		Webpage webPageTmp = ToutiaoProcessor3.getMainInfo(webPage.getId());
		if(webPageTmp != null &&  null != webPageTmp.getContent()) {
			System.out.println("已存在记录，跳过处理。webPageTmp = " + webPageTmp.toString());
			return false;
		}
		
		System.out.println("=============================");
		// 2. 添加索引数据
		XContentBuilder doc = XContentFactory.jsonBuilder()
				.startObject()
					.field("title", webPage.getTitle())
					.field("url", webPage.getUrl())
					.field("domain", webPage.getDomain())
					.field("spiderUUID", webPage.getSpiderUUID())
					.field("spiderInfoId", webPage.getSpiderInfoId())
					.field("category", webPage.getCategory())
					.field("gatherTime", webPage.getGathertime())
					.field("id", webPage.getId())
					.field("publishTime", webPage.getPublishTime())
					.field("dynamicFields", webPage.getDynamicFields())
					.field("processTime", webPage.getProcessTime())

					// .field("content", HTML_PREFIX + webPage.getContent() + HTML_SUFFIX)
					// .field("keywords", webPage.getKeywords())
					// .field("summary", webPage.getSummary())
					// .field("namedEntity", webPage.getNamedEntity())
				.endObject();

		IndexResponse response = client.prepareIndex("commons", "webpage", webPage.getId()).setSource(doc).execute()
				.actionGet();
		System.out.println("id = " + webPage.getId());

		// Index name
		String _index = response.getIndex();
		System.out.println("_index = " + _index);

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

		System.out.println("Result=" + response.getResult());

		System.out.println("列表数据 Done");
		
		return true;
	}

	public static void mainPage(String group_id, long publish_time, String title, String article_url, String tag,
			String source, String imgs) throws IOException, ParseException {

		long start = System.currentTimeMillis();
		Map<String, String> dynamicFields = new HashMap<>();
		dynamicFields.put("imgs", imgs);

		long cost = System.currentTimeMillis() - start;

		System.out.println("=============================");
		// 2. 添加索引数据
		XContentBuilder doc = XContentFactory.jsonBuilder().startObject().field("title", title)
				.field("url", article_url).field("domain", tag)
				.field("spiderUUID", "aaaafc39-6ff7-4a95-8024-b85d99350001")
				.field("spiderInfoId", "AV6ZI-TEE7C-scy0W2qu").field("category", source)
				.field("gatherTime", DateUtil.dateToStamp(DateUtil.getCurrentLongDateTime())).field("id", group_id)
				.field("publishTime", publish_time).field("dynamicFields", dynamicFields).field("processTime", cost)
				.endObject();

		IndexResponse response = client.prepareIndex("commons", "webpage", group_id).setSource(doc).execute()
				.actionGet();
		System.out.println("id = " + group_id);

		// Index name
		String _index = response.getIndex();
		System.out.println("_index = " + _index);

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

		System.out.println("Result=" + response.getResult());

		System.out.println("列表数据 Done");
	}

	public static void detailPage(String group_id, String content, Client client) throws IOException, ParseException {
		
		Webpage webPage = ToutiaoProcessor3.getMainInfo(group_id);

		NLPExtractor keywordsExtractor = new HANLPExtractor();
		NLPExtractor summaryExtractor = new HANLPExtractor();
		NLPExtractor namedEntitiesExtractor = new HANLPExtractor();

		String contentWithoutHtml = formatHtml(content);

		// 抽取关键词,10个词
		List<String> keywords = keywordsExtractor.extractKeywords(contentWithoutHtml);
		// 抽取摘要,5句话
		List<String> summary = summaryExtractor.extractSummary(contentWithoutHtml);
		// 抽取命名实体
		Map<String, Set<String>> namedEntity = namedEntitiesExtractor.extractNamedEntity(contentWithoutHtml);
		
		webPage.setContent(content);
		webPage.setKeywords(keywords);
		webPage.setSummary(summary);
		webPage.setNamedEntity(namedEntity);

		System.out.println("==============详情入库ES处理 stat============");
		// 2. 添加索引数据
		XContentBuilder doc = XContentFactory.jsonBuilder()
				.startObject()
					.field("title", webPage.getTitle())
					.field("url", webPage.getUrl())
					.field("domain", webPage.getDomain())
					.field("spiderUUID", webPage.getSpiderUUID())
					.field("spiderInfoId", webPage.getSpiderInfoId())
					.field("category", webPage.getCategory())
					.field("gatherTime", webPage.getGathertime())
					.field("id", webPage.getId())
					.field("publishTime", webPage.getPublishTime())
					.field("dynamicFields", webPage.getDynamicFields())
					.field("processTime", webPage.getProcessTime())

					 .field("content", HTML_PREFIX + webPage.getContent() + HTML_SUFFIX)
					 .field("keywords", webPage.getKeywords())
					 .field("summary", webPage.getSummary())
					 .field("namedEntity", webPage.getNamedEntity())
				.endObject();

		IndexResponse response = client.prepareIndex("commons", "webpage", group_id).setSource(doc).execute()
				.actionGet();
		System.out.println("id = " + group_id);

		// Index name
		String _index = response.getIndex();
		System.out.println("_index = " + _index);

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

		System.out.println("Result=" + response.getResult());

		System.out.println("==============详情入库ES处理 end============");
		System.out.println("详情数据 Done");

	}

	public static String formatHtml(String content) {
		content = content.replace("</p>", "***");
		content = content.replace("<BR>", "***");
		content = content.replaceAll("<([\\s\\S]*?)>", "");
		content = content.replace("***", "<br/>");
		content = content.replace("\n", "<br/>");
		content = content.replaceAll("(\\<br/\\>\\s*){2,}", "<br/> ");
		content = content.replaceAll("(&nbsp;\\s*)+", " ");
		return content.replaceAll("<br/>", "");
	}
}