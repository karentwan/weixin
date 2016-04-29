package com.weixin.pojo;

/**
 * 获取微信授权的Access_token
 * 和refresh_token
 * @author wan
 */
public class WeixinOauth2Token {
	//网页授权的接口调用凭证
	private String accessToken;
	//凭证有效时长
	private int expiresIn;
	//刷新页面的有效凭证
	private String refreshToken;
	//用户的openId，可以通过这个获取用户的基本信息
	private String openId;
	//用户授权的作用域
	private String scope;
	
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
}
