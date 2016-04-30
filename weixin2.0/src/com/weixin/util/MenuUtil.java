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
 * 创建微信菜单的类
 * @author wan
 */
public class MenuUtil {
	/*
	 * 获取acess_token
	 */
	public static String ACCESS_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET"; 
	/*
	 * 以post方式向这个api中传送菜单数据
	 */
	public static String CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	
	/**
	 * 连接微信服务器，创建菜单
	 * @param requestUrl 微信api请求地址
	 * @param requestMethod 请求方法
	 * 	GET：代表获取创建菜单的ACCESS_TOKEN
	 * 	POST:代表向微信服务器传送菜单数据
	 * @param outputStr 要向微信服务器传送的菜单数据
	 * @return 有来自微信服务器返回的json数据格式封装成的JSONObject对象
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		TrustManager[] tm = {new MyX509TrustManager()};
		SSLContext sslContext;
		try {
			//创建证书管理
			sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			//打开链接
			URL url = new URL(requestUrl);
			HttpsURLConnection httpConn = (HttpsURLConnection)url.openConnection();
			httpConn.setSSLSocketFactory(ssf);
			//确认打开输入输出
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			httpConn.setUseCaches(false);
			//设置请求方式
			httpConn.setRequestMethod(requestMethod);
			if("GET".equalsIgnoreCase(requestMethod)) {
				httpConn.connect();
			} 
			//如果outputStr不为空，那么这个说明有输出信息
			if( outputStr != null) {
				OutputStream out = httpConn.getOutputStream();
				out.write(outputStr.getBytes("UTF-8"));
				out.close();
			}
			//不管是那种请求方式，都会有信息从那边过来
			InputStream in = httpConn.getInputStream();
			InputStreamReader reader = new InputStreamReader(in, "utf-8");
			BufferedReader bufferReader = new BufferedReader(reader);
			//读取信息
			String str = null;
			while( (str = bufferReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferReader.close();
			reader.close();
			in.close();
			httpConn.disconnect();
			//将json数据转换成为一个JSONObject类
			jsonObject = JSONObject.fromObject(buffer.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return jsonObject;
		
	}
	/**
	 * 获取acess_token
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
	 * 真正的创建菜单方法
	 * @param accessToken 从微信服务器获取到的access_token
	 * @param menuStr 创建菜单所需要的菜单数据
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
