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
	 * 经过扩展的XStream流，可以生成微信格式的xml文档
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
	 * 解析服务器传过来的xml文件到map集合里面
	 */
	public static Map<String, String> parseXml(HttpServletRequest request) throws IOException, DocumentException {
		Map<String, String> map = new HashMap<String, String>();
		InputStream in = request.getInputStream();
		SAXReader read = new SAXReader();
		Document document = read.read(in);
		//得到根节点
		Element root = document.getRootElement();
		//获取所有节点
		List<Element> elements = root.elements();
		for( Element e : elements) {
			//将节点的名字和节点的值进行映射
			map.put(e.getName(), e.getText());
		}
		//关闭资源
		in.close();
		return map;
	}
	/**
	 * 将类转换成xml格式的字符串
	 */
	public static <T> String clazzToXml(T t) {
		xstream.alias("xml", t.getClass());
		return xstream.toXML(t);
	}
	/**
	 * 图文消息的转换
	 * @param news 需要转换的news对象
	 * @return
	 */
	public static String clazzToXml(NewsMessage news) {
		xstream.alias("xml", news.getClass());
		xstream.alias("item", Article.class);
		return xstream.toXML(news);
	}
	
	/**
	 * 图片接口
	 * @param image
	 * @return
	 */
	public static String clazzToXml(ImageMessage image) {
		xstream.alias("xml", image.getClass());
		xstream.aliasField("Image", image.getClass(), "image");
		return xstream.toXML(image);
	}
	
	/**
	 * 将语音接口的pojo类转换成为xml文件
	 * @param voice
	 */
	public static String clazzToXml(VoiceMessage voice) {
		xstream.alias("xml", voice.getClass());
		xstream.aliasField("Voice", voice.getClass(), "voice");
		xstream.aliasField("MediaId", Voice.class, "mediaId");
		return xstream.toXML(voice);
	}
	/**
	 * 回复视频消息的xml转换
	 * @param video
	 * @return
	 */
	public static String clazzToXml(VideoMessage video) {
		xstream.alias("xml", video.getClass());
		xstream.aliasField("Video", video.getClass(), "video");
		return xstream.toXML(video);
	}
	
	/**
	 * 将数据从BaseMessage类里面加载到它的子类 
	 */
	public static <T extends BaseMessage> void loadDataFromBase(BaseMessage base, T t) {
		t.setFromUserName(base.getFromUserName());
		t.setCreateTime(base.getCreateTime());
		t.setFuncFlag(base.getFuncFlag());
		t.setMsgType(base.getMsgType());
		t.setToUserName(base.getToUserName());
	}
	
	/**
	 * 判断这个对象是不是单纯的javaBean，如果是int或String这种类型传进来的话返回的应该是count
	 * 如果是一个类里面只含有String或者int类型，那么得到的值将是count+1
	 * @param obj 要判断的对象
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
