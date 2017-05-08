<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>

<fmt:setLocale value="${sessionScope['locale']}"/>
<fmt:requestEncoding value="UTF-8" />
<fmt:setBundle basename="${bundleFile}" var="msg"/>


<br>
<br>
<footer class="footer navbar-default navbar-fixed-bottom" style="position:absolute; bottom:0; width: 100%; position: fixed;">
    <div class="container-fluid footer" align="center">
        <span><fmt:message key="payments.copyright" bundle="${msg}"/></span>
    </div>
</footer>
