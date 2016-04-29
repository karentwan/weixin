package com.weixin.model.response;

public class Article {
	
	private String Title;
	
	private String Description;
	
	private String PicUrl;
	
	private String Url;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description == null ? "" : Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getPicUrl() {
		return PicUrl == null ? "" : PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getUrl() {
		return Url == null ? "" : Url;
	}

	public void setUrl(String url) {
		Url = url;
	}
	
}
