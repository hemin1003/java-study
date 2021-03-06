package com.minbo.javademo.elasticsearch.spider;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.minbo.javademo.elasticsearch.GlobelUtils;
import com.minbo.javademo.elasticsearch.nlp.HANLPExtractor;
import com.minbo.javademo.elasticsearch.nlp.NLPExtractor;
import com.minbo.javademo.utils.UUID;

public class TestEsAddData {

	public static void main(String[] args) throws IOException {
		String ip = GlobelUtils.IP;

		Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
		Client client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), 9300));

		String contentWithoutHtml = "9月16日，在互联网 <http://gu.qq.com/jj160636>视频平台暴风集团 <http://gu.qq.com/sz300431>\n" + 
				"（300431）内部年会上，暴风集团（300431）CEO冯鑫回顾了自己创业初期到现在的三次压力，表示大风吹不倒无事之人。暴风内部人士向澎湃新闻透露了一部分演讲实录。\n" + 
				"\n" + 
				"\n" + 
				"乐视危机发生以后，暴风总被拿来和乐视做类比，相似的业务布局、财务并表、股权质押等。暴风的市值从2015年369亿元人民币，缩水到7月18日停牌时的67.1亿元。\n" + 
				"\n" + 
				"\n" + 
				"冯鑫提及“乐某公司”：“有人说，人生最怕猪一样的队友。我觉得，还怕无法琢磨无法言喻的类同行。这场旋风直接刮到了我们，对我们周边的环境造成了非常多的压力，一开始以为和我们没关系，我们也没到有债主天天在公司门口搭帐篷，但是后来发现，今天的暴风和上市前的暴风不一样了，它真的比以前大了。有句古话说得好，’木秀于林，风必摧之’。苍蝇不盯无缝的蛋，风吹不倒无事之人。”\n" + 
				"\n" + 
				"\n" + 
				"冯鑫反思，在这场危机中，暴风为什么会受到影响，受到质疑和挑战？冯鑫罕见坦承：“暴风到今天为止，真的没有一个很强悍的业务。暴风以前是一个二流的视频平台，尽管暴风魔镜和暴风TV做得非常好，但到今天，还不是非常结实。”\n" + 
				"\n" + 
				"\n" + 
				"冯鑫说，过去暴风在内控和管理上有不严谨的地方，当压力增大的时候，那些脆弱的地方，有漏洞的地方都有可能出事、“可能过去一个合同签得不严谨，可能过去某个重要的资本方沟通得不顺畅，或者拿了别人的钱拿了一年没有理过别人。”\n" + 
				"\n" + 
				"“压力是帮你看清楚问题本质的影子。压力面前，不仅能看到事情，也能看到人。身边的同事，合作伙伴都会有变化，所以真的要珍惜压力。”冯鑫说。\n" + 
				"\n" + 
				"冯鑫还回顾了自己创业至今遇到的几次压力，希望以此给员工和外界打气，试图告诉大家，度过压力过后会让这家公司在浴火之后会得到重生。\n" + 
				"\n" + 
				"第一次是2005年10月冯鑫创业之初。刚刚从雅虎 <http://gu.qq.com/usYHOO.OQ>\n" + 
				"辞职跟别人约好一起准备创业的冯鑫，被别人“放了鸽子”。后来找了周鸿祎、雷军，但都处处碰壁。\n" + 
				"\n" + 
				"第二次是在2009年5月19日，全国多个省份连续发生大面积互联网瘫痪事故，随后，暴风影音陷身事故的“风暴”中心。\n" + 
				"\n" + 
				"第三次是在2010年左右，刚刚递交完上市文件，被证监会告知暂停IPO <http://finance.qq.com/stock/xingu/>\n" + 
				"。“这五年时间意味着你不许融资，每年还要比去年盈利上涨，你也不能给新进的核心团队发股份，因为股份不能变更。在互联网历史上，让一个公司不去融资，不去引进高管，每年还保证利润增长，交材料以后这样扛三到四年，我不知道还有没有第二个这样的公司。”\n" + 
				"\n" + 
				"\n" + 
				"至于前阵子小米公司同样出现危机，但是小米在公司董事长雷军控制之下，最终恢复了增长。“有些人天生是这样的人，与能力无关，他会控制。在压力面前，他像是和魔鬼对抗的上帝一样，他能控制所有的一切。”冯鑫说。\n" + 
				"\n" + 
				"\n" + 
				"按他的说法，目前暴风距离2020年第一个收获季还有三年，希望全体员工能将“创造精神”提升到更高的高度。在未来的三年里，力争让三驾马车业务走得更远、更令人尊敬，努力创造一个更健壮，更独立，更协同的组织。\n" + 
				"\n" + 
				"\n" + 
				"从2016年暴风十周年年会上提出的暴风虫—N421，升级到2017年的AI+战略。暴风集团的核心业务始终聚焦TV和VR这两块新视频入口，未来的一年，VR业务更加健壮；TV业务实现加速度成长，从助跑到起飞，用AI+换道超车。\n" + 
				"\n" + 
				"以下是澎湃新闻整理的冯鑫在2017年暴风集团11周年年会上的发言（节选，未经主讲人审阅）：\n" + 
				"\n" + 
				"压力走了以后你当时忘了看，你就愚蠢地度过了这场压力，所以我第一个对压力的分享是：压力是帮你看清楚问题本质的影子。第二件事情，倒是说为什么不能平平安安，老百姓 \n" + 
				"<http://gu.qq.com/sh603883>\n" + 
				"经常讲一路平平安安才是福，是吧？但实话说，你只要做几件事情试一试，我不相信有平平安安。我甚至对它的规律有一个描述。我觉得中国互联 \n" + 
				"<http://gu.qq.com/sz164906>\n" + 
				"网三年一个大机会，五年一个巨头的机会。至于压力，平均2.5年会有一次，2.5年你会倒一次霉，超过三年就运气太好了。其实压力和我们很熟络，我就借今天压力这堂课和大家回顾暴风11年历史里面关于压力的四件事情。我第一次感受到压力，第一次知道它是压力是我创业（的时候）。大家看11周年是按照暴风成立的时间，2007年1月（计算的）。\n" + 
				"\n" + 
				"\n" + 
				"我创业是2005年10月，所以我们年会是每年的十月份。我离开了雅虎创业，要做一个软件，这个软件的名字叫做酷热下载。这个产品在江湖上应该出现过。但是第一，我不用电脑的这件事大家应该知道吧？跟我一起干代码的人叫徐鸣（音），现在是猎豹公司的合伙人，他要和我一起创业的，然后不说什么原因了，不过已经是事实了，准备创业，结果第二天第三天这人不和我创业了，他含着眼泪和我说，抱歉老大，他说我家里有压力，没办法了。我说，祝福你。我实话和各位说，我们在第二个月挣到钱以后，我还是拿出来5万还是10万给了他，感谢他出来之前的努力。然后我选了第二个人来和我出来创业，结果也没有跟我出来，因为也被周鸿祎留下了。周鸿祎说这人不能带走。这是第二件事。我一开始创业，需要大概50万到80万，具体我忘了。我先找了一个人，被拦住了，我又找了周鸿祎，后来找了雷军，雷军对我是最善意的，说“冯鑫这事儿我需要思考一下”。我说：“那要思考多长时间呢？”他看了我一眼，说：“这个不是很确定。”我那天回家，沿着小花园走了一圈又一圈，想着思考的时间不确定，那我创业的时间是不是也不确定呢？我就跟他描述我没有钱，没有盈利，酷热下载那个产品后来也不允许做，然后我们创业了。这是我们第一次遇到的压力。\n" + 
				"\n" + 
				"\n" + 
				"我们第二次压力，大概是2008年还是2009年，我想在座的有人知道吧？中国有次断网，跟暴风有关，519（断网事件）。但是大家知道吗，在519之前一年的时间，暴风经过最大的变动。暴风在在线那场战役当中，没有抓住战略时机，没有融到钱，与对手的实力突然拉大了非常之大，找不到出口，暴风原有的创业骨干团队年薪幅度，一开始第一张让我非常感动的照片里面，我能看到阿鸣，任峰这些兄弟们都在2008年、2009年相继离开。\n" + 
				"\n" + 
				"\n" + 
				"大家可能不大有感觉。其实这种事一定会如实的，大概率情况下，所以我当时坐飞机往回飞，出了飞机场，张军接了我，到了工信部开他们的紧急事件研讨会。我进门之前在楼下一路小跑，其实会议已经开始开了，是让你回来。已经开了一个多小时了，我就跑进会场。我进会场之前，真的吸了一口气，因为我觉得可能结束了，当时现场还在讨论。然后这个事就这么搞，搞到后来，我们进来的互联网友商，其实别人只是偶然，可能不在乎你，其实后来我和他们也是好朋友。\n" + 
				"网易 <http://gu.qq.com/usNTES.OQ>\n" + 
				"可能新闻要搬到北京，他们要找个大的事件，就揪住了暴风和断网之间有关，做专题，本来我们害怕是我们的责任，但真的不是我们的责任，但网易说是我们的责任。我们派我们当时的市场总监飞到了广州，他现在是阿里的。飞到了广州，因为编辑为了怕沟通的时候没有事实，拒绝见面。后来我们动用了……反正我不跟你讲历史。后来两三个兄弟在我家住了大概一个礼拜吧，三五天，因为每个你发出的文字、发出的声音，必须字斟句酌，直到最后中央台对此事做了专题报道，我们要让那个专题报道能够给我们证明清白。但是中央电视台影响真是太难了。我们打了大概一个月还是一个半月的战役，好像没那么长，记不清了，但是度日如年。我觉得其实那叫劫后余生了。那是我们度过的第二次压力。\n" + 
				"\n" + 
				"\n" + 
				"我们第三次压力是后来我们终于想上市了。大概2009年、2010年，就往回拆了一年，拆完以后交材料交了半年。大多数人说我们熬了三年，其实不是的，我们熬了将近四到五年。我们刚放进去以后，中国证监会史无前例地说我们不批准IPO，暂时不审批了，长达将近两年多，我们熬了五年时间，这是第三次。这五年时间意味着什么？意味着你不许融资，每年还要比去年盈利上涨，你也不能给新进的核心团队发股份，因为股份不能变更。在互联网历史上，让一个公司不去融资，不去引进高管，每年还保证利润增长，交材料以后这样扛三到四年，我不知道还有没有第二个这样的公司。其实熬到最后一年，说实话我觉得是没戏了。所以有时候，刚才是谁说的，是你是什么人，你是不是坚持住了。我们坚持到最后一瞬间而且成功，开广电的会议，我在想阿里（投资）的事情要不要答应，然后接了个电话，说证监会IPO审批重新开始。我当时是在成都一个小饭馆吃饭，这是第三次。\n" + 
				"\n" + 
				"\n" + 
				"今天我们迎来第四次压力。说实话，相比前三次，是不是有点小。我相信我不会再去想，走到任何舞台上，专门把压力的例子给大家回顾一遍。所以今天有机会回顾一次压力，其实我并不会经常去回顾它。我给大家分享我的第二个感觉，压力其实一定会来的，如果压力一直没有找你，说明你是平民百姓，除了地震，那是躲不开的，压力不会找你的。等你有点模样，等你的公司有点模样，压力一定会找到你的。所以两到三年一次是基本规律，我们要学着习惯接受它的到来。第三个我想分享的是风暴过后，收获总是远远大过于失去的。每次压力过后，几乎什么都不会失去。当然，如果是压力中心的那个人出了马脚，那么你什么都会失去。你只要没有了马脚，什么都不会失去，你会收获团队，你会收获核心竞争力，你会收获原来短板的问题得到弥补。我很有文化，我曾经特别喜欢的一个俄罗斯文学家陀思妥耶夫斯，他说：苦态？我生怕自己配不上我蒙受的苦难。我觉得这话我是非常认同的。因为我们有资格去蒙受苦难。这是我想送给大家的，苦难之后必生智慧。\n" + 
				"\n" + 
				"关于压力，我分享的第四件事情：当风暴来临的时候，你什么都不用管，只要做好两件事，很难很具体。\n" + 
				"\n" + 
				"\n" + 
				"第一件事情，压力大的时候，你能不能让脑子里不再思虑，而是安静地思考。思虑什么呢，（比如）最近好难受，干嘛到我家来，我什么时候才能度过，我有没有种侥幸心态，谁能救我一把。你就会在一个没有答案的思虑过程当中度过你的一天又一天，如果严重的话，你会走上著名的抑郁症，要避开思虑，安静地思考。思考什么呢？我要解这道题，我要设什么条件，我要画哪条辅助线，这叫思考。\n" + 
				"\n" + 
				"\n" + 
				"第二件事是坚定地把你手头至少不错的那件事情做到最好，做得比平时更好，也许你找不到最重要的事情，但是你找个肯定不是没用的事情，哪怕是写篇文章，哪怕是给大家写封邮件，哪怕是给下个版本的UI做一下交互，或者做一次重要的谈判，把这个谈判做得和之前不一样，不是去了就去了，提前写写作业，看人是什么，事是什么，大概挑战是什么，打算怎么解决，比上一次做得更漂亮。坚定地把你手头那件正确的小事做好，你只需要做这两件事就没有问题了。我不给大家开玩笑，大概一个多月还是两个多月前的时候，今天没有来的老刘头一次在微信上和我说压力这两个字。我给他发了一个微信回去，我的微信就是这么说的：咬紧牙关，把今天的每一件事做好，一步步走过，我们就会走通盈利。我们老刘同学，发了喝酒拥抱的表情过来。我分享的第四件事情是压力来临的时候，只要做好两件事情就好了。\n" + 
				"\n" + 
				"关于压力，我要分享的最后一件事情是，其实压力一定会过去的。道德经里有句话叫暴雨不中日，狂风不中朝，狂风暴雨都会过去的。压力多长时间会过去呢？大家知道当年\n" + 
				"亚洲金融 <http://gu.qq.com/hk00662>\n" + 
				"风暴吧？知道美国次贷危机吧？多长时间让大局不受影响呢？汶川地震呢，多长时间？没有人超过一年时间，一年是极限，六个月就结束了。六到九个月应该就结束了。所以一场压力来的时候，非常短暂，可能再过两三个月，我们就忘了这件事了。我们再没有这种感觉来说这个事。但是在风暴面前，人这个高贵的动物有个选择的权利。我在每一次压力之前，我在金山面临过压力，我在雅虎也面临过压力，我创业也面临过压力，面临过至关灭顶的压力，我都在压力面前看到向左走向右走。平时是看不出来的。平时你看到那些血勇之人，在压力面前全露了马脚。你看到那些安静的人，显得有点害羞的人，可能那一天非常勇敢。大概会有两个选择。每个人如果你没在压力面前，或者离压力的漩涡远的人，我第一个奉劝你，如果你已经接近那个漩涡了，往里走一走，给自己一次机会去经历一次压力，那是人生的财富。它甚至比一次成功对你更重要。第二个建议是，那个时候你要知道你在做游戏，我看到压力面前向左走的人，突然变得很安静很冷静，甚至变得很智慧，明确地指出今天压力的背后是谁谁谁的错误，是谁谁谁曾经埋下的伏笔，他可能很早就看出来了，事后诸葛亮。\n" + 
				"\n" + 
				"\n" + 
				"当然还有更多的人已经准备卷铺盖找下家了。但是还有更多的人袖手旁观，虽然压力已经波及到他了或者快挨到他了，他往后退了一步，压力和自己无关了。说实话，说压力和自己无关的人很可恶，但是在压力面前大大方方说出这样的话，觉得自己很高明的人，那真的该杀了，意味着在公司危险的时候，他会让危险加倍。他起得作用是让危险double的作用。这是一类人。但是我看到还有另外一类人。第一个叫勇敢，在那个时候他好像突然已经忘掉一切了，抓住一项重要的事情冲过去。我还看到第二种人，叫担当。本来不是他的事，他已经揽过来做了，本来是别人地盘的事，由于被压力压到了，他觉得会有危险，他忍不住去做了。因为在压力来临的时候，你们看到左边那个人吗？本来是他地盘的事，只要有人伸手，不伸手他也要你退出去，有人伸手他早就让出来了。那么右边那个人，就把他抓到手里，去把他干了。我看到有那种跨界的担当，我还看到在自己压力下的控制。\n" + 
				"\n" + 
				"\n" + 
				"我记得以前的雷总，小米之前遇到危机，我说小米遇到危机时候，我说他是控制得住的。有些人在压力面前，他的控制力是非常宝贵的，会让这家公司在浴火之后会得到重生。有些人天生是这样的人，与能力无关，他会控制。在压力面前，他像是和魔鬼对抗的上帝一样，他在控制所有的一切。所以这是我与各位分享的最后一点，与每个人相关，你们人生中会碰到压力的，碰到压力所有人都别躲，又没让你死，又没把手枪搬到你脑袋上。第二，在压力面前，你有一次选择的权利，你有一次主动选择人生的权利，我再说得不客气一点，你在压力面前退一次，可能你退一次就是退一辈子，你进一次可能人生也是进一辈子。所以这是我今天特别想和各位分享的关于压力的最后一点。\n" + 
				"";
		
		 NLPExtractor keywordsExtractor = new HANLPExtractor();
		 NLPExtractor summaryExtractor = new HANLPExtractor();
		 NLPExtractor namedEntitiesExtractor = new HANLPExtractor();
		
		//抽取关键词,10个词
		List<String> keywords = keywordsExtractor.extractKeywords(contentWithoutHtml);
//        System.out.println("keywords=" + keywordsExtractor.extractKeywords(contentWithoutHtml));
        //抽取摘要,5句话
		List<String> summary = summaryExtractor.extractSummary(contentWithoutHtml);
//        System.out.println("summary=" + summaryExtractor.extractSummary(contentWithoutHtml));
        //抽取命名实体
		Map<String,Set<String>> namedEntity = namedEntitiesExtractor.extractNamedEntity(contentWithoutHtml);
//        System.out.println("namedEntity=" + namedEntitiesExtractor.extractNamedEntity(contentWithoutHtml));
		System.out.println();
		
		
//		for (int i = 0; i < 100; i++) {
			String id = UUID.getUUID();
			System.out.println("=============================");
			String name = getRandomEnglishName();
			// 2. 添加索引数据
			XContentBuilder doc = XContentFactory.jsonBuilder()
					.startObject()
						.field("content", "This is content.")
						.field("title", "这是一篇关于御风翱翔公司的苹果新闻哦")
						.field("url", "www.xxx.url")
						.field("domain", "news_society")
						.field("spiderUUID", "aaaafc39-6ff7-4a95-8024-b85d99350001")
						.field("spiderInfoId", "AV6ZI-TEE7C-scy0W2qu")
						.field("gatherTime", "1491698100000")
						.field("keywords", keywords)
						.field("summary", summary)
						.field("id", id)
						.field("publishTime", "1491698100000")
						.field("namedEntity", namedEntity)
						.field("processTime", 20)
					.endObject();

			IndexResponse response = client.prepareIndex("commons", "webpage", id).setSource(doc)
					.execute().actionGet();
			System.out.println("id = " + id);
			
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
//		}

		client.close();
		
		System.out.println("Done");
		
	}

	public static final String[] FEMALE_FIRST_NAMES = { "Mary", "Patricia", "Linda", "Barbara", "Elizabeth", "Jennifer",
			"Maria", "Susan", "Margaret", "Dorothy", "Lisa", "Nancy", "Karen", "Betty", "Helen", "Sandra", "Donna",
			"Carol", "Ruth", "Sharon", "Michelle", "Laura", "Sarah", "Kimberly", "Deborah", "Jessica", "Shirley",
			"Cynthia", "Angela", "Melissa", "Brenda", "Amy", "Anna", "Rebecca", "Virginia", "Kathleen", "Pamela",
			"Martha", "Debra", "Amanda", "Stephanie", "Carolyn", "Christine", "Marie", "Janet", "Catherine", "Frances",
			"Ann", "Joyce", "Diane", "Alice", "Julie", "Heather", "Teresa", "Doris", "Gloria", "Evelyn", "Jean",
			"Cheryl", "Mildred", "Katherine", "Joan", "Ashley", "Judith", "Rose", "Janice", "Kelly", "Nicole", "Judy",
			"Christina", "Kathy", "Theresa", "Beverly", "Denise", "Tammy", "Irene", "Jane", "Lori", "Rachel", "Marilyn",
			"Andrea", "Kathryn", "Louise", "Sara", "Anne", "Jacqueline", "Wanda", "Bonnie", "Julia", "Ruby", "Lois",
			"Tina", "Phyllis", "Norma", "Paula", "Diana", "Annie", "Lillian", "Emily", "Robin", "Peggy", "Crystal",
			"Gladys", "Rita", "Dawn", "Connie", "Florence", "Tracy", "Edna", "Tiffany", "Carmen", "Rosa", "Cindy",
			"Grace", "Wendy", "Victoria", "Edith", "Kim", "Sherry", "Sylvia", "Josephine", "Thelma", "Shannon",
			"Sheila", "Ethel", "Ellen", "Elaine", "Marjorie", "Carrie", "Charlotte", "Monica", "Esther", "Pauline",
			"Emma", "Juanita", "Anita", "Rhonda", "Hazel", "Amber", "Eva", "Debbie", "April", "Leslie", "Clara",
			"Lucille", "Jamie", "Joanne", "Eleanor", "Valerie", "Danielle", "Megan", "Alicia", "Suzanne", "Michele",
			"Gail", "Bertha", "Darlene", "Veronica", "Jill", "Erin", "Geraldine", "Lauren", "Cathy", "Joann",
			"Lorraine", "Lynn", "Sally", "Regina", "Erica", "Beatrice", "Dolores", "Bernice", "Audrey", "Yvonne",
			"Annette", "June", "Samantha", "Marion", "Dana", "Stacy", "Ana", "Renee", "Ida", "Vivian", "Roberta",
			"Holly", "Brittany", "Melanie", "Loretta", "Yolanda", "Jeanette", "Laurie", "Katie", "Kristen", "Vanessa",
			"Alma", "Sue", "Elsie", "Beth", "Jeanne" };
	
	private static final String[] MALE_FIRST_NAMES = {"James", "John", "Robert", "Michael", "William", "David", "Richard", "Charles", "Joseph","Thomas", "Christopher", "Daniel", "Paul", "Mark", "Donald", "George", "Kenneth", "Steven","Edward", "Brian", "Ronald", "Anthony", "Kevin", "Jason", "Matthew", "Gary", "Timothy","Jose", "Larry", "Jeffrey", "Frank", "Scott", "Eric", "Stephen", "Andrew", "Raymond","Gregory", "Joshua", "Jerry", "Dennis", "Walter", "Patrick", "Peter", "Harold", "Douglas","Henry", "Carl", "Arthur", "Ryan", "Roger", "Joe", "Juan", "Jack", "Albert", "Jonathan","Justin", "Terry", "Gerald", "Keith", "Samuel", "Willie", "Ralph", "Lawrence", "Nicholas","Roy", "Ben",
			"jamin", "Bruce", "Brandon", "Adam", "Harry", "Fred", "Wayne", "Billy", "Steve","Louis", "Jeremy", "Aaron", "Randy", "Howard", "Eugene", "Carlos", "Russell", "Bobby","Victor", "Martin", "Ernest", "Phillip", "Todd", "Jesse", "Craig", "Alan", "Shawn","Clarence", "Sean", "Philip", "Chris", "Johnny", "Earl", "Jimmy", "Antonio", "Danny","Bryan", "Tony", "Luis", "Mike", "Stanley", "Leonard", "Nathan", "Dale", "Manuel", "Rodney","Curtis", "Norman", "Allen", "Marvin", "Vincent", "Glenn", "Jeffery", "Travis", "Jeff","Chad", "Jacob", "Lee", "Melvin", "Alfred", "Kyle", "Francis", "Bradley", "Jesus", "Herbert","Frederick", "Ray", "Joel", "Edwin", "Don", "Eddie", "Ricky", "Troy", 
			"Randall", "Barry","Alexander", "Bernard", "Mario", "Leroy", "Francisco", "Marcus", "Micheal", "Theodore","Clifford", "Miguel", "Oscar", "Jay", "Jim", "Tom", "Calvin", "Alex", "Jon", "Ronnie","Bill", "Lloyd", "Tommy", "Leon", "Derek", "Warren", "Darrell", "Jerome", "Floyd", "Leo","Alvin", "Tim", "Wesley", "Gordon", "Dean", "Greg", "Jorge", "Dustin", "Pedro", "Derrick","Dan", "Lewis", "Zachary", "Corey", "Herman", "Maurice", "Vernon", "Roberto", "Clyde","Glen", "Hector", "Shane", "Ricardo", "Sam", "Rick", "Lester", "Brent", "Ramon", "Charlie","Tyler", "Gilbert", "Gene"};
	
	public static final String[] LAST_NAMES = {"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore","Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia","Martinez", "Robinson", "Clark", "Rodriguez", "Lewis", "Lee", "Walker", "Hall", "Allen","Young", "Hernandez", "King", "Wright", "Lopez", "Hill", "Scott", "Green", "Adams", "Baker","Gonzalez", "Nelson", "Carter", "Mitchell", "Perez", "Roberts", "Turner", "Phillips","Campbell", "Parker", "Evans", "Edwards", "Collins", "Stewart", "Sanchez", "Morris","Rogers", "Reed", "Cook", "Morgan", "Bell", "Murphy", "Bailey", "Rivera", "Cooper","Richardson", "Cox", "Howard", "Ward", "Torres", "Peterson", "Gray", "Ramirez", "James","Watson", "Brooks", "Kelly", "Sanders", "Price", "Bennett", "Wood", "Barnes", "Ross","Henderson", "Coleman", "Jenkins", "Perry", "Powell", "Long", "Patterson", "Hughes","Flores", "Washington", "Butler", "Simmons", "Foster", "Gonzales", "Bryant", "Alexander","Russell", "Griffin", "Diaz", "Hayes", "Myers", "Ford", "Hamilton", "Graham", "Sullivan","Wallace", "Woods", "Cole", "West", "Jordan", "Owens", "Reynolds", "Fisher", "Ellis","Harrison", "Gibson", "Mcdonald", "Cruz", "Marshall", "Ortiz", "Gomez", "Murray", "Freeman","Wells", "Webb", "Simpson", "Stevens", "Tucker", "Porter", "Hunter", "Hicks", "Crawford","Henry", "Boyd", "Mason", "Morales", "Kennedy", "Warren", "Dixon", "Ramos", "Reyes", "Burns","Gordon", "Shaw"

			, "Holmes", "Rice", "Robertson", "Hunt", "Black", "Daniels", "Palmer","Mills", "Nichols", "Grant", "Knight", "Ferguson", "Rose", "Stone", "Hawkins", "Dunn","Perkins", "Hudson", "Spencer", "Gardner", "Stephens", "Payne", "Pierce", "Berry","Matthews", "Arnold", "Wagner", "Willis", "Ray", "Watkins", "Olson", "Carroll", "Duncan","Snyder", "Hart", "Cunningham", "Bradley", "Lane", "Andrews", "Ruiz", "Harper", "Fox","Riley", "Armstrong", "Carpenter", "Weaver", "Greene", "Lawrence", "Elliott", "Chavez","Sims", "Austin", "Peters", "Kelley", "Franklin", "Lawson"};
	public final static String[] EMAIL_SUFFIX = { "qq.com", "126.com", "163.com", "gmail.com", "163.net", "msn.com",
			"hotmail.com", "yahoo.com.cn", "sina.com", "@mail.com", "263.net", "sohu.com", "21cn.com", "sogou.com" };

	public final static <T> T nextValue(T[] array) {
		assert (array != null && array.length > 0);
		return array[new Random().nextInt(array.length)];
	}

	public final static String getRandomEnglishName() {
		return getRandomEnglishFirstName() + " " + getRandomEnglishLastName();
	}

	public final static String getRandomEnglishFirstName() {
		return new Random().nextBoolean() ? nextValue(FEMALE_FIRST_NAMES) : nextValue(MALE_FIRST_NAMES);
	}

	public final static String getRandomEnglishLastName() {
		return nextValue(LAST_NAMES);
	}

	public final static String getRandomEmailAddress() {
		return getRandomEnglishFirstName() + getRandomEnglishLastName() + "@" + nextValue(EMAIL_SUFFIX);
	}
}