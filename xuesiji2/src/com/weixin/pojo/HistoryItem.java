package com.weixin.pojo;

/**
 * 历史记录的其中一项
 * @author wan
 */
public class HistoryItem {
	
	//时间
	private String time;
	//签到的总数
	private int signInCount;
	//请假的总数
	private int leaveCount;
	//未签到的总数
	private int disconnectCount;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getSignInCount() {
		return signInCount;
	}

	public void setSignInCount(int signInCount) {
		this.signInCount = signInCount;
	}

	public int getLeaveCount() {
		return leaveCount;
	}

	public void setLeaveCount(int leaveCount) {
		this.leaveCount = leaveCount;
	}

	public int getDisconnectCount() {
		return disconnectCount;
	}

	public void setDisconnectCount(int disconnectCount) {
		this.disconnectCount = disconnectCount;
	}
	
}
