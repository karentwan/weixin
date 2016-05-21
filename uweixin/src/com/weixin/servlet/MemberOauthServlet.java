package com.weixin.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.weixin.dao.BindDao;
import com.weixin.pojo.WeixinOauth2Token;
import com.weixin.util.OauthUtil;
import com.weixin.util.WeixinUtil;

/**
 * 会员查询获取openId
 * @author wan
 */
public class MemberOauthServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String code = request.getParameter("code");
System.out.println("code:" + code);
		if( !"authdeny".equals(code)) {
			//获取访问凭证access_token
			String appId = WeixinUtil.prop.getProperty("appId");
			String secret = WeixinUtil.prop.getProperty("secret");
			WeixinOauth2Token wot = OauthUtil.getOauth2AccessToken(appId, secret, code);
			//获取openId
			String openId = wot.getOpenId();
			boolean flag = BindDao.getInstance().isBind(openId);
			request.setAttribute("openId", openId);
System.out.println("openId:" + openId);
			//如果没有绑定
			if(!flag) {
				//前往绑定的页面
//				request.getRequestDispatcher("/bind.html?openId=" + openId).forward(request, response);
				response.sendRedirect("/uweixin/bind.html?openId=" + openId);
			} else {
				//前往查看余额的页面
//				request.getRequestDispatcher("/user.html?openId=" + openId).forward(request, response);
				response.sendRedirect("/uweixin/users.html?openId=" + openId);
			}
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		
	}

}
