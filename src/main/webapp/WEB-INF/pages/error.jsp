<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope['locale']}"/>
    <fmt:requestEncoding value="UTF-8" />
    <fmt:setBundle basename="${sessionScope['bundleFile']}" var="msg"/>
    <meta charset="utf-8">
    <title>Error</title>
</head>
<body>
    <div class="errors" align="center">
        <c:if test="${requestScope.errors!=null and requestScope.errors.hasErrors()}">
            <c:forEach items="${requestScope.errors.getErrorsAttributes()}" var="value">
                <p1 class="has-error"><fmt:message key="${requestScope.errors.getErrors().get(value)}" bundle="${msg}"/> </p1><br>
            </c:forEach>
        </c:if>
    </div>
</body>
</html>