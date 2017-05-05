<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Admin panel</a>
        </div>
        <ul class="nav navbar-nav">
            <li ><a href="${pageContext.request.contextPath}/admin/cards">Cards</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/payments">Payments</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/tariffs">Tariffs</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/users">Users</a></li>
        </ul>

        <ul class="nav navbar-nav navbar-right">
            <li class="right"><a href="/logout" >Logout</a></li>
        </ul>
    </div>
</nav>