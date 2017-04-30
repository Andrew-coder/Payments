<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <div class="errors" align="center">
        <c:if test="${requestScope.errors!=null and requestScope.errors.hasErrors()}">
            <c:forEach items="${requestScope.errors.getErrorsAttributes()}" var="value">
                <p1 class="has-error">${requestScope.errors.getErrors().get(value)}</p1><br>
            </c:forEach>
        </c:if>
    </div>
</body>
</html>
