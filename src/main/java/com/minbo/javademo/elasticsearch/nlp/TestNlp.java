package com.minbo.javademo.elasticsearch.nlp;

import org.nlpcn.commons.lang.finger.FingerprintService;

/**
 * 指纹去重
 * 地址：https://github.com/hemin1003/nlp-lang
 * @author Minbo
 */
public class TestNlp {

	/**
	 * 任何一段信息文字，都可以对应一个不太长的随机数，作为区别它和其它信息的指纹（Fingerprint)。
	 * 只要算法设计的好，任何两段信息的指纹都很难重复，就如同人类的指纹一样。信息指纹在加密、信息压缩和处理中有着广泛的应用。
	 * 这里的做法是文章抽取特征词，压缩为md5指纹。利用这些指纹进行hash去重。广泛应用在搜索结果、推荐结果去重。
	 */
	public static void main(String[] args) {
//		String content = "卓尔防线继续伤筋动骨 队长梅方出场再补漏说起来卓尔队长梅方本赛季就是个“补漏”的命！在中卫与右边后卫间不停地轮换。如果不出意外，今天与广州恒大一战梅方又要换位置，这也是汉军队长连续三场比赛中的第三次换位。而从梅方的身上也可以看出，本赛季汉军防线如此“折腾”，丢球多也不奇怪了。梅方自2009赛季中乙出道便一直司职中后卫，还曾入选过布拉泽维奇国奥队，也是司职的中卫。上赛季，梅方与忻峰搭档双中卫帮助武汉卓尔队中超成功，但谁知进入本赛季后从第一场比赛开始梅方便不断因为种种“意外”而居无定所。联赛首战江苏舜天时，也是由于登贝莱受伤，朱挺位置前移，梅方临危受命客串右边后卫。第二轮主场与北京国安之战梅方仅仅打了一场中卫，又因为柯钊受罚停赛4轮而不得不再次到边路“补漏”。随着马丁诺维奇被弃用，梅方一度成为中卫首选，在与上海东亚队比赛中，邱添一停赛，梅方与忻峰再度携手，紧接着与申鑫队比赛中移至边路，本轮忻峰又停赛，梅方和邱添一成为中卫线上最后的选择。至于左右边后卫位置，卓尔队方面人选较多，罗毅、周恒、刘尚坤等人均可出战。记者马万勇原标题：卓尔防线继续伤筋动骨队长梅方出场再补漏稿源：中新网作者：";
//      String content2 = "卓尔防线继续伤筋动骨 队长梅方出场再补漏说起来卓尔队长梅方本赛季就是个“补漏”的命！在中卫与右边后卫间不停地轮换。如果不出意外，今天与广州恒大一战梅方又要换位置，这也是汉军队长连续三场比赛中的第三次换位。而从梅方的身上也可以看出，本赛季汉军防线如此“折腾”，丢球多也不奇怪了。梅方自2009赛季中乙出道便一直司职中后卫，还曾入选过布拉泽维奇国奥队，也是司职的中卫。上赛季，梅方与忻峰搭档双中卫帮助武汉卓尔队中超成功，但谁知进入本赛季后从第一场比赛开始梅方便不断因为种种“意外”而居无定所。联赛首战江苏舜天时，也是由于登贝莱受伤，朱挺位置前移，梅方临危受命客串右边后卫。第二轮主场与北京国安之战梅方仅仅打了一场中卫，又因为柯钊受罚停赛4轮而不得不再次到边路“补漏”。随着马丁诺维奇被弃用，梅方一度成为中卫首选，在与上海东亚队比赛中，邱添一停赛，梅方与忻峰再度携手，紧接着与申鑫队比赛中移至边路，本轮忻峰又停赛，梅方和邱添一成为中卫线上最后的选择。至于左右边后卫位置，卓尔队方面人选较多，罗毅、周恒、刘尚坤等人均可出战。记者马万勇";
		
		String content = "<!DOCTYPE html>\n<html lang=\"en\">\n    <head>\n        <meta name=\"apple-mobile-web-app-capable\" content=\"yes\"></meta>\n        <meta http-equiv=\"Cache-Control\" name=\"no-store\"></meta>\n        <meta content=\"telephone=no\" name=\"format-detection\"></meta>\n        <meta content=\"email=no\" name=\"format-detection\"></meta>\n        <meta charset=\"utf-8\"></meta>\n        <meta name=\"viewport\" content=\"target-densitydpi=device-dpi,width=decive-width,initial-scale=1,maximum-scale=1\"></meta>\n        <title></title>\n        <style>\n            html,body {\n                width: 100%;\n                margin: 0;\n                padding: 0;\n            }\n            html, body,* {\n                font-family: \"微软雅黑\";\n                line-height: 24px;\n                font-size: 16px;\n            }\n            h1 {\n                font-size: 24px;\n            }\n            body {\n            }\n            .text-center {\n                text-align: center;\n            }\n            ul,li {\n                list-style: none;\n            }\n            ul {\n                margin: 0;\n                padding: 0 10px;\n            }\n            img { height: auto; width: auto\\9; width:100%; }\n        </style>\n    </head>\n<body> <p>英国威廉王子夫妇可能会打破王室传统，将大儿子乔治送到一所男女混校而非男校就读中学。</p><p>英国《每日邮报》15日报道，乔治目前在托马斯学校巴特西校区读学前班，有消息称威廉夫妇打算让乔治从托马斯学校毕业之后，进入一所男女混合寄宿中学，而非男校伊顿公学就读。</p><p>乔治的母亲凯特曾就读于混合寄宿制学校莫尔伯勒学院，父亲威廉和叔叔哈里均曾就读于著名男校伊顿公学。</p><p>托马斯学校是一所私立男女混校，学校2017年毕业生数据显示，更多毕业生选择进入男女混校就读中学，最受欢迎的去向是布赖顿公学。</p><p>布赖顿公学校长告诉《星期日泰晤士报》，如果王室成员作出这样的决定，那将是“开创性的”。</p><p>他补充道，这反映了“现代的父母越来越强烈地意识到将男孩和女孩分开接受教育是不自然的，不利于孩子做好进入社会的准备，因为如今在职场上，男女是平等合作的伙伴”。</p><p>今年3月，肯辛顿宫发表声明，证实乔治将就读托马斯学校巴特西校区，这令研究王室的专家感到惊讶。之前外界猜测乔治可能会进入韦瑟比学校就读，因为这是威廉和哈里上小学的地方。</p><p>9月，乔治在父亲威廉的陪伴下进入托马斯学校巴特西校区开始学前班生活。（金悦磊）</p> </body></html>";
		String content2 = "<!DOCTYPE html>\n<html lang=\"en\">\n    <head>\n        <meta name=\"apple-mobile-web-app-capable\" content=\"yes\"></meta>\n        <meta http-equiv=\"Cache-Control\" name=\"no-store\"></meta>\n        <meta content=\"telephone=no\" name=\"format-detection\"></meta>\n        <meta content=\"email=no\" name=\"format-detection\"></meta>\n        <meta charset=\"utf-8\"></meta>\n        <meta name=\"viewport\" content=\"target-densitydpi=device-dpi,width=decive-width,initial-scale=1,maximum-scale=1\"></meta>\n        <title></title>\n        <style>\n            html,body {\n                width: 100%;\n                margin: 0;\n                padding: 0;\n            }\n            html, body,* {\n                font-family: \"微软雅黑\";\n                line-height: 24px;\n                font-size: 16px;\n            }\n            h1 {\n                font-size: 24px;\n            }\n            body {\n            }\n            .text-center {\n                text-align: center;\n            }\n            ul,li {\n                list-style: none;\n            }\n            ul {\n                margin: 0;\n                padding: 0 10px;\n            }\n            img { height: auto; width: auto\\9; width:100%; }\n        </style>\n    </head>\n<body> <p>英国威廉王子夫妇可能会打破王室传统，将大儿子乔治送到一所男女混校而非男校就读中学。</p><p>英国《每日邮报》１５日报道，乔治目前在托马斯学校巴特西校区读学前班，有消息称威廉夫妇打算让乔治从托马斯学校毕业之后，进入一所男女混合寄宿中学，而非男校伊顿公学就读。</p><p>乔治的母亲凯特曾就读于混合寄宿制学校莫尔伯勒学院，父亲威廉和叔叔哈里均曾就读于著名男校伊顿公学。</p><p>托马斯学校是一所私立男女混校，学校２０１７年毕业生数据显示，更多毕业生选择进入男女混校就读中学，最受欢迎的去向是布赖顿公学。</p><p>布赖顿公学校长告诉《星期日泰晤士报》，如果王室成员作出这样的决定，那将是“开创性的”。</p><p>他补充道，这反映了“现代的父母越来越强烈地意识到将男孩和女孩分开接受教育是不自然的，不利于孩子做好进入社会的准备，因为如今在职场上，男女是平等合作的伙伴”。</p><p>今年３月，肯辛顿宫发表声明，证实乔治将就读托马斯学校巴特西校区，这令研究王室的专家感到惊讶。之前外界猜测乔治可能会进入韦瑟比学校就读，因为这是威廉和哈里上小学的地方。</p><p>９月，乔治在父亲威廉的陪伴下进入托马斯学校巴特西校区开始学前班生活。（金悦磊）【新华社微特稿】</p> </body></html>";

        String f1 = new FingerprintService().fingerprint(formatHtml(content));
        String f2 = new FingerprintService().fingerprint(formatHtml(content2));
        System.out.println(f1);
        System.out.println(f2);
        System.out.println(f1.equals(f2));
	}
	
	/**
	 * 清除内容标签数据，只保留文本
	 * @param content
	 * @return
	 */
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
