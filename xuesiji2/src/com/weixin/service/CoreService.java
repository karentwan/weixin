package com.weixin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.dom4j.DocumentException;
import com.weixin.model.Article;
import com.weixin.model.BaseMessage;
import com.weixin.model.Image;
import com.weixin.model.ImageMessage;
import com.weixin.model.NewsMessage;
import com.weixin.model.TextMessage;
import com.weixin.util.Constant;
import com.weixin.util.MessageUtil;

public class CoreService {
	
	/**
	 * ҵ������࣬��������
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) throws IOException, DocumentException {
		//�����request������ΪMap����
		//��Ҫ�ظ����ַ���
		String respStr = "";
		Map<String, String> map = MessageUtil.parseXml(request);
		String toUserName = map.get("ToUserName");
		String fromUserName = map.get("FromUserName");
		String msgType = map.get("MsgType");
		String msgId = map.get("MsgId");
		//������Ҫ���ص���
		BaseMessage base = new BaseMessage();
		base.setToUserName(fromUserName);
		base.setFromUserName(toUserName);
		base.setCreateTime(new Date().getTime());
		base.setMsgType(msgType);
		base.setFuncFlag(0);
		if(msgType.equals(Constant.RESP_TYPE_TEXT)) {
			String content = map.get("Content");
			TextMessage text = new TextMessage();
			MessageUtil.loadDataFromBase(base, text);
			text.setMsgType(Constant.RESP_TYPE_TEXT);
			if( content.contains("У��")) {
				//�ظ�ͼƬ��Ϣ
				ImageMessage image = new ImageMessage();
				MessageUtil.loadDataFromBase(base, image);
				image.setMsgType(Constant.REQ_TYPE_IMAGE);
				Image img = new Image();
				img.setMediaId("rDEC8s4Ng8EKJrCx9SqUoeRVBW06GJaL19QuG7uvdeM");
				image.setImage(img);
				respStr = MessageUtil.clazzToXml(image);
			} else if( content.contains("�Ӹ�")) {
				text.setContent("<a href=\"http://mp.weixin.qq.com/s?__biz=MzAwNjgzMDY5MA==&mid=416737979&idx=1&sn=518ee3b71ff183f2edd4fb0828c2aab8&scene=18#wechat_redirect\">�̹ٶӶӸ�</a>");
				respStr = MessageUtil.clazzToXml(text);
			} else if( content.contains("��ʷ")) {
				text.setContent("<a href=\"http://mp.weixin.qq.com/mp/getmasssendmsg?__biz=MzAwNjgzMDY5MA==#wechat_webview_type=1&wechat_redirect\">��ʷ��Ϣ</a>");
				respStr = MessageUtil.clazzToXml(text);
			} else if( content.contains("����") || content.contains("ͶƱ")) {
				text.setContent("<a href=\"http://mp.weixin.qq.com/s?__biz=MzAwNjgzMDY5MA==&mid=2665554574&idx=1&sn=d6358ea99c8f3b21a239d1c5f6e3d7f0&scene=0#wechat_redirect\">��ҵ����ͶƱ</a>");
				respStr = MessageUtil.clazzToXml(text);
			} else if( content.contains("�ɼ�") || content.contains("��ѯ")) {
				text.setContent("<a href=\"http://search1.jxedu.gov.cn/searchProject/lookComputerSearchVm.action\">����������ɼ���ѯ </a>");
				respStr = MessageUtil.clazzToXml(text);
			} else if(content.contains("��") || content.contains("ǩ��")) {
				NewsMessage news = new NewsMessage();
				MessageUtil.loadDataFromBase(base, news);
				news.setMsgType(Constant.RESP_TYPE_NEWS);
				Article  article = new Article();
				article.setTitle("��΢����ݰ����̡�");
				article.setDescription("�Ժ���΢�ž��ܵ㵽�����!");
				article.setPicUrl("http://mmbiz.qpic.cn/mmbiz/aaSJ69FohNLU9V1g8yaibZnW7dujsLoYRrLJq3VibthVvCrUkoGpR6aVaxNkntL77VpxyTWmDkdgvT0Pk0y2IPEw/0?wx_fmt=jpeg");
				article.setUrl("http://mp.weixin.qq.com/s?__biz=MzAwNjgzMDY5MA==&mid=518071694&idx=1&sn=5b56d3b10ed33ea20860d4dc1c1137bb#rd");
				news.setArticleCount(1);
				List<Article> list = new ArrayList<Article>();
				list.add(article);
				news.setArticles(list);
				respStr = MessageUtil.clazzToXml(news);
			} else {
				text.setContent("�������������Ѿ��յ�!");
				respStr = MessageUtil.clazzToXml(text);
			}
		} else if(msgType.equals(Constant.REQ_TYPE_IMAGE)) {
			
		} else if(msgType.equals(Constant.REQ_TYPE_VOICE)) {
			
		} else if(msgType.equals(Constant.REQ_TYPE_LINK)) {
			
		} else if(msgType.equals(Constant.REQ_TYPE_LOCATION)) {
			
		} else if(msgType.equals(Constant.REQ_TYPE_VIDEO)) {
			
		} else if(msgType.equals(Constant.REQ_TYPE_SHORTVIDEO)) {
			
			//�¼���Ϣ
		} else if(msgType.equals(Constant.REQ_TYPE_EVENT)) {
			//����¼����ж�
			String eventType = map.get("Event");
			if(eventType.equals(Constant.EVENT_TYPE_SUBSCRIBE)) {
				TextMessage subText = new TextMessage();
				subText.setContent("��ã���ӭ��עѧ˼��/:rose\r\n" +
					"���ԭ�����Ȱ��ഺ�����������ڴ���һ����ѧ��˼��㼯��Ȧ�ӡ�\r\n"+
					"<a href=\"http://mp.weixin.qq.com/mp/getmasssendmsg?__biz=MzAwNjgzMDY5MA"
					+ "==#wechat_webview_type=1&wechat_redirect\">�鿴��ʷ��Ϣ</a>\r\n"+
					"��ϵ���ǣ�nchuxsj@163.com");
				MessageUtil.loadDataFromBase(base, subText);
				subText.setMsgType(Constant.RESP_TYPE_TEXT);
				respStr = MessageUtil.clazzToXml(subText);
			} else if(eventType.equals(Constant.EVENT_TYPE_UNSUBSCRIBE)) {
				TextMessage unSubText = new TextMessage();
				unSubText.setContent("bye~~�ڴ�������һ�ι�ע");
				MessageUtil.loadDataFromBase(base, unSubText);
				unSubText.setMsgType(Constant.RESP_TYPE_TEXT);
				respStr = MessageUtil.clazzToXml(unSubText);
			} else if(eventType.equals(Constant.EVENT_TYPE_VIEW)) {
				
			} else if(eventType.equals(Constant.EVENT_TYPE_CLICK)) {
				String eventKey = map.get("EventKey");
				respStr = EventService.processEvent(eventKey, base);
			} 
		}
		return respStr;
	}
}
