package com.weixin.test;

import org.junit.Test;

public class ThreadTest {

	private static void print(String str) {
		System.out.println("hello, world!" + str);
	}
	
	class MyThread implements Runnable {
		
		private String str;
		
		public void setStr(String str) {
			this.str = str;
		}
		
		public void run() {
			print(str);
		}
		
	}
	
	
	@Test
	public void testThread() {
		MyThread mt = new MyThread();
		
		for(int i = 0; i < 50; i++) {
			mt.setStr("str" + i);
			new Thread(mt).start();
		}
	}
}
