package com.weixin.servlet;

import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import com.weixin.abstractServlet.AbstractQuery;
import com.weixin.dao.SubQueryDao;
import com.weixin.pojo.Sign;

/**
 * 查询已签到的情况
 * @author wan
 */
public class SubSignInServlet extends AbstractQuery{
	
	private String str = -1 + "";

	@Override
	public void query(String name, String time) {
		Map<String, List<Sign>> map = SubQueryDao.getInstance().querySign(time, name);
		JSONObject obj = JSONObject.fromObject(map);
		str = obj.toString();
	}

	@Override
	public String getResponse() {
		return str;
	}
	
	
}
