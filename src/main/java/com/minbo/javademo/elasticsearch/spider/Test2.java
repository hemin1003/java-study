package com.minbo.javademo.elasticsearch.spider;

import com.minbo.javademo.elasticsearch.nlp.HANLPExtractor;
import com.minbo.javademo.elasticsearch.nlp.NLPExtractor;

public class Test2 {

	public static void main(String[] args) {

		String content = "在河南的宝丰县，当地人习惯称这里的羊肉汤叫羊肉冲汤，今年31岁的康师父正在汤花翻腾的大锅前给客人冲汤。他天天早上四五点起床熬汤，然后不断忙到下午两三点打烊，一年下 来歇不了几天。 这碗羊肉冲汤之以是在当地广受欢迎，大概是因为做法比较特别，冲出来的羊肉不腥不膻，汤味儿也是鲜美十分。案上摆着切好的羊肉、羊头肉、羊肚、羊肠、羊肝、羊肺等等，根据客人的个人爱好往这些大瓷碗里添。 一碗一般的汤是10块钱，如果客人需要加上点肉就要再加些钱，康师傅经常是卖到一碗30元，很多客人另加了20块钱的肉。 之所以当地管羊肉汤叫羊肉冲汤，跟康师父这个冲的动作有关，先把汤盛进放肉的碗里，然后再用勺子挡着碗边把汤滤去，一碗汤下来这个冲滗的动作要反复七八次。 羊肉冲滗的次数，冬夏各有讲究，一般夏天五六次便可，冬季就要到八九次了，为的就是把肉和汤更加的入味，让羊肉吃起来更香。 冲过汤的肉，加案子上放的羊油辣椒、盐、味精、五香粉、胡椒粉等，羊油辣椒是康师父自己特地熬制的，对于一个羊肉汤馆来讲，这一盆辣椒意义很是特别，更是最重要的调味料了。 在调料都加上之后，舀上一勺清汤，这碗冲汤就是做好了，冬季撒上蒜苗，夏天就放点儿葱花，美观又出味儿。 因为羊肉汤里有油，滚汤的温度能够到达一百多度，再加上冲滗七八次，碗的温度已十分高，以是康师父特地定制了这类粗瓷厚碗，另外还做了这个端，也是很细心的一个老板了。 在北方喝羊肉汤，配馍什么的比较多，有烧饼、烙馍、锅盔等，在当地喝羊肉冲汤，大家配的便是烧饼，能够单吃也能够泡碗里。 这碗羊肉冲汤光彩亮光，放辣椒前呈天然的乳白色，肉味鲜美，嚼一口满嘴生香。即使在酷热的炎天，来光顾的人也是络绎不绝，就这么个羊肉冲汤，康师父一天能卖出去三五百碗呢，也是大家都喜欢他家做的这个汤，实在是香。";
		
		NLPExtractor keywordsExtractor = new HANLPExtractor();
		NLPExtractor summaryExtractor = new HANLPExtractor();
		NLPExtractor namedEntitiesExtractor = new HANLPExtractor();

		// 抽取关键词,10个词
		System.out.println("keywords=" + keywordsExtractor.extractKeywords(content));
		// 抽取摘要,5句话
		System.out.println("summary=" + summaryExtractor.extractSummary(content));
		// 抽取命名实体
		System.out.println("namedEntity=" + namedEntitiesExtractor.extractNamedEntity(content));
		
		System.out.println();
	}

}
