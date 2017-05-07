<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/css/bootstrap.css"/>
    <link rel="stylesheet" href="/css/bootstrap-theme.css"/>

    <script src="/js/jquery-3.2.1.min.js" ></script>
    <script src="/js/bootstrap.js" ></script>
</head>
<body>
    <jsp:include page="header.jsp"></jsp:include>

    <table class="table borderless">
        <c:forEach items="${cards}" var="card">
            <tr>
                <td>${card.getCardNumber()}</td>
                <c:if test="${requestScope.get(card.id.toString())}">
                    <td><button class="btn-primary" id="${card.id}_button" name="${card.id}_button">unblock</button></td>
                </c:if>
                <%--<c:if test="${not requestScope.get(card.id.toString())}">
                    <td><a href="/admin/cards/unblock/${value.id}"><button class="btn-danger" id="${card.id}_button" name="${card.id}_button">block</button></a></td>
                </c:if>--%>
            </tr>
        </c:forEach>
    </table>

    <jsp:include page="footer.jsp"></jsp:include>

    <script type="text/javascript">
        $(document).on("click", ".btn-primary", function(){
            var but=$(this).attr('id').split('_')[0];

            $.ajax({
                type: "POST",
                url: "/admin/cards/unblock",
                data: {
                    cardID:but
                },
                success: function(){
                    var selector = "#"+but + "_button";
                    $(selector).hide();
                    alert('Card was successfully unblocked');
                },
                error:function () {
                    alert("Can't block card");
                }
            });
        });
    </script>
</body>
</html>