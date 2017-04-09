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


            <div id="exTab2" class="container">
                <ul class="nav nav-tabs">
                    <li class="active">
                        <a  href="#1" data-toggle="tab">Overview</a>
                    </li>
                    <li><a href="#2" data-toggle="tab">Without clearfix</a>
                    </li>
                    <li><a href="#3" data-toggle="tab">Solution</a>
                    </li>
                </ul>
            </div>


        </nav>
    </div>
</div>
</body>
</html>
