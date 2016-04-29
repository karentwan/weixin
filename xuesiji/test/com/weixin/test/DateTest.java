package com.weixin.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DateTest {

//	@Test
//	public void testHour() {
//		Calendar c = Calendar.getInstance();
//		int hour = c.get(Calendar.HOUR_OF_DAY);
//		int date = c.get(Calendar.DATE);
//		System.out.println("hour:" + hour + "DATE:" + date);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
//		String str = sdf.format(new Date());
//		System.out.println("str:" + str);
//	}
	
//	@Test
//	public void testString() {
//		String str = "asd";
//		Integer.parseInt(str);
//	}
//	@Test
//	public void testList() {
//		List<String> lists= new ArrayList<String>();
//		lists.add("hello");
//		lists.add("world!");
//		System.out.println("str:" + lists.get(0));
//	}
	@Test
	public void testDate() {
		int i = 61 % 30;
		int b = 61/30;
		System.out.println("i:" + i + "\nb:" + b + "\n" + null);
	}
	
}
