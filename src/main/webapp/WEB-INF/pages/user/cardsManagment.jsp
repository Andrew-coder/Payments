<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope['locale']}"/>
    <fmt:requestEncoding value="UTF-8" />
    <fmt:setBundle basename="${bundleFile}" var="msg"/>
    <title>cards management</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/main.css">
    <script src="/js/jquery-3.2.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container">
    <table class="table borderless">
        <c:forEach items="${cards}" var="card">
            <tr>
                <td>${"**** **** **** "}${card.getCardNumber().substring(12,16)}</td>
                <td><a href="/cards/refill/${card.getId()}"><button type="button" class="btn-primary" value="<fmt:message key="payments.cards.refill" bundle="${msg}"/>"><fmt:message key="payments.cards.refill" bundle="${msg}"/></button></a></td>
                <td><button type="button" class="btn-danger" value="<fmt:message key="payments.cards.block" bundle="${msg}"/>" name="${card.getId()}_button" id="${card.getId()}_button"><fmt:message key="payments.cards.block" bundle="${msg}"/></button></td>
            </tr>
        </c:forEach>
    </table>
</div>
<jsp:include page="footer.jsp"/>

<script type="text/javascript">
    $(document).on("click", ".btn-danger", function(){
        var but=$(this).attr('id').split('_')[0];

        $.ajax({
            type: "POST",
            url: "/cards/block",
            data: {
                cardID:but
            },
            success: function(){
                var selector = "#"+but + "_button";
                $(selector).attr("disabled",true);
                alert('<fmt:message key="payments.successful.card.block" bundle="${msg}"/> ');
            },
            error:function () {
                alert("<fmt:message key="payments.unsuccessful.card.block" bundle="${msg}"/>");
            }
        });
    });
</script>

</body>
</html>