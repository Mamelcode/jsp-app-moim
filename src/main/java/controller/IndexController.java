package controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import data.Moim;
import repository.Moims;

@WebServlet("/index")
public class IndexController extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SqlSessionFactory factory = (SqlSessionFactory)
				req.getServletContext().getAttribute("sqlSessionFactory");
		SqlSession session = factory.openSession();
		// select sql은 selectOne, selectList
		List<Moim> result = session.selectList("moims.findLatest");
		
		session.close();
		
		Date today = new Date();
		List<Moim> list = Moims.findLatest();
		req.setAttribute("latest", result);
		
		//req.setAttribute("latest", list);
		req.setAttribute("today", today);
		req.setAttribute("millis", System.currentTimeMillis());
		req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
	}
}
