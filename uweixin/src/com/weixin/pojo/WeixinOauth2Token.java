package com.weixin.pojo;

/**
 * ��ȡ΢����Ȩ��Access_token
 * ��refresh_token
 * @author wan
 */
public class WeixinOauth2Token {
	//��ҳ��Ȩ�Ľӿڵ���ƾ֤
	private String accessToken;
	//ƾ֤��Чʱ��
	private int expiresIn;
	//ˢ��ҳ�����Чƾ֤
	private String refreshToken;
	//�û���openId������ͨ�������ȡ�û��Ļ�����Ϣ
	private String openId;
	//�û���Ȩ��������
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
