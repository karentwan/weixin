package com.weixin.model;

/**
 * 回复视频消息
 * @author wan
 */
public class VideoMessage extends BaseMessage {

	private Video video;

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}
	
}
