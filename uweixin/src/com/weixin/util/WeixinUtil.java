package com.weixin.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import net.sf.json.JSONObject;

/**
 * ����΢�ŵĹ�����
 * @author wan
 */
public class WeixinUtil {
	
	/**
	 * �������Ե���
	 */
	public static Properties prop = new Properties();
	static {
		String url = WeixinUtil.class.getResource("").getPath().replaceAll("%20", " ");  
        String path = url.substring(0, url.indexOf("WEB-INF")) + "WEB-INF/classes/config.properties"; 
		try {
			prop.load(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
