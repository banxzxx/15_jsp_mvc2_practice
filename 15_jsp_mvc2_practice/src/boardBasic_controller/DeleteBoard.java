package boardBasic_controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import boardBasic.dao.BoardDAO;
import boardBasic.dto.BoardDTO;


@WebServlet("/bDelete")
public class DeleteBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//delete 뷰에 보여야할 정보 세팅하기
		// detail에서가지고온 보드아이디 값을 통해 정보를 가져오기 + boarddetail 메서드에 조회수 인자 false 해놓기
		
		BoardDTO boardDTO = BoardDAO.getInstance().getBoardDetail(Long.parseLong(request.getParameter("boardId")), false);
		request.setAttribute("boardDTO", boardDTO);
		
		RequestDispatcher dis = request.getRequestDispatcher("step1_boardBasicEx2/bDelete.jsp"); 
		dis.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//지우려고 한 정보를 받았다 !
		
		request.setCharacterEncoding("utf-8");
		
		// 지우려는 정보의 객체 DTO 만들어주기
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setBoardId(Long.parseLong(request.getParameter("boardId")));
		boardDTO.setPassword(request.getParameter("password"));
		
		
		//지운 후 가상의 html을 통해 지움 여부를 알리기
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter pw = response.getWriter();
		
		String jsScript = "";
		if (BoardDAO.getInstance().deleteBoard(boardDTO) ) {
			jsScript = "<script>";
			jsScript += "alert('deleted successfully !');";
			jsScript += "location.href='bList';";
			jsScript += "</script>";
		}
		else {
			jsScript = "<script>";
			jsScript += "alert('check your password');";
			jsScript += "history.go(-1);";
			jsScript += "</script>";
		}
		
		pw.write(jsScript);
		
	}

}
