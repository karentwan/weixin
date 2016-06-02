package com.weixin.pojo;

/**
 * 辅导员信息
 * @author wan
 */
public class InstruInfo {
	/*
	 * 辅导员工号
	 */
	private String name;
	/*
	 * 辅导员姓名
	 */
	private String nick;
	/*
	 * 辅导员学院
	 */
	private String college;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}
	
}
