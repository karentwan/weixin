package com.weixin.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.weixin.dao.InstruDao;
import com.weixin.pojo.WeixinOauth2Token;
import com.weixin.util.OauthUtil;
import com.weixin.util.WeixinUtil;

/**
 * 获取辅导员的openId授权
 * @author wan
 */
public class InstruOauthServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String code = request.getParameter("code");
		//判断是否已 授权
		if(!"authdeny".equals(code)) {
			String appId = WeixinUtil.prop.getProperty("appId");
			String secret = WeixinUtil.prop.getProperty("secret");
			WeixinOauth2Token wot = OauthUtil.getOauth2AccessToken(appId, secret, code);
			String openId = wot.getOpenId();
			String name = InstruDao.getInstance().verify(openId);
			if(name == null) {
				request.setAttribute("openId", openId);
				//如果辅导员未绑定，那么就跳转到绑定页面
				request.getRequestDispatcher("/WEB-INF/query/bind.jsp").forward(request, response);
				//如果该辅导员已经绑定了页面，那么就给他查询的权限
			} else {
				//将辅导员的姓名放进attribute里面，用以后面的查询
				request.setAttribute("name", name);
				request.getRequestDispatcher("/WEB-INF/query/instruQuery.jsp").forward(request, response);
			}
		}
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
