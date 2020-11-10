<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: hieud
  Date: 10/14/2020
  Time: 4:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Devices Suggestion</title>
  </head>
  <body>
  <h1>Devices Suggestion</h1>
  <c:set value="${sessionScope.ROLE}" var="role"/>
  <c:if test="${role != null}">
    <c:if test="${role == 'user'}">
      <c:redirect url="init"/>
    </c:if>
    <c:if test="${role == 'admin'}">
      <c:redirect url="admin.jsp"/>
    </c:if>
  </c:if>
  <form action="login" method="post">
    Tài khoản: <input type="text" name="txtUsername" placeholder="Nhập tên tài khoản" required oninvalid="this.setCustomValidity('Yêu cầu nhập tên tài khoản')" oninput="setCustomValidity('')"/> <br/>
    Mật khẩu: <input type="password" name="txtPassword" placeholder="Nhập mật khẩu" required oninvalid="this.setCustomValidity('Yêu cầu nhập mật khẩu')" oninput="setCustomValidity('')"/> <br/>
    <input type="submit" value="Đăng nhập"/>
  </form>
  <h2 style="color: red">${requestScope.UNAUTHENTICATED}</h2>
  <h2 style="color: red">${requestScope.UNAUTHORIZED}</h2>
  </body>
</html>
