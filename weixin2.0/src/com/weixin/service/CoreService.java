package com.weixin.service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import com.weixin.model.response.BaseMessage;
import com.weixin.model.response.TextMessage;
import com.weixin.util.Constant;
import com.weixin.util.MessageUtil;

public class CoreService {
	
	private static final Logger log = Logger.getLogger("debug");
	
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
			if( content.contains("ԤԼ") || content.contains("�յ�")) {
				text.setContent("ԤԼʱ����ʱ��ֹ�����ע����֪ͨ��");
				respStr = MessageUtil.clazzToXml(text);
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
				subText.setContent("���,��ӭ��עnchuxuesiji!!");
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
