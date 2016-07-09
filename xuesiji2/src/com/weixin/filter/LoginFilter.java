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
import javax.servlet.http.HttpSession;
import com.weixin.model.User;
import com.weixin.util.WeixinUtil;

public class LoginFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse resp =(HttpServletResponse) response;
		HttpSession session = (HttpSession)((HttpServletRequest) request).getSession();
		User u = (User) session.getAttribute("user");
		if( u != null) {
			chain.doFilter(request, response);
		} else {
			resp.sendRedirect(WeixinUtil.prop.getProperty("route") + "/Login/login.html?status=error");
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
	

}
