package com.weixin.pojo;

import java.util.List;

/**
 * 用户信息的封装类
 * @author wan
 */
public class UserInfo {
	//用户标识
	private String openId;
	//用户昵称
	private String nickName;
	//用户性别
	private int sex;
	//国家
	private String country;
	//省份
	private String province;
	//城市
	private String city;
	//用户头像链接
	private String headImgUrl;
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public List<String> getPrivilegeList() {
		return privilegeList;
	}
	public void setPrivilegeList(List<String> privilegeList) {
		this.privilegeList = privilegeList;
	}
	//用户授权信息
	private List<String> privilegeList;
	
	
}
