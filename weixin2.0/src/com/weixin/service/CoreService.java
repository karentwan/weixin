package com.weixin.service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;

import com.weixin.model.BaseMessage;
import com.weixin.model.TextMessage;
import com.weixin.model.Voice;
import com.weixin.model.VoiceMessage;
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
			if( content.contains("预约") || content.contains("空调")) {
				text.setContent("预约时间暂时截止，请关注后续通知！");
				respStr = MessageUtil.clazzToXml(text);
			} else if( content.contains("队歌")) {
				Voice v = new Voice();
				v.setMediaId("dRudtQ38aiU2CNrUxQjlOxSQwm8Zhz8Oapj0Dwy6Heg");
				v.setMediaId("b72tZL5XZk188O4tuhP_ZUvcLrGoKmKydSyjJjeOv76nsRfLMO7vcuekHccSzYSD");
				v.setMediaId("gTsWRoZdddwN-IGTL8Mt0Sq3Npa2kONei02WdJcOG1w");
				VoiceMessage voice = new VoiceMessage();
				MessageUtil.loadDataFromBase(base, voice);
				voice.setMsgType(Constant.REQ_TYPE_VOICE);
				voice.setVoice(v);
				respStr = MessageUtil.clazzToXml(voice);
System.out.println("respStr:" + respStr);
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
				subText.setContent("你好,欢迎关注nchuxuesiji!!");
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
