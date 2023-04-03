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

import data.Moim;
import data.User;
import repository.Attendances;
import repository.Moims;

@WebServlet("/moim/join-task")
public class MoimJoinTaskController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		SqlSessionFactory factory = (SqlSessionFactory)
				req.getServletContext().getAttribute("sqlSessionFactory");
		SqlSession sqlSession = factory.openSession();
		
		String id = req.getParameter("id");
		Moim found = sqlSession.selectOne("moims.findById", id);
		if(found.getCurrentPerson() >= found.getMaxPerson()) {
			resp.sendRedirect("/moim/detail?id="+ id);
			return;
		}
		User logonUser = (User)req.getSession().getAttribute("logonUser");
		
		Map<String, Object> params = new HashMap<>();
		params.put("moimId", id);
		params.put("userId", logonUser.getId());
		params.put("status", found.getType().equals("public") ? 2 : 1);
		int r = sqlSession.insert("attendances.create", params);
		
		if(found.getType().equals("public")) {
			sqlSession.update("moims.updateCurrentPerson", id);
		}
		
		sqlSession.commit();
		sqlSession.close();
		
		resp.sendRedirect("/moim/detail?id="+ id);
		return;
//		String type = req.getParameter("type");
//		int status = 1;
//		if(type.equals("public")) {
//			status = 2;
//			Moims.updatePerson(id);
//		}
//		
//		int result = Attendances.create(id, logonUser.getId(), status);
//		if(result == 1) {
//			
//			resp.sendRedirect("/moim/detail?id="+ id);
//			return;
//		}
	}
}
