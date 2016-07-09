package com.weixin.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.weixin.dao.StudentDepartDao;
import com.weixin.pojo.WeixinOauth2Token;
import com.weixin.util.OauthUtil;
import com.weixin.util.WeixinUtil;

/**
 * ��ѯ������Ϣ������������ѧ�������Ǹ���Ա
 * !��δ��web.xml��������
 * @author wan
 */
public class QueryFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String code = request.getParameter("code");
		//�ж��Ƿ��� ��Ȩ
		if(!"authdeny".equals(code)) {
			String appId = WeixinUtil.prop.getProperty("appId");
			String secret = WeixinUtil.prop.getProperty("secret");
			WeixinOauth2Token wot = OauthUtil.getOauth2AccessToken(appId, secret, code);
			String openId = wot.getOpenId();
			boolean flag = StudentDepartDao.getInstance().verify(openId);
			//�������󶨵���Ϣ��ѧ��������ʦ�Ļ� ����ô�������ѯ
			if(flag) {
				
				
				
			//��������ʦ����ѧ��������ʦ�Ļ�����ô�ͷ���	
			} else {
				chain.doFilter(request, response);
			}
		}
		
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

}
