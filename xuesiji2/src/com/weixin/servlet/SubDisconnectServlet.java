package com.weixin.servlet;

import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import com.weixin.abstractServlet.AbstractQuery;
import com.weixin.dao.SubQueryDao;
import com.weixin.pojo.Sign;

/**
 * 查询未签到的情况
 * @author wan
 */
public class SubDisconnectServlet extends AbstractQuery{

	private String str = -1 + "";
	
	@Override
	public void query(String name, String time) {
		Map<String, List<Sign>> map = SubQueryDao.getInstance().queryDisconnect(time, name);
		str = JSONObject.fromObject(map).toString();
	}

	@Override
	public String getResponse() {
		return str;
	}

	
	
}
