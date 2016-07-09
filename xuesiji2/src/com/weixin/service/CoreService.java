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
	 * 业务核心类，处理请求
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) throws IOException, DocumentException {
		//将这个request解析成为Map集合
		//需要回复的字符串
		String respStr = "";
		Map<String, String> map = MessageUtil.parseXml(request);
		String toUserName = map.get("ToUserName");
		String fromUserName = map.get("FromUserName");
		String msgType = map.get("MsgType");
		String msgId = map.get("MsgId");
		//创建需要返回的类
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
			if( content.contains("校历")) {
				//回复图片消息
				ImageMessage image = new ImageMessage();
				MessageUtil.loadDataFromBase(base, image);
				image.setMsgType(Constant.REQ_TYPE_IMAGE);
				Image img = new Image();
				img.setMediaId("rDEC8s4Ng8EKJrCx9SqUoeRVBW06GJaL19QuG7uvdeM");
				image.setImage(img);
				respStr = MessageUtil.clazzToXml(image);
			} else if( content.contains("队歌")) {
				text.setContent("<a href=\"http://mp.weixin.qq.com/s?__biz=MzAwNjgzMDY5MA==&mid=416737979&idx=1&sn=518ee3b71ff183f2edd4fb0828c2aab8&scene=18#wechat_redirect\">教官队队歌</a>");
				respStr = MessageUtil.clazzToXml(text);
			} else if( content.contains("历史")) {
				text.setContent("<a href=\"http://mp.weixin.qq.com/mp/getmasssendmsg?__biz=MzAwNjgzMDY5MA==#wechat_webview_type=1&wechat_redirect\">历史消息</a>");
				respStr = MessageUtil.clazzToXml(text);
			} else if( content.contains("礼物") || content.contains("投票")) {
				text.setContent("<a href=\"http://mp.weixin.qq.com/s?__biz=MzAwNjgzMDY5MA==&mid=2665554574&idx=1&sn=d6358ea99c8f3b21a239d1c5f6e3d7f0&scene=0#wechat_redirect\">毕业礼物投票</a>");
				respStr = MessageUtil.clazzToXml(text);
			} else if( content.contains("成绩") || content.contains("查询")) {
				text.setContent("<a href=\"http://search1.jxedu.gov.cn/searchProject/lookComputerSearchVm.action\">计算机二级成绩查询 </a>");
				respStr = MessageUtil.clazzToXml(text);
			} else if(content.contains("绑定") || content.contains("签到")) {
				NewsMessage news = new NewsMessage();
				MessageUtil.loadDataFromBase(base, news);
				news.setMsgType(Constant.RESP_TYPE_NEWS);
				Article  article = new Article();
				article.setTitle("《微信身份绑定流程》");
				article.setDescription("以后用微信就能点到请假了!");
				article.setPicUrl("http://mmbiz.qpic.cn/mmbiz/aaSJ69FohNLU9V1g8yaibZnW7dujsLoYRrLJq3VibthVvCrUkoGpR6aVaxNkntL77VpxyTWmDkdgvT0Pk0y2IPEw/0?wx_fmt=jpeg");
				article.setUrl("http://mp.weixin.qq.com/s?__biz=MzAwNjgzMDY5MA==&mid=518071694&idx=1&sn=5b56d3b10ed33ea20860d4dc1c1137bb#rd");
				news.setArticleCount(1);
				List<Article> list = new ArrayList<Article>();
				list.add(article);
				news.setArticles(list);
				respStr = MessageUtil.clazzToXml(news);
			} else {
				text.setContent("您的留言我们已经收到!");
				respStr = MessageUtil.clazzToXml(text);
			}
		} else if(msgType.equals(Constant.REQ_TYPE_IMAGE)) {
			
		} else if(msgType.equals(Constant.REQ_TYPE_VOICE)) {
			
		} else if(msgType.equals(Constant.REQ_TYPE_LINK)) {
			
		} else if(msgType.equals(Constant.REQ_TYPE_LOCATION)) {
			
		} else if(msgType.equals(Constant.REQ_TYPE_VIDEO)) {
			
		} else if(msgType.equals(Constant.REQ_TYPE_SHORTVIDEO)) {
			
			//事件消息
		} else if(msgType.equals(Constant.REQ_TYPE_EVENT)) {
			//点击事件的判断
			String eventType = map.get("Event");
			if(eventType.equals(Constant.EVENT_TYPE_SUBSCRIBE)) {
				TextMessage subText = new TextMessage();
				subText.setContent("你好，欢迎关注学思集/:rose\r\n" +
					"坚持原创，热爱青春，我们致力于打造一个大学生思想汇集的圈子。\r\n"+
					"<a href=\"http://mp.weixin.qq.com/mp/getmasssendmsg?__biz=MzAwNjgzMDY5MA"
					+ "==#wechat_webview_type=1&wechat_redirect\">查看历史消息</a>\r\n"+
					"联系我们：nchuxsj@163.com");
				MessageUtil.loadDataFromBase(base, subText);
				subText.setMsgType(Constant.RESP_TYPE_TEXT);
				respStr = MessageUtil.clazzToXml(subText);
			} else if(eventType.equals(Constant.EVENT_TYPE_UNSUBSCRIBE)) {
				TextMessage unSubText = new TextMessage();
				unSubText.setContent("bye~~期待您的下一次关注");
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
