<%--
  Created by IntelliJ IDEA.
  User: hieud
  Date: 10/30/2020
  Time: 12:00 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ERROR PAGE</title>
</head>
<body>
<a href="index.jsp" style="color: blue; text-decoration: none">Quay về trang index</a>
<a href="logout" style="color: blue; text-decoration: none; position: absolute; top: 0; right: 0">Đăng xuất user ${sessionScope.USERNAME}</a>
<h2 style="color: red">${requestScope.ERROR}</h2>
<h2 style="color: red">${requestScope.INVALID}</h2>
</body>
</html>
