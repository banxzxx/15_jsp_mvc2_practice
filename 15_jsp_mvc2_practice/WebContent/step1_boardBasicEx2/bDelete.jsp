<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>delete</title>
</head>
<body>
	<h1> 게시글 삭제하기 </h1>
	<form action="bDelete" method="post">
		<table border="1">
			<tr>
				<td>writer</td>
				<td>${boardDTO.writer }</td>
			</tr>
			<tr>
				<td>written date</td>
				<td>${boardDTO.enrollDT }</td>
			</tr>
			<tr>
				<td>subject</td>
				<td>${boardDTO.subject }</td>
			</tr>
			<tr>
				<td>password</td>
				<td><input type="password" name="password"></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="hidden" name="boardId" value="${boardDTO.boardId }">
					<input type="submit" value="delete!">
					<input type="button" value="go list" onclick="location.href='bList'">
				</td>
			</tr>
		</table>
	
	</form>
</body>
</html>