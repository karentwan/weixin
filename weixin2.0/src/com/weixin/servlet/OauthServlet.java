package com.weixin.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.weixin.pojo.UserInfo;
import com.weixin.pojo.WeixinOauth2Token;
import com.weixin.util.OauthUtil;
import com.weixin.util.WeixinUtil;

/**
 * 验证
 * @author wan
 */
public class OauthServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//设置字符集编码
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		//用户授权同意之后获取code值
		String code = request.getParameter("code");
		if( !"authdeny".equals(code)) {
			//获取访问凭证access_token
			String appId = WeixinUtil.prop.getProperty("appId");
			String secret = WeixinUtil.prop.getProperty("secret");
			WeixinOauth2Token wot = OauthUtil.getOauth2AccessToken(appId, secret, code);
			String accessToken = wot.getAccessToken();
			String openId = wot.getOpenId();
			//获取用户信息
			UserInfo ui = OauthUtil.getUserInfo(accessToken, openId);
			//将这里面的内容放进篮子里面
			request.setAttribute("userInfo", ui);
//			request.setAttribute("openId", ui.getOpenId());
		}
//		request.getRequestDispatcher("bind").forward(request, response);
		//重定向到微信jsp页面
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
	

}
 