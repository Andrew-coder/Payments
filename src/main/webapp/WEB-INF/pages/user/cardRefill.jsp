<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/main.css">
    <script src="/js/jquery-3.2.1.min.js"></script>
</head>
<body>
    <div class="container">
        <div>
            <c:if test="${requestScope.errors!=null and requestScope.errors.hasErrors()}">
                <c:forEach items="${requestScope.errors.getErrorsAttributes()}" var="value">
                    <p1 class="has-error">${requestScope.errors.getErrors().get(value)}</p1><br>
                </c:forEach>
            </c:if>
        </div>
        <form action="/cards/refill" method="post">
            <input name="id" type="hidden" value="${requestScope.card}">
            <div class="form-group row">
                <label class="col-2 col-form-label">Card number</label>
                <div class="col-10">
                    <input type="text" name="card_number" id="card_number" class="form-control" placeholder="card number" value="${requestScope.previousCardNumber}">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-2 col-form-label">pin</label>
                <div class="col-10">
                    <input type="text" name="card_pin" id="card_pin" class="form-control" placeholder="pin" value="${requestScope.previousPin}">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-2 col-form-label">cvv</label>
                <div class="col-10">
                    <input type="text" name="card_cvv" id="card_cvv" class="form-control" placeholder="cvv" value="${requestScope.previousCvv}">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-2 col-form-label">expire date</label>
                <div class="col-10">
                    <input type="date" name="card_expire" id="card_expire" class="form-control" placeholder="expire date" value="${requestScope.previousExpireDate}">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-2 col-form-label">sum</label>
                <div class="col-10">
                    <input type="text" name="sum" id="sum" class="form-control" placeholder="sum" value="${requestScope.previousSum}">
                </div>
            </div>

            <div class="form-group row">
                <div class="row">
                    <div class="col-sm-6 col-sm-offset-3">
                        <input type="submit" name="refill-submit" id="refill-submit" tabindex="4" class="form-control btn" value="Refill">
                    </div>
                </div>
            </div>
        </form>
    </div>
</body>
</html>
