<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">

    <link rel="stylesheet" href="/css/main.css">

    <script src="/js/jquery-3.2.1.min.js"></script>
    <script src="/js/form-slider.js"></script>
    <script src="/js/payments-form-initializer.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>

<div class="container">
    <div class="row">
        <div class="container">
            <div class="container">
                <div>
                    <c:if test="${requestScope.errors!=null and requestScope.errors.hasErrors()}">
                        <c:forEach items="${requestScope.errors.getErrorsAttributes()}" var="value">
                            <p1 class="has-error">${requestScope.errors.getErrors().get(value)}</p1><br>
                        </c:forEach>
                    </c:if>
                </div>

                <div class="radio">
                    <a href="#" class="active" id="card-transfer"><label><input id="card" type="radio" class="radio" name="optradio" checked="checked">Card transfer</label></a>
                </div>
                <div class="radio">
                    <a href="#" id="account-transfer"><label><input id="account" type="radio" class="radio" name="optradio">Account transfer</label></a>
                </div>

                <input id="tab" type="hidden" value="${requestScope.tab}">

                <div class="panel panel-body">
                    <div class="row">

                            <form id="card-pay-form" action="/payments/card" method="post" role="form" >
                                <div class="form-group row">
                                    <label class="col-2 col-form-label">From card</label>
                                    <div class="col-10">
                                        <select name="cards" class="form-control">
                                            <c:if test="${cards==null || cards.isEmpty()}">
                                                <c:if test="${requestScope.errors!=null and requestScope.errors.hasErrors()}">
                                                    <option>${requestScope.previousCardNumber}</option>
                                                </c:if>
                                                <c:if test="${requestScope.errors==null}">
                                                    <option>You have no any cards</option>
                                                </c:if>
                                            </c:if>
                                            <c:forEach items="${cards}" var="card">
                                                <option value="${card.cardNumber}">${card.cardNumber}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-2 col-form-label">On the card</label>
                                    <div class="col-10">
                                        <input type="text" name="target_card" id="target_card" class="form-control" placeholder="recipient's card" value="${requestScope.previousTargetCard}">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-2 col-form-label">Sum</label>
                                    <div class="col-10">
                                        <input type="text" name="sum" class="form-control" value="${requestScope.previousSum}">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-2 col-form-label">Purpose of payment</label>
                                    <div class="col-10">
                                        <input type="text" name="purpose" class="form-control" value="${requestScope.previousPurpose}">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="pay-submit" id="pay-submit" tabindex="4" class="form-control btn" value="Pay">
                                        </div>
                                    </div>
                                </div>
                            </form>



                            <form id="account-form-pay" action="/payments/account" method="post" role="form" style="display: none;">
                                <div class="form-group row">
                                    <label class="col-2 col-form-label">From card</label>
                                    <div class="col-10">
                                        <select name="cards" class="form-control">
                                            <c:if test="${cards==null || cards.isEmpty()}">
                                                <c:if test="${requestScope.errors!=null and requestScope.errors.hasErrors()}">
                                                    <option>${requestScope.previousCardNumber}</option>
                                                </c:if>
                                                <c:if test="${requestScope.errors==null}">
                                                    <option>You have no any cards</option>
                                                </c:if>
                                            </c:if>
                                            <c:forEach items="${cards}" var="card">
                                                <option value="${card.cardNumber}">${card.cardNumber}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-2 col-form-label">mfo</label>
                                    <div class="col-10">
                                        <input type="text" name="mfo" class="form-control" value="${requestScope.previousMfo}">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-2 col-form-label">usreou</label>
                                    <div class="col-10">
                                        <input type="text" name="usreou" class="form-control" value="${requestScope.previousUsreou}">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-2 col-form-label">account number</label>
                                    <div class="col-10">
                                        <input type="text" name="account_number" class="form-control" value="${requestScope.previousAccount}">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-2 col-form-label">Sum</label>
                                    <div class="col-10">
                                        <input type="text" name="sum" class="form-control" value="${requestScope.previousSum}">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-2 col-form-label">Purpose of payment</label>
                                    <div class="col-10">
                                        <input type="text" name="purpose" class="form-control" value="${requestScope.previousPurpose}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn" value="Pay">
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
