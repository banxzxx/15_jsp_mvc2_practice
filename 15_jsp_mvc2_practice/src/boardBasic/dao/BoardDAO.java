package boardBasic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
			DataSource ds = (DataSource) envctx.lookup("jdbc/pool3"); 		  // <Context>태그안의 <Resource> 환경설정의 name이 jdbc/pool인 것을 검색
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
			pstmt = conn.prepareStatement("INSERT INTO BOARD2(WRITER, EMAIL, SUBJECT, PASSWORD, ENROLL_DT, READ_CNT,CONTENT) VALUES(?,?,?,?,NOW(),0,?)");
			pstmt.setString(1, boardDTO.getWriter());
			pstmt.setString(2, boardDTO.getEmail());
			pstmt.setString(3, boardDTO.getSubject());
			pstmt.setString(4, boardDTO.getPassword());
			pstmt.setString(5, boardDTO.getContent());
			pstmt.executeUpdate();
			
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			getClose();
		}
	}
	
	public ArrayList<BoardDTO> getBoardList(){
		
		// 새로운 보드리스트 선언
		ArrayList<BoardDTO> boardList = new ArrayList<>();
		
		//데베와의 연결을 통해 객체들을 리스트에 넣어주기
		try {
			getConnection();
			
			pstmt = conn.prepareStatement("SELECT * FROM BOARD2");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardDTO boardDTO = new BoardDTO();
				
				// password 제외하고 다 받기
				boardDTO.setBoardId(rs.getLong("BOARD_ID"));
				boardDTO.setWriter(rs.getString("WRITER"));
				boardDTO.setEmail(rs.getString("EMAIL"));
				boardDTO.setSubject(rs.getString("SUBJECT"));
				boardDTO.setEnrollDT(rs.getDate("ENROLL_DT"));
				boardDTO.setReadCnt(rs.getLong("READ_CNT"));
				boardDTO.setContent(rs.getString("CONTENT"));
				
				boardList.add(boardDTO);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			getClose();
		}
		
		return boardList;
	}
	
	public BoardDTO getBoardDetail(long boardId, boolean isincreaseReadCnt) {
		
		// 해당 보드 아이디의 DTO를 가져올 새로운 객체 생성 !
		BoardDTO boardDTO = new BoardDTO();
		
		try {
			getConnection();
			
			// bDetail에서 해당 함수를 불러온거면 조회수를 증가하는게 맞음!
			if ( isincreaseReadCnt ) {
				pstmt = conn.prepareStatement("UPDATE BOARD2 SET READ_CNT = READ_CNT + 1 WHERE BOARD_ID =?");
				pstmt.setLong(1, boardId);
				pstmt.executeUpdate();
			}
			
			// 해당 보드 아이디의 DTO 정보를 전부 가져오기 ( password 제외 ) 
			pstmt = conn.prepareStatement("SELECT * FROM BOARD2 WHERE BOARD_ID = ?");
			pstmt.setLong(1, boardId);
			rs = pstmt.executeQuery();
			
			if ( rs.next() ) {
				boardDTO.setBoardId(boardId);
				boardDTO.setWriter(rs.getString("WRITER"));
				boardDTO.setEmail(rs.getString("Email"));
				boardDTO.setSubject(rs.getString("Subject"));
				boardDTO.setEnrollDT(rs.getDate("ENROLL_DT"));
				boardDTO.setReadCnt(rs.getLong("READ_CNT"));
				boardDTO.setContent(rs.getString("CONTENT"));
			}
				
		} catch (Exception e ) {
			e.printStackTrace();
		} finally {
			getClose();
		}
		
		
		return boardDTO;
	}
	
	// 비밀번호 맞는지 검사하기 !
	public boolean checkValidateMember(BoardDTO boardDTO) {
		boolean isValid = false;
		
		try {
			getConnection();
			
			pstmt = conn.prepareStatement("SELECT * FROM BOARD2 WHERE BOARD_ID = ? AND PASSWORD = ?");
			pstmt.setLong(1, boardDTO.getBoardId());
			pstmt.setString(2, boardDTO.getPassword());
			rs = pstmt.executeQuery();
			
			if ( rs.next() ) isValid = true;
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
		
		return isValid;
	}
	public boolean updateBoard(BoardDTO boardDTO) {
		boolean isUpdate = false;
		
		try { 
			// 비밀번호가 맞는 경우, 새로 적은대로 수정해주기 
			if (checkValidateMember(boardDTO) ) {
				getConnection();
				
				pstmt = conn.prepareStatement("UPDATE BOARD2 SET SUBJECT=?, CONTENT=? WHERE BOARD_ID=?");
				pstmt.setString(1, boardDTO.getSubject());
				pstmt.setString(2, boardDTO.getContent());
				pstmt.setLong(3, boardDTO.getBoardId());
				
				pstmt.executeUpdate();
				isUpdate = true;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
		
		return isUpdate;
	}
	
	public boolean deleteBoard(BoardDTO boardDTO) {
		boolean isDelete = false;
		
		try {
			
			if (checkValidateMember(boardDTO) ) {
				getConnection();
				
				pstmt = conn.prepareStatement("DELETE FROM BOARD2 WHERE BOARD_ID = ?");
				pstmt.setLong(1, boardDTO.getBoardId());
				pstmt.executeUpdate();
				
				isDelete = true;
			}
		}catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			getClose();
		}
		
		return isDelete;
	}
	
}
