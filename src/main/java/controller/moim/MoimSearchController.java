package controller.moim;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.Moim;
import repository.Moims;

@WebServlet("/moim/search")
public class MoimSearchController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String[] arycate = req.getParameterValues("cate");
		String cate = req.getParameter("cate");
		
		req.setAttribute("select", arycate);
		
		if(arycate != null) {
			List<Moim> li = Moims.findBycateBoth(arycate);
			req.setAttribute("category", li);
			req.getRequestDispatcher("/WEB-INF/views/moim/search.jsp").forward(req, resp);
			return;
		}
		
		if(cate != null && !cate.equals("")) {
			List<Moim> li = Moims.findBycate(cate);
			req.setAttribute("category", li);
		}else if(cate == null) {
			List<Moim> li = Moims.findByAllcate();
			req.setAttribute("category", li);
		}
		
		req.getRequestDispatcher("/WEB-INF/views/moim/search.jsp").forward(req, resp);
	}
}
