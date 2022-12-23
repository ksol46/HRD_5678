package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.DAO;


@WebServlet("/")
public class ProgramController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ProgramController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doPro(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doPro(request,response);
	}
	
	protected void doPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String context = request.getContextPath();
		
		String command = request.getServletPath();
		
		String site = null;
		
		System.out.println(context + "," + command);
		
		DAO dao = new DAO();
		
		switch (command) {
		case "/member" :
			site = dao.memberView(request, response);
			break;
		case "/home" :
			site = "index.jsp";
			break;
		case "/vote" :
			site = "vote.jsp";
			break;
		case "/do_vote" :
			int result = dao.do_vote(request, response);
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			if(result ==1) { //업데이트 성공
				out.println("<script>");
				out.println("alert('투표하기 정보가 정상적으로 등록 되었습니다!'); location.href='" + context +"';");
				out.println("</script>");
				out.flush();
			} else {
				out.println("<script>");
				out.println("alert('잘못 입력하였습니다!'); location.href='" + context +"';");
				out.println("</script>");
				out.flush();
			}
			break;
		case "/inquiry" :
		site = dao.do_inquiry(request, response);
			break;
		case "/rank" :
			site = dao.do_rank(request, response);
			break;
		default : break;
		}
		
		
		getServletContext().getRequestDispatcher("/" + site).forward(request, response);
	}
	

}
