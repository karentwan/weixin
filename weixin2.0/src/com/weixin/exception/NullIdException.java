package com.weixin.exception;

/**
 * ��Id�쳣
 * @author wan
 */
public class NullIdException extends Exception{
	
	public NullIdException(){}
	
	public NullIdException(String msg) {
		super(msg);
	}
}
