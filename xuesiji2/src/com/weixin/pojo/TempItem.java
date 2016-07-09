package com.weixin.pojo;

/**
 * 记录数据的临时Item
 * @author wan
 */
public class TempItem {
	
	/*
	 * 是什么类型的数据，例如signInCount、leaveCount、disconnectCount
	 */
	private String type;
	
	private String time;
	
	private int count;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
	
	
}
