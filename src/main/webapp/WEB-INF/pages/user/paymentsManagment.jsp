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
    <link rel="stylesheet" href="/css/bootstrap.min.css">

    <link rel="stylesheet" href="/css/main.css">

    <script src="/js/jquery-3.2.1.min.js"></script>
    <script src="/js/form-slider.js"></script>
    <script src="/js/payments-form-initializer.js"></script>
</head>
<body>

<div class="mainmenu-wrapper">
    <div class="container">
        <div class="menuextras">
            <div class="extras">
                <ul>
                    <li>

                        <div class="dropdown choose-country">
                            <c:forEach items="${SUPPORTED_LOCALES}" var="value">
                                <c:if test="${value eq sessionScope['locale']}">
                                    <a class="#" data-toggle="dropdown" href="#"><img src="images/${value.language}.png" alt="${value.language}">  ${value.language}</a>
                                </c:if>
                            </c:forEach>
                            <ul class="dropdown-menu" role="menu">
                                <c:forEach items="${SUPPORTED_LOCALES}" var="value">
                                    <li role="menuitem"><a href="payments?lang=${value.language}"><img src="images/${value.language}.png" alt="${value.language}"> ${value.language}</a></li>
                                </c:forEach>
                            </ul>
                        </div>

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

<div class="container">
    <div class="row">
        <div class="container">
            <div class="container">
                <div>
                    <c:if test="${requestScope.errors!=null and requestScope.errors.hasErrors()}">
                        <c:forEach items="${requestScope.errors.getErrorsAttributes()}" var="value">
                            <p1 class="has-error"><fmt:message key="${requestScope.errors.getErrors().get(value)}" bundle="${msg}"/> </p1><br>
                        </c:forEach>
                    </c:if>
                </div>

                <div class="radio">
                    <a href="#" class="active" id="card-transfer"><label><input id="card" type="radio" class="radio" name="optradio" checked="checked"><fmt:message key="payments.transfer.card" bundle="${msg}"/></label></a>
                </div>
                <div class="radio">
                    <a href="#" id="account-transfer"><label><input id="account" type="radio" class="radio" name="optradio"><fmt:message key="payments.transfer.account" bundle="${msg}"/></label></a>
                </div>

                <input id="tab" type="hidden" value="${requestScope.tab}">

                <div class="panel panel-body">
                    <div class="row">

                            <form id="card-pay-form" action="/payments/card" method="post" role="form" >
                                <div class="form-group row">
                                    <label class="col-2 col-form-label"><fmt:message key="payments.from.card" bundle="${msg}"/></label>
                                    <div class="col-10">
                                        <select name="cards" class="form-control">
                                            <c:if test="${cards==null || cards.isEmpty()}">
                                                <c:if test="${requestScope.errors!=null and requestScope.errors.hasErrors()}">
                                                    <option>${requestScope.previousCardNumber}</option>
                                                </c:if>
                                                <c:if test="${requestScope.errors==null}">
                                                    <option><fmt:message key="payments.no.cards" bundle="${msg}"/></option>
                                                </c:if>
                                            </c:if>
                                            <c:forEach items="${cards}" var="card">
                                                <option value="${card.cardNumber}">${card.cardNumber}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-2 col-form-label"><fmt:message key="payments.to.card" bundle="${msg}"/></label>
                                    <div class="col-10">
                                        <input type="text" name="target_card" id="target_card" class="form-control" placeholder="<fmt:message key="payments.cards.recipient" bundle="${msg}"/> " value="${requestScope.previousTargetCard}">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-2 col-form-label"><fmt:message key="payments.placeholder.sum" bundle="${msg}"/></label>
                                    <div class="col-10">
                                        <input type="text" name="sum" class="form-control" value="${requestScope.previousSum}">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-2 col-form-label"><fmt:message key="payments.placeholder.purpose" bundle="${msg}"/></label>
                                    <div class="col-10">
                                        <input type="text" name="purpose" class="form-control" value="${requestScope.previousPurpose}">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="pay-submit" id="pay-submit" tabindex="4" class="form-control btn" value="<fmt:message key="payments.pay" bundle="${msg}"/>">
                                        </div>
                                    </div>
                                </div>
                            </form>



                            <form id="account-form-pay" action="/payments/account" method="post" role="form" style="display: none;">
                                <div class="form-group row">
                                    <label class="col-2 col-form-label"><fmt:message key="payments.from.card" bundle="${msg}"/></label>
                                    <div class="col-10">
                                        <select name="cards" class="form-control">
                                            <c:if test="${cards==null || cards.isEmpty()}">
                                                <c:if test="${requestScope.errors!=null and requestScope.errors.hasErrors()}">
                                                    <option>${requestScope.previousCardNumber}</option>
                                                </c:if>
                                                <c:if test="${requestScope.errors==null}">
                                                    <option><fmt:message key="payments.no.cards" bundle="${msg}"/></option>
                                                </c:if>
                                            </c:if>
                                            <c:forEach items="${cards}" var="card">
                                                <option value="${card.cardNumber}">${card.cardNumber}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-2 col-form-label"><fmt:message key="payments.placeholder.mfo" bundle="${msg}"/></label>
                                    <div class="col-10">
                                        <input type="text" name="mfo" class="form-control" value="${requestScope.previousMfo}">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-2 col-form-label"><fmt:message key="payments.placeholder.usreou" bundle="${msg}"/></label>
                                    <div class="col-10">
                                        <input type="text" name="usreou" class="form-control" value="${requestScope.previousUsreou}">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-2 col-form-label"><fmt:message key="payments.placeholder.account" bundle="${msg}"/></label>
                                    <div class="col-10">
                                        <input type="text" name="account_number" class="form-control" value="${requestScope.previousAccount}">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-2 col-form-label"><fmt:message key="payments.placeholder.sum" bundle="${msg}"/></label>
                                    <div class="col-10">
                                        <input type="text" name="sum" class="form-control" value="${requestScope.previousSum}">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-2 col-form-label"><fmt:message key="payments.placeholder.purpose" bundle="${msg}"/></label>
                                    <div class="col-10">
                                        <input type="text" name="purpose" class="form-control" value="${requestScope.previousPurpose}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn" value="<fmt:message key="payments.pay" bundle="${msg}"/> ">
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

<jsp:include page="footer.jsp"/>

<script src="js/bootstrap.min.js"></script>
<script src="js/main-menu.js"></script>
<script src="js/template.js"></script>
</body>
</html>