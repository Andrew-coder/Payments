<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope['locale']}"/>
    <fmt:requestEncoding value="UTF-8" />
    <fmt:setBundle basename="${bundleFile}" var="msg"/>
    <title>Title</title>
    <link rel="stylesheet" href="/css/bootstrap.css"/>
    <link rel="stylesheet" href="/css/bootstrap-theme.css"/>
    <link rel="stylesheet" href="/css/main.css">
    <script src="/js/jquery-3.2.1.min.js" ></script>
    <script src="/js/bootstrap.js" ></script>
</head>
<body>
    <jsp:include page="header.jsp"></jsp:include>
    <div class="container">
        <div>
            <c:if test="${requestScope.errors!=null and requestScope.errors.hasErrors()}">
                <c:forEach items="${requestScope.errors.getErrorsAttributes()}" var="value">
                    <p1 class="has-error"><fmt:message key="${requestScope.errors.getErrors().get(value)}" bundle="${msg}"/> </p1><br>
                </c:forEach>
            </c:if>
        </div>
        <form action="/cards/refill" method="post">
            <input name="id" type="hidden" value="${requestScope.card}">
            <div class="form-group row">
                <label class="col-2 col-form-label"><fmt:message key="payments.placeholder.card.number" bundle="${msg}"/></label>
                <div class="col-10">
                    <input type="text" name="card_number" id="card_number" class="form-control" placeholder="<fmt:message key="payments.placeholder.card.number" bundle="${msg}"/>" value="${requestScope.previousCardNumber}" required>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-2 col-form-label"><fmt:message key="payments.placeholder.pin" bundle="${msg}"/></label>
                <div class="col-10">
                    <input type="text" name="card_pin" id="card_pin" class="form-control" placeholder="<fmt:message key="payments.placeholder.pin" bundle="${msg}"/>" value="${requestScope.previousPin}" required>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-2 col-form-label"><fmt:message key="payments.placeholder.cvv" bundle="${msg}"/></label>
                <div class="col-10">
                    <input type="text" name="card_cvv" id="card_cvv" class="form-control" placeholder="<fmt:message key="payments.placeholder.cvv" bundle="${msg}"/>" value="${requestScope.previousCvv}" required>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-2 col-form-label"><fmt:message key="payments.placeholder.expire.date" bundle="${msg}"/></label>
                <div class="col-10">
                    <input type="date" name="card_expire" id="card_expire" class="form-control" placeholder="<fmt:message key="payments.placeholder.expire.date" bundle="${msg}"/>" value="${requestScope.previousExpireDate}" required>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-2 col-form-label"><fmt:message key="payments.placeholder.sum" bundle="${msg}"/></label>
                <div class="col-10">
                    <input type="text" name="sum" id="sum" class="form-control" placeholder="<fmt:message key="payments.placeholder.sum" bundle="${msg}"/>" value="${requestScope.previousSum}" required>
                </div>
            </div>

            <div class="form-group row">
                <div class="row">
                    <div class="col-sm-6 col-sm-offset-3">
                        <input type="submit" name="refill-submit" id="refill-submit" tabindex="4" class="form-control btn" value="<fmt:message key="payments.cards.refill" bundle="${msg}"/>">
                    </div>
                </div>
            </div>
        </form>
    </div>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>