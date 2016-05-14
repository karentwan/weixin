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
import com.weixin.model.Article;
import com.weixin.model.BaseMessage;
import com.weixin.model.ImageMessage;
import com.weixin.model.NewsMessage;
import com.weixin.model.VideoMessage;
import com.weixin.model.Voice;
import com.weixin.model.VoiceMessage;

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
	 * ͼƬ�ӿ�
	 * @param image
	 * @return
	 */
	public static String clazzToXml(ImageMessage image) {
		xstream.alias("xml", image.getClass());
		xstream.aliasField("Image", image.getClass(), "image");
		return xstream.toXML(image);
	}
	
	/**
	 * �������ӿڵ�pojo��ת����Ϊxml�ļ�
	 * @param voice
	 */
	public static String clazzToXml(VoiceMessage voice) {
		xstream.alias("xml", voice.getClass());
		xstream.aliasField("Voice", voice.getClass(), "voice");
		xstream.aliasField("MediaId", Voice.class, "mediaId");
		return xstream.toXML(voice);
	}
	/**
	 * �ظ���Ƶ��Ϣ��xmlת��
	 * @param video
	 * @return
	 */
	public static String clazzToXml(VideoMessage video) {
		xstream.alias("xml", video.getClass());
		xstream.aliasField("Video", video.getClass(), "video");
		return xstream.toXML(video);
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
