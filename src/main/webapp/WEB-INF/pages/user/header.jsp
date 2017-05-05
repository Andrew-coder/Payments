<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="mainmenu-wrapper">
    <div class="container">
        <div class="menuextras">
            <div class="extras">
                <ul>
                    <li>
                        <jsp:include page="localeSelector.jsp"/>
                    </li>
                    <c:if test="${sessionScope.user==null}">
                        <li><a href="/login">Login</a></li>
                    </c:if>
                    <c:if test="${sessionScope.user!=null}">
                        <li>${user.name} ${' '} ${user.surname}</li>
                        <li><a href="/logout">Logout</a></li>
                    </c:if>
                </ul>
            </div>
        </div>
        <nav id="mainmenu" class="mainmenu">
                <ul>
                    <li>
                        <a href="/home">Home</a>
                    </li>
                    <li>
                        <a href="/cards">cards</a>
                    </li>
                    <li>
                        <a href="/payments">payments</a>
                    </li>
                </ul>
        </nav>
    </div>
</div>
