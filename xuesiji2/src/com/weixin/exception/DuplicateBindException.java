package com.weixin.exception;

/**
 * ���ذ��쳣
 * @author wan
 */
public class DuplicateBindException extends Exception{
	
	public DuplicateBindException() {}
	
	public DuplicateBindException(String msg) {
		super(msg);
	}
	
}
