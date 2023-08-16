<%--
  Created by IntelliJ IDEA.
  User: binh1
  Date: 8/15/2023
  Time: 3:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
</head>
<body>
<form action="/upload" method="post" enctype="multipart/form-data">
	<input type="file" name="urls" multiple>
	<button type="submit">UPLOAD</button>
</form>
</body>
</html>
