package boardBasic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import boardBasic.dto.BoardDTO;

public class BoardDAO {

	//싱글톤
	private BoardDAO() {} // 프라이빗 생성자
	private static BoardDAO instance = new BoardDAO(); // 고정 프라이빗 객체 생성
	public static BoardDAO getInstance() { // static 객체 외부에서 불러오는 메서드
		return instance;
	}
	
	private Connection conn  = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	public void getConnection() {
		
		try {
			Context initctx = new InitialContext();
			Context envctx = (Context) initctx.lookup("java:comp/env");
			DataSource ds = (DataSource) envctx.lookup("jdbc/pool"); 		  // <Context>태그안의 <Resource> 환경설정의 name이 jdbc/pool인 것을 검색
			conn = ds.getConnection();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
	}
	
	public void getClose() {
		if (rs != null)    {try {rs.close();}   catch (SQLException e) {}}
    	if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
        if (conn != null)  {try {conn.close();}  catch (SQLException e) {}}
	}
	
	// 작성된 정보를 데베에 넣기
	public void insertBoard(BoardDTO boardDTO ) {
		try {
			
			getConnection();
			
			// 작성자, 이메일, 제목, 비밀번호, 작성된 시간, 조회수(=0), 내용
			pstmt = conn.prepareStatement("INSERT INTO BOARD2(WRITER, EMAIL, SUBJECT, PASSWORD, ENROLL_DT, READ_CNT,CONTENT) VALUES(?,?,?,?,NOW(),0,?");
			pstmt.setString(1, boardDTO.getWriter());
			pstmt.setString(2, boardDTO.getEmail());
			pstmt.setString(3, boardDTO.getSubject());
			pstmt.setString(4,boardDTO.getPassword());
			pstmt.setString(5, boardDTO.getContent());
			
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			getClose();
		}
	}
	
}
