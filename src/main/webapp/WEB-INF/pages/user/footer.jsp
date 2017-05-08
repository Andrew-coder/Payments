<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>

<fmt:setLocale value="${sessionScope['locale']}"/>
<fmt:requestEncoding value="UTF-8" />
<fmt:setBundle basename="${bundleFile}" var="msg"/>
<!-- Footer -->
<div class="footer" style="position:absolute; bottom:0; width: 100%; position: fixed;">
    <div class="container">

        <div class="row">
            <div class="col-md-12">
                <div class="footer-copyright"><fmt:message key="payments.copyright" bundle="${msg}"/></div>
            </div>
        </div>
    </div>
</div>