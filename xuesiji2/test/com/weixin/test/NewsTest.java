package com.weixin.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.weixin.model.Article;
import com.weixin.model.BaseMessage;
import com.weixin.model.NewsMessage;
import com.weixin.util.Constant;
import com.weixin.util.MessageUtil;

public class NewsTest {
	
	@Test
	public void testNews() {
		BaseMessage base= new BaseMessage();
		base.setCreateTime(new Date().getTime());
		base.setFromUserName("111");
		base.setFuncFlag(0);
		base.setMsgType("news");
		base.setToUserName("321");
		NewsMessage news = new NewsMessage();
		MessageUtil.loadDataFromBase(base, news);
		news.setMsgType(Constant.RESP_TYPE_NEWS);
		Article  article = new Article();
		article.setTitle("学思集绑定流程");
		article.setUrl("http://mp.weixin.qq.com/s?__biz=MzAwNjgzMDY5MA==&mid=518071694&idx=1&sn=5b56d3b10ed33ea20860d4dc1c1137bb#rd");
		news.setArticleCount(1);
		List<Article> list = new ArrayList<Article>();
		list.add(article);
		news.setArticles(list);
		System.out.println(MessageUtil.clazzToXml(news));
	}

}
