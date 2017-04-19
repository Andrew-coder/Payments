<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/icomoon-social.css">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,600,800' rel='stylesheet' type='text/css'>

    <link rel="stylesheet" href="/css/leaflet.css" />

    <link rel="stylesheet" href="/css/leaflet.ie.css" />

    <link rel="stylesheet" href="/css/main.css">

    <script src="/js/modernizr-2.6.2-respond-1.1.0.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
    <script src="/js/form-slider.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>

<div class="container">
    <div class="row">
        <div class="container">
            <div class="container">

                <div class="radio">
                    <a href="#" class="active" id="card-transfer"><label><input id="card" type="radio" class="radio" name="optradio">Card transfer</label></a>
                </div>
                <div class="radio">
                    <a href="#" id="account-transfer"><label><input id="account" type="radio" class="radio" name="optradio">Account transfer</label></a>
                </div>


                <div class="panel-body">
                    <div class="row">

                            <form id="card-pay-form" action="" method="post" role="form" >
                                <div class="form-group row">
                                    <label class="col-2 col-form-label">From card</label>
                                    <div class="col-10">
                                        <select name="cards" class="form-control">
                                            <c:if test="${cards==null || cards.isEmpty()}">
                                                <option>You have no any cards</option>
                                            </c:if>
                                            <c:forEach items="${cards}" var="card">
                                                <option value="${card.getCardNumber()}"></option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-2 col-form-label">On the card</label>
                                    <div class="col-10">
                                        <input type="text" name="target_card" id="target_card" class="form-control" placeholder="recipient's card">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-2 col-form-label">Sum</label>
                                    <div class="col-10">
                                        <input type="text" name="sum" class="form-control">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-2 col-form-label">Purpose of payment</label>
                                    <div class="col-10">
                                        <input type="text" name="purpose" class="form-control">
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



                            <form id="account-form-pay" action="" method="post" role="form" style="display: none;">
                                <div class="form-group row">
                                    <label class="col-2 col-form-label">mfo</label>
                                    <div class="col-10">
                                        <input type="text" name="sum" class="form-control">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-2 col-form-label">usreou</label>
                                    <div class="col-10">
                                        <input type="text" name="sum" class="form-control">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-2 col-form-label">account number</label>
                                    <div class="col-10">
                                        <input type="text" name="sum" class="form-control">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-2 col-form-label">Sum</label>
                                    <div class="col-10">
                                        <input type="text" name="sum" class="form-control">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-2 col-form-label">Purpose of payment</label>
                                    <div class="col-10">
                                        <input type="text" name="purpose" class="form-control">
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

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="js/jquery-1.9.1.min.js"><\/script>')</script>
<script src="js/bootstrap.min.js"></script>
<script src="http://cdn.leafletjs.com/leaflet-0.5.1/leaflet.js"></script>
<script src="js/jquery.fitvids.js"></script>
<script src="js/jquery.sequence-min.js"></script>
<script src="js/jquery.bxslider.js"></script>
<script src="js/main-menu.js"></script>
<script src="js/template.js"></script>

</body>
</html>
