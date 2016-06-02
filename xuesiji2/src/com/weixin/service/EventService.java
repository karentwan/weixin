package com.weixin.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.weixin.dao.SignInDao;
import com.weixin.model.Article;
import com.weixin.model.BaseMessage;
import com.weixin.model.NewsMessage;
import com.weixin.model.TextMessage;
import com.weixin.util.Constant;
import com.weixin.util.MessageUtil;
import com.weixin.util.WeixinUtil;

/**
 * 处理事件的service
 * @author wan
 */
public class EventService {
	
	/*
	 * 数据库操作的到层
	 */
	private static SignInDao signInDao = SignInDao.getInstance();
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 处理事件的函数
	 * 直接由这个函数产生应该怎样回复的信息
	 * @param key 判断事件的key值
	 * @param base 会从这个类里面将一些通用的内容拷贝出来，例如发向哪个用户，来自哪个用户
	 */
	public static String processEvent(String key, BaseMessage base) {
		String result = "";
		
		//预约空调
		if( key.equals(Constant.CLICK11_KEY)) {
			TextMessage clickText = new TextMessage();
			clickText.setContent("预约时间暂时截止，请关注后续通知！");
			MessageUtil.loadDataFromBase(base, clickText);
			clickText.setMsgType(Constant.RESP_TYPE_TEXT);
			result = MessageUtil.clazzToXml(clickText);
		} else if( key.equals(Constant.CLICK12_KEY)) {
			//获得用户的openId
			String openId = base.getToUserName();
			List<String> rl = signInDao.query(openId);
			String account = rl.get(0);
			//获取当前时间
			Calendar calendar = Calendar.getInstance();
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			//格式化当前的时间，使它变成2016-04-17格式
			String time = sdf.format(new Date());
			//创建签到回复信息
			TextMessage signText = new TextMessage();
System.out.println("hour:\t" + hour);
			if( account.equals("")) {
				signText.setContent("<a href=\""+ WeixinUtil.prop.getProperty("xuesiji")+"/mobile/bind_s.html?openId="+ base.getToUserName() +"\">点我绑定微信号</a>");
			} else if( hour >= 23 || hour < 19) {
				signText.setContent("现在不是签到时间，请在当晚21：00到23：00之间签到！ ");
				//能够签到
			} else {
				int code = signInDao.insert(account, rl.get(1), time, rl.get(2));
				//签到失败的情况
				if( code == -1) {
					signText.setContent("签到失败！\r\n(原因：（1）你可能已经签过了\r\n（2）不在辅导员的管辖范围之内!)");
					//签到成功的情况
				} else {
					signText.setContent("签到成功，你当前总积分为:" + code + "!");
				}
			}
			MessageUtil.loadDataFromBase(base, signText);
			signText.setMsgType(Constant.RESP_TYPE_TEXT);
			result = MessageUtil.clazzToXml(signText);
			//今晚请假
		} else if( key.equals(Constant.CLICK13_KEY)) {
			TextMessage clickText = new TextMessage();
			clickText.setContent("功能开发中...");
			MessageUtil.loadDataFromBase(base, clickText);
			clickText.setMsgType(Constant.RESP_TYPE_TEXT);
			result = MessageUtil.clazzToXml(clickText);
		} else if( key.equals(Constant.CLICK14_KEY)) {
			
		} else if( key.equals(Constant.CLICK15_KEY)) {
			
		} else if( key.equals(Constant.CLICK21_KEY)) {
			
		} else if( key.equals(Constant.CLICK22_KEY)) {
			TextMessage clickText = new TextMessage();
			clickText.setContent("投稿邮箱: nchuxsj@163.com");
			MessageUtil.loadDataFromBase(base, clickText);
			clickText.setMsgType(Constant.RESP_TYPE_TEXT);
			result = MessageUtil.clazzToXml(clickText);
		} else if( key.equals(Constant.CLICK23_KEY)) {
			
		} else if( key.equals(Constant.CLICK24_KEY)) {
			NewsMessage news = new NewsMessage();
			Article article = new Article();
			article.setTitle("在昌航听花的声音");
			article.setDescription("三月的南昌，时晴时雨，乍暖还寒又暖再寒再寒再寒。昨天还在阳光下开得灿烂的繁花，一夜间被雨打落，清晨零星的落红");
			//图片地址
			article.setPicUrl("http://mmbiz.qpic.cn/mmbiz/o5AcfmswVB2KvRCFQskcAZ9DyaIvSsBUv12SicciafWicYmk79m4BQNecbMCvkl8vV2ZtiauMJruVruiaT4oaIktyvQ/0");
			//原文地址
			article.setUrl("http://mp.weixin.qq.com/s?__biz=MzAwNjgzMDY5MA==&mid=416064622&idx=1&sn=593c6330c0b7d040a967b23c6d4d48a3&scene=18#wechat_redirect");
			List<Article> articleList = new ArrayList<Article>();
			articleList.add(article);
			news.setArticleCount(articleList.size());
			news.setArticles(articleList);
			MessageUtil.loadDataFromBase(base, news);
			news.setMsgType("news");
			result = MessageUtil.clazzToXml(news);
		} else if(key.equals(Constant.CLICK25_KEY) ) {
			
		} else if (key.equals(Constant.CLICK31_KEY)) {
			
		} else if( key.equals(Constant.CLICK32_KEY)) {
			
		} else if( key.equals(Constant.CLICK33_KEY)) {
			TextMessage clickText = new TextMessage();
			clickText.setContent("敬请期待！");
			MessageUtil.loadDataFromBase(base, clickText);
			clickText.setMsgType(Constant.RESP_TYPE_TEXT);
			result = MessageUtil.clazzToXml(clickText);
			//view
		} else if( key.equals(Constant.CLICK34_KEY)) {
			
		} else if( key.equals(Constant.CLICK35_KEY)) {
			TextMessage clickText = new TextMessage();
			clickText.setContent("敬请期待！");
			MessageUtil.loadDataFromBase(base, clickText);
			clickText.setMsgType(Constant.RESP_TYPE_TEXT);
			result = MessageUtil.clazzToXml(clickText);
		} 
		return result;
	}
	
	
}
