package com.weixin.exception;

/***
 * 数据不合格异常
 * @author wan
 */
public class NumberInvalidException extends Exception{

	public NumberInvalidException() {
		super();
	}
	
	public NumberInvalidException(String msg) {
		super(msg);
	}
	
}
