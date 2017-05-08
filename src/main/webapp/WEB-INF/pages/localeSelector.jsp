<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fmr" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="payments.utils.constants.Attributes" %>
<%@ page import="payments.controller.i18n.LocaleHolder" %>
<%@ page import="payments.utils.constants.PagesPath" %>

<fmt:setLocale value="${sessionScope['locale']}"/>
<fmt:requestEncoding value="UTF-8" />
<fmt:setBundle basename="${bundleFile}" var="msg"/>

    <div class="dropdown choose-country">
        <c:forEach items="${SUPPORTED_LOCALES}" var="value">
            <c:if test="${value eq sessionScope['locale']}">
                <a class="#" data-toggle="dropdown" href="#"><img src="images/${value.language}.png" alt="${value.language}">  ${value.language}</a>
            </c:if>
        </c:forEach>
        <ul class="dropdown-menu" role="menu">
                <c:forEach items="${SUPPORTED_LOCALES}" var="value">
                    <li role="menuitem"><a href="?lang=${value.language}"><img src="images/${value.language}.png" alt="${value.language}"> ${value.language}</a></li>
                </c:forEach>
        </ul>
    </div>