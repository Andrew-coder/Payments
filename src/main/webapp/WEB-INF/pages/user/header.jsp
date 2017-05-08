<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>

<fmt:setLocale value="${sessionScope['locale']}"/>
<fmt:requestEncoding value="UTF-8" />
<fmt:setBundle basename="${bundleFile}" var="msg"/>

<div class="mainmenu-wrapper">
    <div class="container">
        <div class="menuextras">
            <div class="extras">
                <ul>
                    <li>
                        <jsp:include page="../localeSelector.jsp"/>
                    </li>
                    <c:if test="${sessionScope.user==null}">
                        <li><a href="/login"><fmt:message key="payments.login" bundle="${msg}"/></a></li>
                    </c:if>
                    <c:if test="${sessionScope.user!=null}">
                        <li>${user.name} ${' '} ${user.surname}</li>
                        <li><a href="/logout"><fmt:message key="payments.logout" bundle="${msg}"/></a></li>
                    </c:if>
                </ul>
            </div>
        </div>
        <nav id="mainmenu" class="mainmenu">
                <ul>
                    <li>
                        <a href="/home"><fmt:message key="payments.menu.home" bundle="${msg}"/></a>
                    </li>
                    <li>
                        <a href="/cards"><fmt:message key="payments.menu.cards" bundle="${msg}"/></a>
                    </li>
                    <li>
                        <a href="/payments"><fmt:message key="payments" bundle="${msg}"/></a>
                    </li>
                </ul>
        </nav>
    </div>
</div>
