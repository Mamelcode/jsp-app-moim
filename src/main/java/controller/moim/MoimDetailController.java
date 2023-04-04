package controller.moim;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import data.Attendance;
import data.Comment;
import data.Moim;
import data.User;
import repository.Attendances;
import repository.Moims;
import repository.Users;

@WebServlet("/moim/detail")
public class MoimDetailController extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String id = req.getParameter("id");
		
		//============= 댓글 가져오기 ============= 
		SqlSessionFactory factory = (SqlSessionFactory)
				req.getServletContext().getAttribute("sqlSessionFactory");
		SqlSession sqlSession = factory.openSession();
		List<Comment> commentList = sqlSession.selectList("replys.findByComment", id);
		req.setAttribute("commentList", commentList);
		
		List<Attendance> attendances = Attendances.findByMoimId(id);
		//=====================================		
		
		for(Attendance a : attendances) {
			User found = Users.findById(a.getUserId());
			a.setUserAvatarURL(found.getAvatarURL());
			a.setUserName(found.getName());
		}
		req.setAttribute("attendances", attendances);
		
		
		Moim moim = Moims.findById(id);
		if(moim == null) {
			resp.sendRedirect("/moim/search");
			return;
		} 
		
		req.setAttribute("moim", moim);
		req.setAttribute("status", 0);		
		
		User logonUser = (User)req.getSession().getAttribute("logonUser");
		if(logonUser == null) {
			req.setAttribute("status", -1);
		}else {
			//===================
			String logonId = logonUser.getId();
			for(Attendance a : attendances) {
				if(a.getUserId().equals(logonId)) {
					req.setAttribute("status", 2);
					break;
				}
			}
		}

		req.getRequestDispatcher("/WEB-INF/views/moim/detail.jsp").forward(req, resp);
	}
}
