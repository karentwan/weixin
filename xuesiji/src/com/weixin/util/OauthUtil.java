package com.weixin.util;

import java.io.UnsupportedEncodingException;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.weixin.pojo.UserInfo;
import com.weixin.pojo.WeixinOauth2Token;

/**
 * ����oauth2��֤�Ĺ�����
 * @author wan
 */
public class OauthUtil {
	
	/*
	 * Ҫ����ȥ������
	 */
	private static final String OAUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?"
			+ "appid=wx3e0507081663b5ee&redirect_uri=http%3A%2F%2F070411.com%2Fweixin%2FoauthServlet&response_type=code&"
			+ "scope=snsapi_userinfo&state=STATE#wechat_redirect";
	
	/**
	 * ��ȡ��ҳƾ֤
	 * @return
	 */
	public static WeixinOauth2Token getOauth2AccessToken(String appId, String appSecret, String code) {
		WeixinOauth2Token wot = null;
		//ƴװ���ʵ�ַ
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token"
				+ "?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		//https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
		
		requestUrl = requestUrl.replace("APPID", appId);
		requestUrl = requestUrl.replace("SECRET", appSecret.trim());
		requestUrl = requestUrl.replace("CODE", code.trim());
		//��ȡ��ҳ����Ȩ
		JSONObject jsonObject = MenuUtil.httpRequest(requestUrl, "GET", null);
System.out.println("requestUrl:" + requestUrl);
		if( null != jsonObject) {
			try {
				wot = new WeixinOauth2Token();
				//����������json���ݷ�װ��pojo������
				wot.setAccessToken(jsonObject.getString("access_token"));
				wot.setExpiresIn(jsonObject.getInt("expires_in"));
				wot.setRefreshToken(jsonObject.getString("refresh_token"));
				wot.setOpenId(jsonObject.getString("openid"));
				wot.setScope(jsonObject.getString("scope"));
			} catch(Exception e) {
				wot = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
System.out.println("errorCode:" + errorCode + "errorMsg:" + errorMsg );
			}
			
		}
		return wot;
	}
	
	public static WeixinOauth2Token refreshOauth2AccessToken(String appId, String refreshToken) {
		WeixinOauth2Token wot = null;
		//ƴ�������ַ
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?"
				+ "appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
		requestUrl = requestUrl.replace("APPID", appId);
		requestUrl = requestUrl.replace("REFRESH_TOKEN", refreshToken);
		
		JSONObject jsonObject = MenuUtil.httpRequest(requestUrl, "GET", null);
		
		if( null != jsonObject) {
			
			try {
				wot = new WeixinOauth2Token();
				//����������json���ݷ�װ��pojo������
				wot.setAccessToken(jsonObject.getString("access_token"));
				wot.setExpiresIn(jsonObject.getInt("expires_in"));
				wot.setRefreshToken(jsonObject.getString("refresh_token"));
				wot.setOpenId(jsonObject.getString("openid"));
				wot.setScope(jsonObject.getString("scope"));
				
			} catch(Exception e) {
				wot = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
System.out.println("errorCode:" + errorCode + "errorMsg:" + errorMsg );
			}
			
		}
		
		
		return wot;
	}
	/**
	 * ��ȡ�û�����Ϣ
	 * @param accessToken ��ҳ��Ȩ��access_token
	 * @param openId
	 * @return
	 */
	public static UserInfo getUserInfo(String accessToken, String openId) {
		UserInfo ui = null;
		//ƴװ�����ַ
		String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		requestUrl = requestUrl.replace("OPENID", openId);
		
		JSONObject jsonObject = MenuUtil.httpRequest(requestUrl, "GET", null);
		if( jsonObject != null) {
			try {
				ui = new UserInfo();
				ui.setOpenId(jsonObject.getString("openid"));
				ui.setNickName(jsonObject.getString("nickname"));
				ui.setSex(jsonObject.getInt("sex"));
				ui.setCountry(jsonObject.getString("country"));
				ui.setProvince(jsonObject.getString("province"));
				ui.setCity(jsonObject.getString("city"));
				ui.setHeadImgUrl(jsonObject.getString("headimgurl"));
				ui.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"), List.class));
			
			} catch(Exception e) {
				ui = null;
				int errorCode = jsonObject.getInt("errCode");
				String errorMsg = jsonObject.getString("errmsg");
System.out.println("errorCode:" + errorCode + "errorMsg:" + errorMsg );
			}
			
		}
		return ui;
	}	
	/**
	 * ��url��ַ���б���
	 * @param source
	 * @return
	 */
	public static String urlEncodeUTF8(String source) {
		String result = null;
		try {
			result = java.net.URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	
}
