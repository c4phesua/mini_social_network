<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
    Document   : template
    Created on : Sep 19, 2020, 4:34:59 PM
    Author     : nhoxp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>MiniSociety</title>

    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<!-- nav -->
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid form-inline justify-content-between">
        <div class="navbar-header">
            <a class="navbar-brand" href="index.jsp">MiniSociety</a>
        </div>
        <!-- search post form -->
        <form class="navbar-left" action="MainController" method="post">
            <input class="form-control" type="search" name="txtKeyword" placeholder="Search" aria-label="Search" required>
            <button class="btn btn-success" type="submit" name="btnAction" value="Search">Search</button>
        </form>
        <!-- /search post form -->
        <ul class="nav navbar-nav navbar-right">
            <li><a href="home.jsp">Home</a></li>
            <c:if test="${sessionScope.get('is_login')}">
                <li><a href="profile.jsp">Profile</a></li>
                <li><a href="logout">Logout</a></li>
            </c:if>
            <c:if test="${!sessionScope.get('is_login')}">
                <li><a href="index.jsp">Login</a></li>
            </c:if>
        </ul>
    </div>
</nav>
<!-- ./nav -->

<!-- main -->
<main class="container paddingtop">
    <div class="row">
        <div class="col-md-3">
            <!-- profile brief -->
            <div class="panel panel-default fixediv welcome">
                <div class="panel-heading">
                    <h4 align="center">WELCOME</h4>
                </div>
                <div class="panel-body">
                    <h4>${sessionScope.get("userInfo").getName()}</h4>
                    <p>${sessionScope.get("userInfo").getStatus()}</p>
                </div>
            </div>
            <!-- ./profile brief -->
        </div>
        <!-- post form -->
        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h6>posted ${requestScope.post.getDateTimeString()} by ${requestScope.post.getEmail()}</h6>
                </div>
                <div class="panel-title">
                    <h3 align="center" class="breaker">${requestScope.post.getTitle()}</h3>
                </div>
                <div class="panel-body">
                    <h5 class="breaker">${requestScope.post.getDesc()}</h5>
                    <img class="img-thumbnail" src="data:image/png;base64, ${requestScope.post.getImgBase64()}">
                    <div class="form-inline btn-group">
                        <!-- like button form -->
                        <c:if test="${!requestScope.post.isLike(sessionScope.userInfo.getEmail())}">
                            <form action="MainController" method="post" class="form-group">
                                <input class="hidden" name="txtPostId" value="${requestScope.post.getId()}">
                                <input type="hidden" name="txtValue" value="1">
                                <button type="submit" class="btn btn-default btn-sm" name="btnAction" value="Emotion">
                                    <span class="glyphicon glyphicon-thumbs-up"></span> Like ${requestScope.post.getLikesNum()}
                                </button>
                            </form>
                        </c:if>
                        <c:if test="${requestScope.post.isLike(sessionScope.userInfo.getEmail())}">
                            <form action="MainController" method="post" class="form-group">
                                <input class="hidden" name="txtPostId" value="${requestScope.post.getId()}">
                                <input type="hidden" name="txtValue" value="0">
                                <button type="submit" class="btn btn-default btn-sm btn-primary" name="btnAction" value="Emotion">
                                    <span class="glyphicon glyphicon-thumbs-up"></span> Like ${requestScope.post.getLikesNum()}
                                </button>
                            </form>
                        </c:if>
                        <!-- /like button form -->

                        <!-- dislike button form -->
                        <c:if test="${!requestScope.post.isDislike(sessionScope.userInfo.getEmail())}">
                            <form action="MainController" method="post" class="form-group">
                                <input class="hidden" name="txtPostId" value="${requestScope.post.getId()}">
                                <input type="hidden" name="txtValue" value="-1">
                                <button type="submit" class="btn btn-default btn-sm" name="btnAction" value="Emotion">
                                    <span class="glyphicon glyphicon-thumbs-down"></span> Dislike ${requestScope.post.getDisLikesNum()}
                                </button>
                            </form>
                        </c:if>

                        <c:if test="${requestScope.post.isDislike(sessionScope.userInfo.getEmail())}">
                            <form action="MainController" method="post" class="form-group">
                                <input class="hidden" name="txtPostId" value="${requestScope.post.getId()}">
                                <input type="hidden" name="txtValue" value="0">
                                <button type="submit" class="btn btn-default btn-sm btn-primary" name="btnAction" value="Emotion">
                                    <span class="glyphicon glyphicon-thumbs-down"></span> Dislike ${requestScope.post.getDisLikesNum()}
                                </button>
                            </form>
                        </c:if>
                        <!-- /dislike button form -->
                    </div>
                </div>
                <!--Post comment-->
                <div class="panel-footer">
                    <form class="input-group" action="MainController" method="post">
                        <input class="hidden" name="txtPostId" value="${requestScope.post.getId()}">
                        <input class="form-control" type="text" name="txtComment" placeholder="Type your text here..." maxlength="256" required>
                        <span class="input-group-btn">
                            <input class="btn btn-success" type="submit" name="btnAction" value="Comment">
                        </span>
                    </form>
                </div>
                <!--/Post comment-->

                <!--Comment section-->
                <c:forEach var="comment" items="${requestScope.post.getComments()}">
                    <div class="panel-footer">
                        <h4>${comment.getEmail()}</h4>
                        <h5>${comment.getText()}</h5>
                    </div>
                </c:forEach>
                <!-- /Comment section -->
            </div>
        </div>
        <!-- /post form -->
        <div class="col-md-3">
            <!-- notification -->
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 align="center">Notifications</h4>
                </div>
                <div class="panel-body notification">
                    <c:if test="${sessionScope.get('is_login')}">
                    <ul>
                        <c:forEach var="noti" items="${sessionScope.get('userInfo').getNotifications()}">
                            <li>
                                <a href="detail?id=${noti.getPostId()}">${noti.getMsg()} by ${noti.getActor()} at ${noti.getDatTimeString()}</a>
                            </li>
                        </c:forEach>
                    </ul>
                    </c:if>
                </div>
            </div>
            <!-- ./notification -->
        </div>
    </div>
</main>
<!-- ./main -->

<!-- footer -->
<!-- ./footer -->
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/script.js"></script>
</body>
</html>
