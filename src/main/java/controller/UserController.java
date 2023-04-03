package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mindrot.jbcrypt.BCrypt;

import data.Avatar;
import data.User;
import repository.Avatars;
import repository.Users;

@WebServlet("/user/*")
public class UserController extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getContextPath() + "/user";
		String uri = req.getRequestURI().substring(path.length());
		
		if(uri.equals("/join")) { //회원가입
			List<Avatar> li = Avatars.findAll();
			req.setAttribute("avatars", li);
			
			if(req.getParameter("error") != null) {
				int error = Integer.parseInt(req.getParameter("error"));
				req.setAttribute("error", error);
			}
			
			req.getRequestDispatcher("/WEB-INF/views/user/join.jsp").forward(req, resp);
		}else if(uri.equals("/join-task")) { //회원가입 처리
			req.setCharacterEncoding("utf-8");
			String id = req.getParameter("id").toLowerCase();
			String pass = req.getParameter("pass");
			String name = req.getParameter("name");
			String avatar = req.getParameter("avatar");
			
			if(!id.matches("^[a-zA-Z0-9]+$")) {
				resp.sendRedirect("/user/join?error=1");
				return;
			}else if(pass.length() < 4) {
				resp.sendRedirect("/user/join?error=2");
				return;
			}else if(name.length() < 2) {
				resp.sendRedirect("/user/join?error=3");
				return;
			}
			
			String hashed = BCrypt.hashpw(pass, BCrypt.gensalt());
			
			int result = Users.create(id, hashed, name, avatar);
			if(result == 1) {
				resp.sendRedirect("/user/login");
				return;
			}else {
				resp.sendRedirect("/user/join?error=4");
				return;
			}
		}else if(uri.equals("/login")) { // 로그인
			if(req.getParameter("error") != null) {
				int error = Integer.parseInt(req.getParameter("error"));
				req.setAttribute("error", error);
			}
			
			
			if(req.getParameter("url") != null) {
				String url = req.getParameter("url");
				req.setAttribute("url", url);
			}
			
			Cookie[] cookies = req.getCookies();
			if(cookies != null) {
				for(Cookie c : cookies) {
					if(c.getName().equals("ID_SAVE")) {
						req.setAttribute("idsave", c.getValue());
					}
				}
			}
			
			req.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(req, resp);
		}else if(uri.equals("/login-task")) { // 로그인처리
			String id = req.getParameter("id");
			String pass = req.getParameter("pass");
			String idCookie = req.getParameter("idCookie");
			// User one = Users.findById(id);
			
			//==========================================================
			SqlSessionFactory factory = (SqlSessionFactory)
					req.getServletContext().getAttribute("sqlSessionFactory");
			SqlSession sqlSession = factory.openSession();
			User one = sqlSession.selectOne("users.findById", id);
			//==========================================================			
			sqlSession.close();
			if(one == null) {
				resp.sendRedirect("/user/login?error=2");
				return;
			}
			
			if(!BCrypt.checkpw(pass, one.getPass())) {
				resp.sendRedirect("/user/login?error=1");
				return;
			}else {
				HttpSession session = req.getSession();
				session.setAttribute("logon", true);
				session.setAttribute("logonUser", one);
				if(idCookie != null && idCookie.equals("on")) {
					Cookie c = new Cookie("ID_SAVE", one.getId());
					c.setPath("/");
					c.setMaxAge(60*60*24*30*3);

					resp.addCookie(c);
					
				}
				
				if(req.getParameter("url") != null) {
					String url = req.getParameter("url");
					resp.sendRedirect(url);
					return;
				}else {
					resp.sendRedirect("/");
					return;
				}
				// req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
			}
		}
	}
}
