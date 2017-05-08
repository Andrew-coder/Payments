<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>

<fmt:setLocale value="${sessionScope['locale']}"/>
<fmt:requestEncoding value="UTF-8" />
<fmt:setBundle basename="${bundleFile}" var="msg"/>


<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#"><fmt:message key="payments.admin.panel" bundle="${msg}"/></a>
        </div>
        <ul class="nav navbar-nav">
            <li ><a href="${pageContext.request.contextPath}/admin/cards"><fmt:message key="payments.menu.cards" bundle="${msg}"/></a></li>
            <li><a href="${pageContext.request.contextPath}/admin/payments"><fmt:message key="payments" bundle="${msg}"/></a></li>
            <li><a href="${pageContext.request.contextPath}/admin/tariffs"><fmt:message key="payments.menu.tariffs" bundle="${msg}"/></a></li>
            <li><a href="${pageContext.request.contextPath}/admin/users"><fmt:message key="payments.menu.users" bundle="${msg}"/></a></li>
        </ul>

        <ul class="nav navbar-nav">
            <li class="right"><a href="/logout" ><fmt:message key="payments.logout" bundle="${msg}"/></a></li>
        </ul>
        <div class="nav navbar-nav navbar-right">
            <jsp:include page="../localeSelector.jsp"></jsp:include>
        </div>
    </div>
</nav>