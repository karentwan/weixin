package com.weixin.exception;

/**
 * 用户名和密码匹配失败
 * @author wan
 */
public class MatchFiledException extends Exception{
	
	public MatchFiledException() {
		
	}
	
	public MatchFiledException(String msg) {
		super(msg);
	}

}
