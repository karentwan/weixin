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
 * ��ȡ����Ա��openId��Ȩ
 * @author wan
 */
public class InstruOauthServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String code = request.getParameter("code");
		//�ж��Ƿ��� ��Ȩ
		if(!"authdeny".equals(code)) {
			String appId = WeixinUtil.prop.getProperty("appId");
			String secret = WeixinUtil.prop.getProperty("secret");
			WeixinOauth2Token wot = OauthUtil.getOauth2AccessToken(appId, secret, code);
			String openId = wot.getOpenId();
			String name = InstruDao.getInstance().verify(openId);
			if(name == null) {
				request.setAttribute("openId", openId);
				//�������Աδ�󶨣���ô����ת����ҳ��
				request.getRequestDispatcher("/WEB-INF/query/bind.jsp").forward(request, response);
				//����ø���Ա�Ѿ�����ҳ�棬��ô�͸�����ѯ��Ȩ��
			} else {
				//������Ա�������Ž�attribute���棬���Ժ���Ĳ�ѯ
				request.setAttribute("name", name);
				request.getRequestDispatcher("/WEB-INF/query/instruQuery.jsp").forward(request, response);
			}
		}
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
