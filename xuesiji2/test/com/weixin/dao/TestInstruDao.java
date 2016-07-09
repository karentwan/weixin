package com.weixin.dao;

import java.util.List;
import org.junit.Test;

public class TestInstruDao {

	private InstruDao id = InstruDao.getInstance();
	
	@Test
	public void testDate() {
		List<String> list = id.getDate(1);
		for(String s : list) {
			System.out.println("s:" + s);
		}
	}
	
}
