<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: hieud
  Date: 11/7/2020
  Time: 10:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Devices Suggestion</title>
</head>
<body>
<c:set value="${sessionScope.ROLE}" var="role"/>
<c:if test="${role != null}" var="checkRole">
    <c:if test="${role == 'user'}">
        <c:redirect url="init"/>
    </c:if>
</c:if>
<c:if test="${!checkRole}">
    <c:redirect url="index.jsp"/>
</c:if>

<h1>XIN CHÀO, ADMIN</h1>
<a href="logout" style="color: blue; text-decoration: none; position: absolute; top: 0; right: 0">Đăng xuất</a>
<form action="crawlTechbox" method="post">
    <input type="submit" value="Crawl Techbox Page"/>
</form>
<form action="crawlBoba" method="post">
    <input type="submit" value="Crawl Boba Page"/>
</form>
</body>
</html>
