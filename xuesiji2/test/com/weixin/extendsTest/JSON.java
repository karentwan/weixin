package com.weixin.extendsTest;

import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.junit.Test;
import com.weixin.dao.SubQueryDao;
import com.weixin.pojo.Sign;

public class JSON {

	@Test
	public void test() {
		Map<String, List<Sign>> map = SubQueryDao.getInstance().queryDisconnect("2016-06-02", "admin");
		System.out.println("map:" + JSONObject.fromObject(map).toString());
	}
	
}
