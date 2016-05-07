package com.weixin.exception;

/**
 * 多重绑定异常
 * @author wan
 */
public class DuplicateBindException extends Exception{
	
	public DuplicateBindException() {}
	
	public DuplicateBindException(String msg) {
		super(msg);
	}
	
}
