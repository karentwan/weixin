package com.weixin.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.weixin.model.response.Article;
import com.weixin.model.response.BaseMessage;
import com.weixin.model.response.NewsMessage;

public class MessageUtil {

	/**
	 * ������չ��XStream������������΢�Ÿ�ʽ��xml�ĵ�
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				boolean cdata = true;
				
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}
				
				public void writeText(QuickWriter writer, String text) {
					if(cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});
	
	/**
	 * ������������������xml�ļ���map��������
	 */
	public static Map<String, String> parseXml(HttpServletRequest request) throws IOException, DocumentException {
		Map<String, String> map = new HashMap<String, String>();
		InputStream in = request.getInputStream();
		SAXReader read = new SAXReader();
		Document document = read.read(in);
		//�õ����ڵ�
		Element root = document.getRootElement();
		//��ȡ���нڵ�
		List<Element> elements = root.elements();
		for( Element e : elements) {
			//���ڵ�����ֺͽڵ��ֵ����ӳ��
			map.put(e.getName(), e.getText());
		}
		//�ر���Դ
		in.close();
		return map;
	}
	/**
	 * ����ת����xml��ʽ���ַ���
	 */
	public static <T> String clazzToXml(T t) {
		xstream.alias("xml", t.getClass());
		return xstream.toXML(t);
	}
	/**
	 * ͼ����Ϣ��ת��
	 * @param news ��Ҫת����news����
	 * @return
	 */
	public static String clazzToXml(NewsMessage news) {
		xstream.alias("xml", news.getClass());
		xstream.alias("item", Article.class);
		return xstream.toXML(news);
	}
	
	/**
	 * �����ݴ�BaseMessage��������ص��������� 
	 */
	public static <T extends BaseMessage> void loadDataFromBase(BaseMessage base, T t) {
		t.setFromUserName(base.getFromUserName());
		t.setCreateTime(base.getCreateTime());
		t.setFuncFlag(base.getFuncFlag());
		t.setMsgType(base.getMsgType());
		t.setToUserName(base.getToUserName());
	}
	
	/**
	 * �ж���������ǲ��ǵ�����javaBean�������int��String�������ʹ������Ļ����ص�Ӧ����count
	 * �����һ��������ֻ����String����int���ͣ���ô�õ���ֵ����count+1
	 * @param obj Ҫ�жϵĶ���
	 * @param count
	 * @return 
	 */
	private static int isCell(Object obj, int count) throws IllegalArgumentException, IllegalAccessException {
		Class clazz = obj.getClass();
		if(clazz == Integer.class || clazz == String.class) {
			return count;
		}
		Field[] fields = clazz.getDeclaredFields();
		int t1 = count, t2 = 0;
		for(int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			Object o = fields[i].get(obj);
			Class clazz1 = o.getClass();
			if(clazz1 == Integer.class || clazz1 == String.class) {
				continue;
			}
			t2 = isCell(o, count + 1);
			if( t1 < t2) {
				t1 = t2;
			}
		}
		return t1;
	}
	
}
