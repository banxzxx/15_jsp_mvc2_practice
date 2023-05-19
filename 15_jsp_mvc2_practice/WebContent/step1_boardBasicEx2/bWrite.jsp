<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>작성하기</title>
<script src="ckeditor/ckeditor.js"></script>
</head>
<body>

	<h3>작성하기</h3>
	<form action="bWrite" method="post">
		<table border="1">
			<tr>
				<td>writer</td>
				<td><input type="text" name="writer"><td>
			</tr>
			<tr>
				<td>subject</td>
				<td><input type="text" name="subject"><td>
			</tr>
			<tr>
				<td>email</td>
				<td><input type="text" name="email"><td>
			</tr>
			<tr>
				<td>password</td>
				<td><input type="password" name="password"><td>
			</tr>
			<tr>
				<td>글내용</td>
				<td>
					<textarea rows="10" cols="40" name="content"></textarea>
					<script>CKEDITOR.replace("content")</script>
				</td>
			</tr>
			<tr align="center">
				<td colspan="2">
				<input type="submit" value="글쓰기">
				</td>
			</tr>
		
		</table>
	
	
	</form>

</body>
</html>