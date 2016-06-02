package com.weixin.pojo;
/**
 * 创建菜单需要的ACESSTOKEN的封装类
 * 微信服务器发送过来的数据是一个json格式的字符串
 * @author wan
 */
public class AccessToken {
	
	private String token;
	
	private int expiresIn;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	
}
