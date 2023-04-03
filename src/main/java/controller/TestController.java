package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/test")
public class TestController extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 쿠키 : 브라우저에서 관리하는 데이터가 담긴 파일
		// 쿠키에는 도메인값(쿠키 발행처)이 있는데 브라우저는 웹 요청을 보낼때, 발행처가 같다면,
		// 쿠키를 자동으로 전동하게 되어있다.
		
		// 백엔드 쪽에서 클라이언트가 보낸 쿠키값을 확인할 수 있다.
		Cookie[] cookies = req.getCookies();
		
		// 가지고 오는 쿠키가 없다면 null
		if(cookies != null) {
			for(Cookie c : cookies) {
				System.out.println("Cookie ==> "+ c.toString());
			}
		}
		
		
	}
	
}



//@Override
//protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//	String message = "집에 가고 싶다";
//	
//	/*
//		for(int cnt=1; cnt <= 5; cnt++) {
//			String hashed = BCrypt.hashpw(message, BCrypt.gensalt());
//			System.out.println(hashed);
//		}
//		
//		String hashed = BCrypt.hashpw(message, BCrypt.gensalt());
//		System.out.println(BCrypt.checkpw(message, hashed));
//	 */
//}
