package com.weixin.test;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import com.weixin.dao.ManagerDao;

public class ManagerTest {

	@Test
	public void testClass() {
		List<String> list = new ArrayList<String>();
		list.add("140431");
		list.add("150451");
//		list.add("150452");
//		list.add("130443");
		int code = 0;
//		code = ManagerDao.getInstance().deleteClass(list);
		System.out.println("code:" + code);
	}
	
}
