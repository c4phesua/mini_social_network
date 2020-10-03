<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
    Document   : profile
    Created on : Sep 19, 2020, 4:32:56 PM
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
            <!-- edit profile -->
            <div class="panel panel-default">
                <div class="panel-body">
                    <h4>Edit profile</h4>
                    <form method="post" action="">
                        <div class="form-group">
                            <input class="form-control" type="text" name="status" placeholder="Status" value="">
                        </div>

                        <div class="form-group">
                            <input class="form-control" type="text" name="location" placeholder="Location" value="">
                        </div>

                        <div class="form-group">
                            <input class="btn btn-primary" type="submit" name="update_profile" value="Save">
                        </div>
                    </form>
                </div>
            </div>
            <!-- ./edit profile -->
        </div>
        <div class="col-md-6">
            <!-- user profile -->
            <div class="media">
                <div class="media-left">
                    <img src="img/my_avatar.jpg" class="media-object" style="width: 128px; height: 128px;">
                </div>
                <div class="media-body">
                    <h2 class="media-heading">${sessionScope.get("userInfo").getName()}</h2>
                    <p>Status: ${sessionScope.get("userInfo").getStatus()}</p>
                </div>
            </div>
            <!-- user profile -->

            <hr>

            <!-- timeline -->
            <div>
                <!-- post -->
                <c:forEach var="post" items="${sessionScope.get('userInfo').getPosts()}">
                    <input class="btn-danger btn" type="button" value="Delete" onclick="confirmBox()">
                    <div class="panel panel-primary post-pointer form-inline" onclick="location.href='detail?id=${post.getId()}'">
                        <div class="panel-heading">
                            <h4 align="center" class="breaker">${post.getTitle()}</h4>
                        </div>
                        <div class="panel-body">
                            <p class="breaker">${post.getDesc()}</p>
                            <img class="img-thumbnail" src="data:image/png;base64, ${post.getImgBase64()}">
                        </div>
                        <div class="panel-footer">
                            <span>posted ${post.getDateTimeString()} by ${post.getEmail()}</span>
                            <span class="pull-right">
                                <a class="text-danger" href="#">
                                    <form style="display: none" action="MainController" method="post">
                                        <input type="hidden" name="txtId" value="${post.getId()}">
                                        <input type="submit" id="delete" name="btnAction" value="Delete">
                                    </form>
                                </a>
                            </span>
                        </div>
                    </div>
                </c:forEach>
                <!-- ./post -->
                <script>
                    function confirmBox() {
                        var r = confirm("Do you want to delete this post?");
                        if (r === true) {
                            document.getElementById('delete').click();
                        }
                    }
                </script>
            </div>
            <!-- ./timeline -->
        </div>
        <div class="col-md-3">
            <!-- notification -->
            <div class="panel panel-default fixediv">
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
