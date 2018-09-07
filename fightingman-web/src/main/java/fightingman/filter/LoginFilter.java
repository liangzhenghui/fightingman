package fightingman.filter;

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

import fightingman.ums.model.User;

/**
 * @author liangzhenghui
 * @date Aug 30, 2013    7:49:34 PM
 */
public class LoginFilter implements Filter{

	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletReponse = (HttpServletResponse)response;
		HttpSession session = httpServletRequest.getSession();
		User user = (User) session.getAttribute("user");
		if( user == null) {
			httpServletReponse.sendRedirect(httpServletRequest.getContextPath()+"/login.jsp");
		}
		else {
			chain.doFilter(request, response);
		}
		
	}

	public void destroy() {
		
	}

}
