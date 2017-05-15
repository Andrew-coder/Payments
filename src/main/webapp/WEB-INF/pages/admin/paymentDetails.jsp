<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="m" uri="/WEB-INF/taglib/Paginator.tld" %>
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

    <script src="/js/jquery-3.2.1.min.js" ></script>
    <script src="/js/bootstrap.js" ></script>
    <script src="js/main-menu.js"></script>
</head>
<body>
    <jsp:include page="header.jsp"></jsp:include>
    <table class="table borderless">
        <tr>
            <td><fmt:message key="payments.payment.id" bundle="${msg}"/></td>
            <td>${payment.id}</td>
        </tr>
        <tr>
            <td><fmt:message key="payments.payment.sender" bundle="${msg}"/></td>
            <td>${payment.sender.accountNumber}</td>
        </tr>
        <tr>
            <td><fmt:message key="payments.payment.recipient" bundle="${msg}"/></td>
            <td>${payment.recipient.accountNumber}</td>
        </tr>
        <tr>
            <td><fmt:message key="payments.payment.sum" bundle="${msg}"/></td>
            <td>${payment.sum.toString()}</td>
        </tr>
        <tr>
            <td><fmt:message key="payments.payment.time" bundle="${msg}"/></td>
            <td>${payment.date.toString()}</td>
        </tr>
        <tr>
            <td><fmt:message key="payments.placeholder.mfo" bundle="${msg}"/></td>
            <td>${payment.mfo}</td>
        </tr>
        <tr>
            <td><fmt:message key="payments.placeholder.usreou" bundle="${msg}"/></td>
            <td>${payment.usreou}</td>
        </tr>
        <tr>
            <td><fmt:message key="payments.payment.type" bundle="${msg}"/></td>
            <td>${payment.tariff.type.typeName}</td>
        </tr>
        <tr>
            <td><fmt:message key="payments.payment.purpose" bundle="${msg}"/></td>
            <td>${payment.paymentPurpose}</td>
        </tr>
    </table>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>