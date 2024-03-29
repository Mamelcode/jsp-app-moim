package controller.moim;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import data.Moim;
import repository.Moims;

@WebServlet("/moim/search")
public class MoimSearchController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String[] arycate = req.getParameterValues("cate");
		String cate = req.getParameter("cate");
		
		//============== 페이징처리 ===============
		String page =  req.getParameter("page");
		
		int p;
		if(req.getParameter("page") == null) {
			p = 1;
		}else {
			p = Integer.parseInt(req.getParameter("page"));
		}
		
		SqlSessionFactory factory = (SqlSessionFactory)
				req.getServletContext().getAttribute("sqlSessionFactory");
		SqlSession sqlSession = factory.openSession();
		Map<String, Object> map = new HashMap<>();
		map.put("a", (p-1)*6+1);
		map.put("b", 6*p);
		map.put("cates", arycate);
		//List<Moim> list = sqlSession.selectList("moims.findSomeByCates", arycate);
		List<Moim> list = sqlSession.selectList("moims.findSomeByAtoBInCates", map);
		
		int total = sqlSession.selectOne("moims.countDatas", arycate);
		int totalPage = total/6 + (total % 6 > 0 ? 1 : 0);
		int viewPage = 5;
		
		int endPage = (((p-1)/viewPage)+1) * viewPage;
		if(totalPage < endPage) {
		    endPage = totalPage;
		}
		int startPage = ((p-1)/viewPage) * viewPage + 1;
		
		sqlSession.close();
		//====================================
		req.setAttribute("select", arycate);
		req.setAttribute("start", startPage);
		req.setAttribute("last", endPage);
		boolean existPrev = p >= 6;
		boolean existNext = true;
		if(endPage >= totalPage)
		{
			existNext = false;
		}
		
		req.setAttribute("existPrev", existPrev);
		req.setAttribute("existNext", existNext);
		
		if(arycate != null) {
			List<Moim> li = Moims.findBycateBoth(arycate);
			req.setAttribute("category", list);
			req.getRequestDispatcher("/WEB-INF/views/moim/search.jsp").forward(req, resp);
			return;
		}
		
		if(cate != null && !cate.equals("")) {
			List<Moim> li = Moims.findBycate(cate);
			req.setAttribute("category", list);
		}else if(cate == null) {
			List<Moim> li = Moims.findByAllcate();
			req.setAttribute("category", list);
		}
		
		req.getRequestDispatcher("/WEB-INF/views/moim/search.jsp").forward(req, resp);
	}
}
