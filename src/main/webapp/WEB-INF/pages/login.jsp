<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <fmt:setLocale value="${sessionScope['locale']}"/>
    <fmt:requestEncoding value="UTF-8" />
    <fmt:setBundle basename="${sessionScope['bundleFile']}" var="msg"/>
    <meta charset="utf-8">
    <title>Title</title>
    <link rel="stylesheet" href="/css/login.css">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <script src="/js/jquery-3.2.1.min.js"></script>
    <script src="/js/login.js"></script>
    <script src="/js/login-form-initializer.js"></script>
    <script src="/js/main-menu.js"></script>
    <script src="/js/bootstrap.min.js"></script>
</head>
<body>

<div class="dropdown choose-country top-right">
    <c:forEach items="${SUPPORTED_LOCALES}" var="value">
        <c:if test="${value eq sessionScope['locale']}">
            <a class="#" data-toggle="dropdown" href="#"><img src="images/${value.language}.png" alt="${value.language}">  ${value.language}</a>
        </c:if>
    </c:forEach>
    <ul class="dropdown-menu" role="menu">
        <c:forEach items="${SUPPORTED_LOCALES}" var="value">
            <li role="menuitem"><a href="login?lang=${value.language}"><img src="images/${value.language}.png" alt="${value.language}"> ${value.language}</a></li>
        </c:forEach>
    </ul>
</div>

<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div>
                <c:if test="${requestScope.errors!=null and requestScope.errors.hasErrors()}">
                    <c:forEach items="${requestScope.errors.getErrorsAttributes()}" var="value">
                        <p1 class="has-error"><fmt:message key="${errors.errors.get(value)}" bundle="${msg}"/> </p1><br>
                    </c:forEach>
                </c:if>
            </div>

            <div class="panel panel-login">
                <div class="panel-heading">
                    <input id="tab" type="hidden" value="${requestScope.tab}">
                    <div class="row">
                        <div class="col-xs-6">
                            <a href="#" class="active" id="login-form-link"><fmt:message key="payments.login" bundle="${msg}"/></a>
                        </div>
                        <div class="col-xs-6">
                            <a href="#" id="register-form-link"><fmt:message key="payments.register" bundle="${msg}"/></a>
                        </div>
                    </div>
                    <hr>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <form id="login-form" action="/login" method="post" role="form" style="display: block;">
                                <div class="form-group">
                                    <input type="text" name="login_name" id="login_name" tabindex="1" class="form-control" placeholder="<fmt:message key="payments.placeholder.cellphone" bundle="${msg}"/>" value="${requestScope.previousLoginCellphone}" required>
                                </div>
                                <div class="form-group">
                                    <input type="password" name="login_password" id="login_password" tabindex="2" class="form-control" placeholder="<fmt:message key="payments.placeholder.password" bundle="${msg}"/>" value="${requestScope.previousLoginPassword}" required>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-login" value="<fmt:message key="payments.login" bundle="${msg}"/>">
                                        </div>
                                    </div>
                                </div>
                            </form>


                            <form id="register-form" action="/register" method="post" role="form" style="display: none;">
                                <div class="form-group">
                                    <input type="text" name="name" id="name" tabindex="1" class="form-control" placeholder="<fmt:message key="payments.placeholder.name" bundle="${msg}"/>" value="${requestScope.previousUserName}" required>
                                </div>
                                <div class="form-group">
                                    <input type="text" name="surname" id="surname" tabindex="1" class="form-control" placeholder="<fmt:message key="payments.placeholder.surname" bundle="${msg}"/>" value="${requestScope.previousUserSurname}" required>
                                </div>
                                <div class="form-group">
                                    <input type="date" name="birthDate" id="birthDate" tabindex="1" class="form-control" placeholder="Birth Date yyyy-mm-dd" value="${requestScope.previousUserDate}" required>
                                </div>
                                <div class="form-group">
                                    <input type="text" name="cellphone" id="cellphone" tabindex="1" class="form-control" placeholder="<fmt:message key="payments.placeholder.cellphone" bundle="${msg}"/>" value="${requestScope.previousUserCellphone}" required>
                                </div>
                                <div class="form-group">
                                    <input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="<fmt:message key="payments.placeholder.password" bundle="${msg}"/>" value="${requestScope.previousUserPassword}" required>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-register" value="<fmt:message key="payments.register" bundle="${msg}"/>">
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>