package com.weixin.pojo;

/**
 * ��ʷ��¼������һ��
 * @author wan
 */
public class HistoryItem {
	
	//ʱ��
	private String time;
	//ǩ��������
	private int signInCount;
	//��ٵ�����
	private int leaveCount;
	//δǩ��������
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
