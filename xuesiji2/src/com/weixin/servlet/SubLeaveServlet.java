package com.weixin.servlet;

import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import com.weixin.abstractServlet.AbstractQuery;
import com.weixin.dao.SubQueryDao;
import com.weixin.pojo.Leave;

/**
 * 查询请假的情况
 * @author wan
 */
public class SubLeaveServlet extends AbstractQuery{

	private String str = -1 + "";
	
	@Override
	public void query(String name, String time) {
		Map<String, List<Leave>> map = SubQueryDao.getInstance().queryLeave(time, name);
		str = JSONObject.fromObject(map).toString();
	}

	@Override
	public String getResponse() {
		return str;
	}

}
