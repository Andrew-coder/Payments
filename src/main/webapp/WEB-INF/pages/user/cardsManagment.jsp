<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>cards managment</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/main.css">
    <script src="/js/jquery-3.2.1.min.js"></script>
    <script src="/js/ajax_block.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container">
    <a href="cards/add" class="btn btn-default active"><i class="glyphicon glyphicon-plus"></i> Add card</a>
    <table class="table borderless">
        <c:forEach items="${cards}" var="card">
            <tr>
                <td>${card.getCardNumber()}</td>
                <td><a href="/cards/refill/${card.getId()}"><button type="button" class="btn-primary" value="Refill">Refill</button></a></td>
                <td><button type="button" class="btn-danger" value="block" name="${card.getId()}_button" id="${card.getId()}_button">Block</button></td>
            </tr>
        </c:forEach>
    </table>
</div>
<jsp:include page="footer.jsp"/>

<script src="js/bootstrap.min.js"></script>
<script src="js/main-menu.js"></script>

</body>
</html>
