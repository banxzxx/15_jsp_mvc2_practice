<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>update</title>
<script src="ckeditor/ckeditor.js"></script>
</head>
<body>
	<h1>게시글 수정하기</h1>
	<form action="bUpdate" method="post">
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
				<td><input type="text" value="${boardDTO.subject }" name="subject"></td>
			</tr>
			<tr>
				<td>password</td>
				<td><input type="password" name="password"></td>
			</tr>
			<tr>
				<td>content</td>
				<td>
					<textarea rows="10" cols="50" name="content">${boardDTO.content }</textarea>
					<script>CKEDITOR.replace("content");</script>
				</td>
			</tr>
			<tr align="center">
				<td colspan="2">
					<input type="hidden" name="boardId" value="${boardDTO.boardId }">
					<input type="submit" value="replace!">
					<input type="button" value="go list" onclick="location.href='bList'">
				</td>
			</tr>
		
		</table>
		
	</form>

</body>
</html>