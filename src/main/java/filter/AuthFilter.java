package filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.User;

@WebFilter({"/moim/create", "/moim/join-task"})
public class AuthFilter extends HttpFilter {
	
	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String url = request.getRequestURI();
		// System.out.println("URL ==> "+ url);
		
		User logonUser = (User)request.getSession().getAttribute("logonUser");
		boolean logon = (boolean) request.getSession().getAttribute("logon");
		
		if(logon && logonUser != null) {
			chain.doFilter(request, response);
		}else {
			response.sendRedirect("/user/login?url="+ url);
		}
	}
}
