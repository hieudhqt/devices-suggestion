<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<% pageContext.setAttribute("newLineChar", "\n"); %>
<%--
  Created by IntelliJ IDEA.
  User: hieud
  Date: 10/31/2020
  Time: 4:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chi tiết sản phẩm</title>
    <script type="text/javascript">

        var count = 0;
        var cells = [];
        var tableId = 'suggestProducts';
        var tmpDom = null;
        var parser = new DOMParser();
        var xmlDom = null;

        function suggest(tableId) {
            var select = document.getElementById("room-option");
            var option = select.options[select.selectedIndex];
            tmpDom = option.getAttribute("data-support");
            xmlDom = parser.parseFromString(tmpDom, "text/xml");

            deleteRow(tableId);
            initiateTable(tableId);
            getAll(xmlDom);
        }

        function initiateTable(tableId) {
            var tableElement = document.getElementById(tableId);
            var imageRow = tableElement.insertRow();
            imageRow.setAttribute("id", "imageRow");
            var nameRow = tableElement.insertRow();
            nameRow.setAttribute("id", "nameRow");
            var priceRow = tableElement.insertRow();
            priceRow.setAttribute("id", "priceRow");

            return tableElement;
        }

        function addRow(cells) {
            var imageRow = document.getElementById("imageRow");
            var nameRow = document.getElementById("nameRow");
            var priceRow = document.getElementById("priceRow");

            var imgHrefElement = document.createElement("a");
            imgHrefElement.setAttribute("href", "detail?productHash=" + cells[0]);
            var imgElement = document.createElement("img");
            imgElement.setAttribute("src", cells[1]);
            imgElement.setAttribute("width", "200px");
            imgElement.setAttribute("height", "200px");
            var imgCell = document.createElement("td");
            imgHrefElement.appendChild(imgElement);
            imgCell.appendChild(imgHrefElement);

            imageRow.appendChild(imgCell);

            var newNameCell = nameRow.insertCell();
            var nameHrefElement = document.createElement("a");
            nameHrefElement.setAttribute("href", "detail?productHash=" + cells[0]);
            nameHrefElement.setAttribute("style", "color: blue; text-decoration: none;");
            nameHrefElement.innerText = cells[2];
            newNameCell.appendChild(nameHrefElement);

            var newPriceCell = priceRow.insertCell();
            newPriceCell.innerText = Number.parseFloat(cells[3]).toLocaleString() + " đồng";
        }

        function deleteRow(tableId) {
            var tableElement = document.getElementById(tableId);
            var tableRows = tableElement.getElementsByTagName("tr");
            var rowCount = tableRows.length;

            for (var i = rowCount - 1; i >= 0; i--) {
                tableElement.deleteRow(i);
            }
        }

        function getAll(node) {
            count++;
            if (node == null) return;
            if (node.tagName == "room_id") {
                var hash = node.nextSibling;
                var name = hash.nextSibling;
                var price = name.nextSibling;
                var url = price.nextSibling;
                var imageUrl = url.nextSibling;

                cells[0] = hash.firstChild.nodeValue;
                cells[1] = imageUrl.firstChild.nodeValue;
                cells[2] = name.firstChild.nodeValue;
                cells[3] = price.firstChild.nodeValue;

                addRow(cells);
            }
            var childs = node.childNodes;
            for (let i = 0; i < childs.length; i++) {
                getAll(childs[i]);
            }
        }

    </script>
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

<a href="init" style="color: blue; text-decoration: none">Về trang chủ</a>
<a href="logout" style="color: blue; text-decoration: none; position: absolute; top: 0; right: 0">Đăng xuất</a>
<br/> <br/>
<c:if test="${requestScope.DETAIL != null}">
    <c:if test="${requestScope.ADDED != true}" var="checkCondition">
        <form action="addFavourite" method="post">
            <input type="hidden" name="productHash" value="${requestScope.DETAIL.hash}"/>
            <input type="submit" value="Thêm vào yêu thích"/>
        </form>
    </c:if>
    <c:if test="${!checkCondition}">
        <p>Bạn đã thêm sản phẩm này rồi!</p>
    </c:if>
    <table border="1">
        <tr>
            <td>
                <b>
                    Tên
                </b>
            </td>
            <td>${requestScope.DETAIL.name}</td>
        </tr>
        <tr>
            <td>
                <b>Danh mục</b>
            </td>
            <td>
                    ${requestScope.DETAIL.categoryByCategoryHash.name}
            </td>
        </tr>
        <tr>
            <td>
                <b>Hình ảnh</b>
            </td>
            <td><img src="${requestScope.DETAIL.imageUrl}" alt="${requestScope.DETAIL.name}"/></td>
        </tr>
        <tr>
            <td>
                <b>Giá</b>
            </td>
            <td>
                <c:set value="${requestScope.DETAIL.price}" var="price"/>
                <c:if test="${price > 0}" var="checkCondition">
                    <fmt:formatNumber type="number" maxFractionDigits="0" value="${price}"/> đồng
                </c:if>
                <c:if test="${!checkCondition}">
                    Liên hệ
                </c:if>
            </td>
        </tr>
        <tr>
            <td>
                <b>Bảo hành</b>
            </td>
            <td>${requestScope.DETAIL.warranty}</td>
        </tr>
        <tr>
            <td style="width: 130px">
                <b>Chi tiết sản phẩm</b>
            </td>
            <td><p>${fn:replace(requestScope.DETAIL.description, newLineChar, "<br/>")}</p></td>
        </tr>
    </table>
    <br/> <br/>
    <c:if test="${requestScope.ROOMLIST != null}">
        <c:if test="${not empty requestScope.ROOMLIST}">
            Những sản phẩm có thể dùng trong <select id="room-option" onchange="suggest(tableId)">
            <c:set value="${requestScope.SUGGESTION}" var="mapping"/>
            <c:forEach items="${requestScope.ROOMLIST}" var="roomType" varStatus="counter">
                <c:set value="${mapping[roomType.id]}" var="resultObject"/>
                <option value="${roomType.id}"
                        data-support="<c:out value="${resultObject.xmlResult}"/>" left-limit="${resultObject.fromPrice}"
                        right-limit="${resultObject.toPrice}">${roomType.name}</option>
            </c:forEach>
        </select> <br/> <br/>
            <table border="1" id="suggestProducts">
            </table>
            <script type="text/javascript">
                suggest(tableId);
            </script>
        </c:if>
    </c:if>
</c:if>
</body>
</html>
