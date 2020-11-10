<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: hieud
  Date: 10/30/2020
  Time: 4:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Devices Suggestion</title>
    <link rel="stylesheet" href="slider.css"/>
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

<h1>XIN CHÀO, ${sessionScope.NAME}</h1>
<c:url value="showFavourite" var="favouriteLink">
    <c:param name="pageNumber" value="1"/>
</c:url>
<a href="${favouriteLink}" style="color: blue; text-decoration: none">Sản phẩm đã được quan tâm</a>
<a href="logout" style="color: blue; text-decoration: none; position: absolute; top: 0; right: 0">Đăng xuất</a>

<c:if test="${requestScope.CATELIST != null}">
    <form action="search" method="post">
        <p>Bạn muốn tìm thiết bị thuộc danh mục nào?</p>
        <select name="categoryHash">
            <c:forEach items="${requestScope.CATELIST}" var="category">
                <option value="${category.hash}" ${category.hash == requestScope.SELECTED ? 'selected="selected"' : ''}>${category.name}</option>
            </c:forEach>
        </select> <br/> <br/>

        <c:set var="savedFromPrice" value="${param.fromPrice == null ? 0 : param.fromPrice}"/>
        <c:set var="savedToPrice" value="${param.toPrice == null ? 5000000 : param.toPrice}"/>

        <div class="price-slider">
        <span>Giá từ <input type="number" value="${savedFromPrice}" min="${requestScope.MINPRICE}"
                            max="${requestScope.MAXPRICE}" name="fromPrice" style="width: 7em;" readonly/> đến <input
                type="number" value="${savedToPrice}" min="${requestScope.MINPRICE}" max="${requestScope.MAXPRICE}"
                name="toPrice" style="width: 7em;" readonly/> đồng</span>
            <input value="${savedFromPrice}" min="${requestScope.MINPRICE}" max="${requestScope.MAXPRICE}" step="100000"
                   type="range"/>
            <input value="${savedToPrice}" min="${requestScope.MINPRICE}" max="${requestScope.MAXPRICE}" step="100000"
                   type="range"/>
            <svg width="100%" height="25">
                <line x1="4" y1="0" x2="600" y2="0" stroke="#212121" stroke-width="20" stroke-dasharray="1 20"></line>
            </svg>
        </div>
        <br/>

        <script src="slider.js"></script>
        <input type="hidden" name="pageNumber" value="1"/>
        <input type="submit" value="Tìm"/>
    </form>
</c:if>
<c:if test="${requestScope.PRODUCTLIST != null}">
    <c:if test="${not empty requestScope.PRODUCTLIST}">
        <h2>Những sản phẩm được quan tâm nhiều nhất</h2>
        <table border="1">
            <c:forEach begin="0" end="2" varStatus="loop">
                <tr>
                    <c:forEach items="${requestScope.PRODUCTLIST}" var="product">
                        <c:url var="getFavouriteProductDetail" value="detail">
                            <c:param name="productHash" value="${product.hash}"/>
                        </c:url>
                        <td>
                            <c:if test="${loop.count == 1}">
                                <a href="${getFavouriteProductDetail}">
                                    <img src="${product.imageUrl}" alt="${product.name}" width="200px" height="200px"/>
                                </a>
                            </c:if>
                            <c:if test="${loop.count == 2}">
                                <a href="${getFavouriteProductDetail}" style="color: blue; text-decoration: none">${product.name}</a>
                            </c:if>
                            <c:if test="${loop.count == 3}">
                                <fmt:formatNumber type="number" maxFractionDigits="0" value="${product.price}"/> đồng
                            </c:if>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</c:if>
<c:if test="${requestScope.INFO != null}">
    <c:if test="${not empty requestScope.INFO}" var="checkList">
        <p>Tìm thấy <span style="color: green">${requestScope.MAXRESULT}</span> sản phẩm </p>
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
                            <img src="${product.imageUrl}" alt="${product.name}" width="300" height="300"/>
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
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${!checkList}">
        <h2 style="color: red">Không có sản phẩm nào</h2>
    </c:if>
    <c:if test="${requestScope.PREVIOUS != false}">
        <c:url value="search" var="prevPage">
            <c:param name="pageNumber" value="${requestScope.CURRENTPAGE - 1}"/>
            <c:param name="categoryHash" value="${requestScope.SELECTED}"/>
            <c:param name="fromPrice" value="${param.fromPrice}"/>
            <c:param name="toPrice" value="${param.toPrice}"/>
        </c:url>
        <a href="${prevPage}" style="color: blue; text-decoration: none">Trước</a>
    </c:if>
    <c:if test="${requestScope.CURRENTPAGE > 0}">
        Trang ${requestScope.CURRENTPAGE}
    </c:if>
    <c:if test="${requestScope.NEXT != false}">
        <c:url value="search" var="nextPage">
            <c:param name="pageNumber" value="${requestScope.CURRENTPAGE + 1}"/>
            <c:param name="categoryHash" value="${requestScope.SELECTED}"/>
            <c:param name="fromPrice" value="${param.fromPrice}"/>
            <c:param name="toPrice" value="${param.toPrice}"/>
        </c:url>
        <a href="${nextPage}" style="color: blue; text-decoration: none">Sau</a>
    </c:if>
</c:if>
</body>
</html>
