<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="m" uri="/WEB-INF/taglib/Paginator.tld" %>
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

    <table class="table borderless">
        <tr>
            <td>id</td>
            <td>sum</td>
            <td>date time</td>
            <td>type</td>
            <td>details</td>
        </tr>
        <c:forEach items="${payments}" var="payment">
            <tr>
                <td>${payment.id}</td>
                <td>${payment.sum.toString()}</td>
                <td>${payment.date.toString()}</td>
                <td>${payment.tariff.type.typeName}</td>
                <td><a href="payments/${payment.id}"><button class="btn-primary">view</button></a></td>
            </tr>
        </c:forEach>
    </table>
    <m:display paginParamName="page" totalPages="${pages}"/>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>