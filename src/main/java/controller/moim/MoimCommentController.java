package controller.moim;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

@WebServlet("/moim/comment")
public class MoimCommentController extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		SqlSessionFactory factory = (SqlSessionFactory)
				req.getServletContext().getAttribute("sqlSessionFactory");
		SqlSession sqlSession = factory.openSession();
		
		String commentText = req.getParameter("commentText");
		String commentUser = req.getParameter("commentUser");
		String moimId = req.getParameter("moimId");
		
		Map<String, Object> params = new HashMap<>();
		params.put("moimId", moimId);
		params.put("writer", commentUser);
		params.put("ment", commentText);
		int r = sqlSession.insert("replys.create", params);
		
		sqlSession.commit();
		sqlSession.close();
		
		resp.sendRedirect("/moim/detail?id="+ moimId);
		return;
	}
}
