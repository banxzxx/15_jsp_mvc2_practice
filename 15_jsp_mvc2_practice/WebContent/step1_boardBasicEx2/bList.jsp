<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>List</title>
</head>
<body>
	<h3>게시글 전체조회</h3>
	<table border="1">
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>조회수</th>
		</tr>
		
		<c:set var="idx" value="1"></c:set>
		
		<c:forEach var="boardDTO" items="${boardList }">
			<tr align="center">
				<td>${idx }</td>
				<td><a href="bDetail?boardId=${boardDTO.boardId }">${boardDTO.subject }</a></td>
				<td>${boardDTO.writer }</td>
				<td>${boardDTO.enrollDT }</td>
				<td>${boardDTO.readCnt }</td>
			</tr>
			<c:set var="idx" value="${idx = idx+1 }"/>
		</c:forEach>
		
		<tr>
			<td colspan="5">
				<input type="button" value="write" onclick="location.href='bWrite'">
			</td>
		</tr>
		
	</table>

</body>
</html>