package com.weixin.pojo;

/**
 * 对应tb_instru表的信息
 * @author wan
 */
public class Instru {
	/*
	 * 自动生成的主键 
	 */
	private int id;
	/*
	 * 辅导员的工号
	 */
	private String name;
	/*
	 * 密码 
	 */
	private String psd;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPsd() {
		return psd;
	}

	public void setPsd(String psd) {
		this.psd = psd;
	}
	
}
