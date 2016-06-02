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
 * �����¼���service
 * @author wan
 */
public class EventService {
	
	/*
	 * ���ݿ�����ĵ���
	 */
	private static SignInDao signInDao = SignInDao.getInstance();
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * �����¼��ĺ���
	 * ֱ���������������Ӧ�������ظ�����Ϣ
	 * @param key �ж��¼���keyֵ
	 * @param base �����������潫һЩͨ�õ����ݿ������������緢���ĸ��û��������ĸ��û�
	 */
	public static String processEvent(String key, BaseMessage base) {
		String result = "";
		
		//ԤԼ�յ�
		if( key.equals(Constant.CLICK11_KEY)) {
			TextMessage clickText = new TextMessage();
			clickText.setContent("ԤԼʱ����ʱ��ֹ�����ע����֪ͨ��");
			MessageUtil.loadDataFromBase(base, clickText);
			clickText.setMsgType(Constant.RESP_TYPE_TEXT);
			result = MessageUtil.clazzToXml(clickText);
		} else if( key.equals(Constant.CLICK12_KEY)) {
			//����û���openId
			String openId = base.getToUserName();
			List<String> rl = signInDao.query(openId);
			String account = rl.get(0);
			//��ȡ��ǰʱ��
			Calendar calendar = Calendar.getInstance();
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			//��ʽ����ǰ��ʱ�䣬ʹ�����2016-04-17��ʽ
			String time = sdf.format(new Date());
			//����ǩ���ظ���Ϣ
			TextMessage signText = new TextMessage();
System.out.println("hour:\t" + hour);
			if( account.equals("")) {
				signText.setContent("<a href=\""+ WeixinUtil.prop.getProperty("xuesiji")+"/mobile/bind_s.html?openId="+ base.getToUserName() +"\">���Ұ�΢�ź�</a>");
			} else if( hour >= 23 || hour < 19) {
				signText.setContent("���ڲ���ǩ��ʱ�䣬���ڵ���21��00��23��00֮��ǩ���� ");
				//�ܹ�ǩ��
			} else {
				int code = signInDao.insert(account, rl.get(1), time, rl.get(2));
				//ǩ��ʧ�ܵ����
				if( code == -1) {
					signText.setContent("ǩ��ʧ�ܣ�\r\n(ԭ�򣺣�1��������Ѿ�ǩ����\r\n��2�����ڸ���Ա�Ĺ�Ͻ��Χ֮��!)");
					//ǩ���ɹ������
				} else {
					signText.setContent("ǩ���ɹ����㵱ǰ�ܻ���Ϊ:" + code + "!");
				}
			}
			MessageUtil.loadDataFromBase(base, signText);
			signText.setMsgType(Constant.RESP_TYPE_TEXT);
			result = MessageUtil.clazzToXml(signText);
			//�������
		} else if( key.equals(Constant.CLICK13_KEY)) {
			TextMessage clickText = new TextMessage();
			clickText.setContent("���ܿ�����...");
			MessageUtil.loadDataFromBase(base, clickText);
			clickText.setMsgType(Constant.RESP_TYPE_TEXT);
			result = MessageUtil.clazzToXml(clickText);
		} else if( key.equals(Constant.CLICK14_KEY)) {
			
		} else if( key.equals(Constant.CLICK15_KEY)) {
			
		} else if( key.equals(Constant.CLICK21_KEY)) {
			
		} else if( key.equals(Constant.CLICK22_KEY)) {
			TextMessage clickText = new TextMessage();
			clickText.setContent("Ͷ������: nchuxsj@163.com");
			MessageUtil.loadDataFromBase(base, clickText);
			clickText.setMsgType(Constant.RESP_TYPE_TEXT);
			result = MessageUtil.clazzToXml(clickText);
		} else if( key.equals(Constant.CLICK23_KEY)) {
			
		} else if( key.equals(Constant.CLICK24_KEY)) {
			NewsMessage news = new NewsMessage();
			Article article = new Article();
			article.setTitle("�ڲ�������������");
			article.setDescription("���µ��ϲ���ʱ��ʱ�꣬էů������ů�ٺ��ٺ��ٺ������컹�������¿��ò��õķ�����һҹ�䱻����䣬�峿���ǵ����");
			//ͼƬ��ַ
			article.setPicUrl("http://mmbiz.qpic.cn/mmbiz/o5AcfmswVB2KvRCFQskcAZ9DyaIvSsBUv12SicciafWicYmk79m4BQNecbMCvkl8vV2ZtiauMJruVruiaT4oaIktyvQ/0");
			//ԭ�ĵ�ַ
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
			clickText.setContent("�����ڴ���");
			MessageUtil.loadDataFromBase(base, clickText);
			clickText.setMsgType(Constant.RESP_TYPE_TEXT);
			result = MessageUtil.clazzToXml(clickText);
			//view
		} else if( key.equals(Constant.CLICK34_KEY)) {
			
		} else if( key.equals(Constant.CLICK35_KEY)) {
			TextMessage clickText = new TextMessage();
			clickText.setContent("�����ڴ���");
			MessageUtil.loadDataFromBase(base, clickText);
			clickText.setMsgType(Constant.RESP_TYPE_TEXT);
			result = MessageUtil.clazzToXml(clickText);
		} 
		return result;
	}
	
	
}
