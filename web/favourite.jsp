<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: hieud
  Date: 10/31/2020
  Time: 4:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sản phẩm yêu thích</title>
</head>
<body>
<c:set value="${sessionScope.ROLE}" var="role"/>
<c:if test="${role != null}" var="checkRole">
    <c:if test="${role == 'admin'}">
        <c:redirect url="admin.jsp"/>
    </c:if>
</c:if>
<c:if test="${!checkRole}">
    <c:redirect url="index.jsp"/>
</c:if>

<a href="init" style="color: blue; text-decoration: none">Về trang chủ</a> <br/>
<a href="logout" style="color: blue; text-decoration: none; position: absolute; top: 0; right: 0">Đăng xuất</a>
<c:if test="${requestScope.INFO != null}">
    <c:if test="${not empty requestScope.INFO}" var="checkList">

        <br/> <br/>
        <form action="report" method="post">
            <input type="submit" value="Xuất báo cáo (pdf)"/>
        </form> <br/> <br/>

        <table border="1">
            <tr>
                <th>Số thứ tự</th>
                <th>Tên</th>
                <th>Hình ảnh</th>
                <th>Giá</th>
            </tr>
            <c:forEach items="${requestScope.INFO}" var="product" varStatus="counter">
                <c:url var="getProductDetail" value="detail">
                    <c:param name="productHash" value="${product.hash}"/>
                </c:url>
                <tr>
                    <td>${counter.count + requestScope.COUNTNO}</td>
                    <td>
                        <a href="${getProductDetail}" style="color: darkblue; text-decoration: none">${product.name}</a>
                    </td>
                    <td>
                        <a href="${getProductDetail}">
                            <img src="${product.imageUrl}" width="300" height="300"/>
                        </a>
                    </td>
                    <td>
                        <c:set value="${product.price}" var="price"/>
                        <c:if test="${price > 0}" var="checkCondition">
                            <fmt:formatNumber type="number" maxFractionDigits="0" value="${price}"/> đồng
                        </c:if>
                        <c:if test="${!checkCondition}">
                            Liên hệ
                        </c:if>
                    </td>
                    <td>
                        <c:url var="deteteLink" value="deleteFavourite">
                            <c:param name="productHash" value="${product.hash}"/>
                            <c:param name="pageNumber" value="${requestScope.CURRENTPAGE}"/>
                        </c:url>
                        <a href="${deteteLink}" style="color: red; text-decoration: none">Xoá khỏi danh sách</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${!checkList}">
        <h2 style="color: red">Bạn chưa thêm sản phẩm nào!</h2>
    </c:if>
    <c:if test="${requestScope.PREVIOUS != false}">
        <c:url value="search" var="prevPage">
            <c:param name="pageNumber" value="${requestScope.CURRENTPAGE - 1}"/>
        </c:url>
        <a href="${prevPage}" style="color: blue; text-decoration: none">Trước</a>
    </c:if>
    <c:if test="${requestScope.CURRENTPAGE > 0}">
        Trang ${requestScope.CURRENTPAGE}
    </c:if>
    <c:if test="${requestScope.NEXT != false}">
        <c:url value="search" var="nextPage">
            <c:param name="pageNumber" value="${requestScope.CURRENTPAGE + 1}"/>
        </c:url>
        <a href="${nextPage}" style="color: blue; text-decoration: none">Sau</a>
    </c:if>
</c:if>
</body>
</html>
