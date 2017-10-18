package com.minbo.javademo.elasticsearch.toutiao;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.minbo.javademo.elasticsearch.nlp.HANLPExtractor;
import com.minbo.javademo.elasticsearch.nlp.NLPExtractor;
import com.minbo.javademo.utils.StrUtil;

import us.codecraft.webmagic.selector.JsonPathSelector;

public class ProcessData {
	
	protected static final Logger logger = Logger.getLogger(ProcessData.class);

	public boolean mainPage(Webpage webPage) throws IOException, ParseException {
		ToutiaoDetail tDetail = new ToutiaoDetail();
		Webpage webPageTmp = tDetail.getMainInfo(webPage.getId());
		if(webPageTmp != null && !StrUtil.null2Str(webPageTmp.getContent()).equals("")) {
			logger.info("已存在记录content，跳过处理。id = " + webPageTmp.getId());
			return false;
		}else {
//			logger.info("不存在content，新处理");
//			logger.info("==============添加列表数据 start ===============");
			webPage.setFlag(0);
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
						.field("flag", webPage.getFlag())
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

	public void detailPage(String id, String content, Client client, String imgs) throws IOException, ParseException {
		ToutiaoDetail tDetail = new ToutiaoDetail();
		Webpage webPage = tDetail.getMainInfo(id);
		
		if(webPage.getFlag() == 1) {
			logger.info("已存在详情数据了，重复记录，跳过处理。webPage.id = " + webPage.getId());
		}else {
			logger.info("不存在详情数据，新处理");
			
			//当列表没有图时（长度为9，即为空），取详情内容第一张图
			if(webPage.getDynamicFields() == null || webPage.getDynamicFields().toString().length() <= 9) {
				if(!StrUtil.null2Str(imgs).equals("")) {
					Map<String, Object> dynamicFields = new HashMap<>();
					dynamicFields.put("imgs", "[" + imgs + "]");
					webPage.setDynamicFields(dynamicFields);
				}
			}
			
			NLPExtractor extractor = new HANLPExtractor();
//			NLPExtractor summaryExtractor = new HANLPExtractor();
//			NLPExtractor namedEntitiesExtractor = new HANLPExtractor();
			
			String contentWithoutHtml = formatHtml(content);
			
			if(StrUtil.null2Str(contentWithoutHtml).equals("")) {
				webPage.setKeywords(null);
				webPage.setSummary(null);
				webPage.setNamedEntity(null);
				
			}else {
				// 抽取关键词, 10个词
				List<String> keywords = extractor.extractKeywords(contentWithoutHtml);
				// 抽取摘要, 5句话
				List<String> summary = extractor.extractSummary(contentWithoutHtml);
				// 抽取命名实体
				Map<String, Set<String>> namedEntity = null;
				try {
					namedEntity = extractor.extractNamedEntity(contentWithoutHtml);
				} catch (Exception e) {
					logger.error("抽取命名实体异常：" + e.getMessage(), e);
				}
				
				webPage.setKeywords(keywords);
				webPage.setSummary(summary);
				webPage.setNamedEntity(namedEntity);
			}
			webPage.setContent(content);
			webPage.setFlag(1);
			
			logger.info("id=" + webPage.getId());
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

						 .field("content", Utils.HTML_PREFIX + webPage.getContent() + Utils.HTML_SUFFIX)
						 .field("keywords", webPage.getKeywords())
						 .field("summary", webPage.getSummary())
						 .field("namedEntity", webPage.getNamedEntity())
						 .field("flag", webPage.getFlag())
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