package com.weixin.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import net.sf.json.JSONObject;
import com.weixin.pojo.AccessToken;

/**
 * ����΢�Ų˵�����
 * @author wan
 */
public class MenuUtil {
	/*
	 * ��ȡacess_token
	 */
	public static String ACCESS_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET"; 
	/*
	 * ��post��ʽ�����api�д��Ͳ˵�����
	 */
	public static String CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	
	/**
	 * ����΢�ŷ������������˵�
	 * @param requestUrl ΢��api�����ַ
	 * @param requestMethod ���󷽷�
	 * 	GET�������ȡ�����˵���ACCESS_TOKEN
	 * 	POST:������΢�ŷ��������Ͳ˵�����
	 * @param outputStr Ҫ��΢�ŷ��������͵Ĳ˵�����
	 * @return ������΢�ŷ��������ص�json���ݸ�ʽ��װ�ɵ�JSONObject����
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		TrustManager[] tm = {new MyX509TrustManager()};
		SSLContext sslContext;
		try {
			//����֤�����
			sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			//������
			URL url = new URL(requestUrl);
			HttpsURLConnection httpConn = (HttpsURLConnection)url.openConnection();
			httpConn.setSSLSocketFactory(ssf);
			//ȷ�ϴ��������
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			httpConn.setUseCaches(false);
			//��������ʽ
			httpConn.setRequestMethod(requestMethod);
			if("GET".equalsIgnoreCase(requestMethod)) {
				httpConn.connect();
			} 
			//���outputStr��Ϊ�գ���ô���˵���������Ϣ
			if( outputStr != null) {
				OutputStream out = httpConn.getOutputStream();
				out.write(outputStr.getBytes("UTF-8"));
				out.close();
			}
			//��������������ʽ����������Ϣ���Ǳ߹���
			InputStream in = httpConn.getInputStream();
			InputStreamReader reader = new InputStreamReader(in, "utf-8");
			BufferedReader bufferReader = new BufferedReader(reader);
			//��ȡ��Ϣ
			String str = null;
			while( (str = bufferReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferReader.close();
			reader.close();
			in.close();
			httpConn.disconnect();
			//��json����ת����Ϊһ��JSONObject��
			jsonObject = JSONObject.fromObject(buffer.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return jsonObject;
		
	}
	/**
	 * ��ȡacess_token
	 */
	public static AccessToken getAcsessToken(String appid, String appsecret) {
		AccessToken access = new AccessToken();
		String url = ACCESS_URL.replace("APPID", appid).replace("APPSECRET", appsecret);
		JSONObject jsonObject = httpRequest(url, "GET", null);
		if( jsonObject != null) {
			access.setToken(jsonObject.getString("access_token"));
			access.setExpiresIn(jsonObject.getInt("expires_in"));
		}
		return access;
	}
	/**
	 * �����Ĵ����˵�����
	 * @param accessToken ��΢�ŷ�������ȡ����access_token
	 * @param menuStr �����˵�����Ҫ�Ĳ˵�����
	 */
	public static int createMenu(String accessToken, String menuStr ) {
		int result = 0;
		String url = CREATE_URL.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = httpRequest(url, "POST", menuStr);
		if(null != jsonObject) {
			if(0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
			}
		}
		return result;
	}
}
