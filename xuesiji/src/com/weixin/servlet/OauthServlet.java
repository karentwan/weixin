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
 * ��֤
 * @author wan
 */
public class OauthServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//�����ַ�������
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		//�û���Ȩͬ��֮���ȡcodeֵ
		String code = request.getParameter("code");
		if( !"authdeny".equals(code)) {
			//��ȡ����ƾ֤access_token
			String appId = WeixinUtil.prop.getProperty("appId");
			String secret = WeixinUtil.prop.getProperty("secret");
			WeixinOauth2Token wot = OauthUtil.getOauth2AccessToken(appId, secret, code);
			String accessToken = wot.getAccessToken();
			String openId = wot.getOpenId();
			//��ȡ�û���Ϣ
			UserInfo ui = OauthUtil.getUserInfo(accessToken, openId);
			//������������ݷŽ���������
			request.setAttribute("userInfo", ui);
//			request.setAttribute("openId", ui.getOpenId());
		}
//		request.getRequestDispatcher("bind").forward(request, response);
		//�ض���΢��jspҳ��
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
	

}
 