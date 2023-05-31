<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>detail</title>
</head>
<body>
	<h1> 게시글 상세 조회 </h1>
	
	<table border="1">
		<tr>
			<td>read_cnt</td>
			<td>${boardDTO.readCnt }</td>
		</tr>
		<tr>
			<td>writer</td>
			<td>${boardDTO.writer }</td>
		</tr>
		<tr>
			<td>written date</td>
			<td>${boardDTO.enrollDT }</td>
		</tr>
		<tr>
			<td>email</td>
			<td>${boardDTO.email }</td>
		</tr>
		<tr>
			<td>subject</td>
			<td>${boardDTO.subject }</td>
		</tr>
		<tr>
			<td>content</td>
			<td>${boardDTO.content }</td>
		</tr>
		
		<tr>
			<td colspan="3">
				<!-- 수정,삭제는 보드아이드를 가진채로 이동해주기 -->
				<input type="button" value="replace" onclick="location.href='bUpdate?boardId=${boardDTO.boardId}'">
				<input type="button" value="delete" onclick="location.href='bDelete?boardId=${boardDTO.boardId}'">
				<input type="button" value="go list" onclick="location.href='bList'">
			</td>
		</tr>
	</table>
</body>
</html>