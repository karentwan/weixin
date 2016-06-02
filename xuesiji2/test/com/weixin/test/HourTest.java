package com.weixin.test;

import org.junit.Test;

public class HourTest {
	
	@Test
	public void testHour() {
		int hour = 23;
		if( hour >= 23 || hour < 21) {
			System.out.println("现在不是签到时间，不能签到!");
		}
		
		
	}

}
