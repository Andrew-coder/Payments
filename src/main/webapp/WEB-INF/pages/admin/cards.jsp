<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/css/bootstrap.css"/>
    <link rel="stylesheet" href="/css/bootstrap-theme.css"/>

    <script src="/js/jquery-3.2.1.min.js" ></script>
    <script src="/js/bootstrap.js" ></script>
</head>
<body>
    <jsp:include page="header.jsp"></jsp:include>

    <a href="cards/add" class="btn btn-default active"><i class="glyphicon glyphicon-plus"></i> Add card</a>

    <table class="table borderless">
        <c:forEach items="${cards}" var="card">
            <tr>
                <td>${card.getCardNumber()}</td>
                <c:if test="${requestScope.get(card.id.toString())}">
                    <td><a href="/admin/cards/block/${value.id}"><button class="btn-danger">unblock</button></a></td>
                </c:if>
                <c:if test="${not requestScope.get(card.id.toString())}">
                    <td><a href="/admin/cards/unblock/${value.id}"><button class="btn-primary">block</button></a></td>
                </c:if>
            </tr>
        </c:forEach>
    </table>

    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>