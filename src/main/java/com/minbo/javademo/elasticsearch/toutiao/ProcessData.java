package com.minbo.javademo.elasticsearch.toutiao;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.minbo.javademo.elasticsearch.nlp.HANLPExtractor;
import com.minbo.javademo.elasticsearch.nlp.NLPExtractor;
import com.minbo.javademo.utils.StrUtil;

public class ProcessData {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	public static final String HTML_PREFIX = "<!DOCTYPE html>\n" + "<html lang=\"en\">\n" + "    <head>\n"
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

	public static final String HTML_SUFFIX = " </body></html>";

	public boolean mainPage(MyWebpage webPage) throws IOException, ParseException {
		ToutiaoDetail tDetail = new ToutiaoDetail();
		MyWebpage webPageTmp = tDetail.getMainInfo(webPage.getId());
		if(webPageTmp != null && !StrUtil.null2Str(webPageTmp.getContent()).equals("")) {
			logger.info("已存在记录content，跳过处理。id = " + webPageTmp.getId());
			return false;
		}else {
//			logger.info("不存在content，新处理");
//			logger.info("==============添加列表数据 start ===============");
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
						.field("itemId", webPage.getItemId())
						.field("publishTime", webPage.getPublishTime())
						.field("dynamicFields", webPage.getDynamicFields())
						.field("processTime", webPage.getProcessTime())

						.field("content", webPage.getContent())
						.field("keywords", webPage.getKeywords())
						.field("summary", webPage.getSummary())
						.field("namedEntity", webPage.getNamedEntity())
					.endObject();

			IndexResponse response = ToutiaoDetail.client.prepareIndex("commons", "webpage", webPage.getId()).setSource(doc).execute()
					.actionGet();
			logger.info("id = " + webPage.getId());
			logger.info("result = " + response.getResult());

			logger.info("列表数据 Done");
			logger.info("==============添加列表数据 end ===============");
			logger.info("=============================================");
			
			return true;
		}
	}

	public void detailPage(String id, String content, Client client) throws IOException, ParseException {
		ToutiaoDetail tDetail = new ToutiaoDetail();
		MyWebpage webPage = tDetail.getMainInfo(id);
		if(webPage != null && !StrUtil.null2Str(webPage.getContent()).equals("")) {
			logger.info("已存在详情数据了，重复记录，跳过处理。webPage = " + webPage);
		}else {
			logger.info("不存在详情数据，新处理");
			NLPExtractor keywordsExtractor = new HANLPExtractor();
			NLPExtractor summaryExtractor = new HANLPExtractor();
			NLPExtractor namedEntitiesExtractor = new HANLPExtractor();
			
			String contentWithoutHtml = formatHtml(content);
			
			logger.info("contentWithoutHtml=" + contentWithoutHtml);
			if(StrUtil.null2Str(contentWithoutHtml).equals("")) {
				webPage.setKeywords(null);
				webPage.setSummary(null);
				webPage.setNamedEntity(null);
				
			}else {
				// 抽取关键词,10个词
				List<String> keywords = keywordsExtractor.extractKeywords(contentWithoutHtml);
				// 抽取摘要,5句话
				List<String> summary = summaryExtractor.extractSummary(contentWithoutHtml);
				// 抽取命名实体
				Map<String, Set<String>> namedEntity = namedEntitiesExtractor.extractNamedEntity(contentWithoutHtml);
				
				webPage.setKeywords(keywords);
				webPage.setSummary(summary);
				webPage.setNamedEntity(namedEntity);
			}
			webPage.setContent(content);
			logger.info("content=" + webPage.getContent());
			logger.info("keywords=" + webPage.getKeywords());
			logger.info("summary=" + webPage.getSummary());
			logger.info("namedEntity=" + webPage.getNamedEntity());

			logger.info("===========================================");
			logger.info("==============详情入库ES处理 stat============");
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
						.field("itemId", webPage.getItemId())
						.field("publishTime", webPage.getPublishTime())
						.field("dynamicFields", webPage.getDynamicFields())
						.field("processTime", webPage.getProcessTime())

						 .field("content", HTML_PREFIX + webPage.getContent() + HTML_SUFFIX)
						 .field("keywords", webPage.getKeywords())
						 .field("summary", webPage.getSummary())
						 .field("namedEntity", webPage.getNamedEntity())
					.endObject();

			IndexResponse response = client.prepareIndex("commons", "webpage", webPage.getId()).setSource(doc).execute()
					.actionGet();
			logger.info("id=" + id);
			logger.info("result=" + response.getResult());

			logger.info("详情数据 Done");
			logger.info("==============详情入库ES处理 end============");
			logger.info("===========================================");
		}
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