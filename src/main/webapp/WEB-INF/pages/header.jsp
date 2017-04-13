<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="mainmenu-wrapper">
    <div class="container">
        <div class="menuextras">
            <div class="extras">
                <ul>
                    <li>
                        <jsp:include page="localeSelector.jsp"/>
                    </li>
                    <li><a href="/login">Login</a></li>
                </ul>
            </div>
        </div>
        <nav id="mainmenu" class="mainmenu">
                <ul>
                    <li class="active">
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
</body>
</html>
