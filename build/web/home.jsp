<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
    Document   : home
    Created on : Sep 19, 2020, 4:32:38 PM
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
        <div class="col-md-6">
            <!-- post form -->
            <form method="post" action="MainController" enctype="multipart/form-data">
                <div class="input-group">
                    <input id="test" class="form-control" type="text" name="txtTitle" placeholder="Title" maxlength="200" required><br>
                    <textarea class="form-control textarea" name="txtDesc" placeholder="Description....." maxlength="500" required
                    rows="5" cols="90"></textarea><br>
                    <input name="image" type="file" id="fileName" accept=".jpg,.jpeg,.png" onchange="validateFileType()" required/>
                    <script type="text/javascript">
                        function validateFileType(){
                            var input = document.getElementById("fileName");
                            var fileSize = (input.files[0].size/1024).toFixed(4);
                            var fileName = input.value;
                            var idxDot = fileName.lastIndexOf(".") + 1;
                            var extFile = fileName.substr(idxDot, fileName.length).toLowerCase();
                            if (fileSize > 256){
                                document.getElementById("fileName").value = "";
                                alert("Image size must smaller than 257KB");
                                document.getElementById("blah").setAttribute("style", "display: none");
                            }
                            else if (extFile=="jpg" || extFile=="jpeg" || extFile=="png"){
                                var reader = new FileReader();
                                reader.onload = function(e) {
                                    document.getElementById("blah").src = e.target.result;
                                };
                                reader.readAsDataURL(input.files[0]);
                                document.getElementById("blah").setAttribute("style", "");
                            }else{
                                document.getElementById("fileName").value = "";
                                document.getElementById("blah").setAttribute("style", "display: none");
                                alert("Only jpg/jpeg and png files are allowed!");
                            }
                        }
                    </script>
                    <p align="center">
                        <img src="#" id="blah" class="img-thumbnail" style="display: none"/>
                    </p>
                    <p align="right">
                        <input class="btn btn-success" type="submit" name="btnAction" value="Post">
                    </p>
                </div>
            </form><hr>
            <!-- ./post form -->

            <!-- feed -->
            <div>
                <c:if test="${requestScope.offset == null}">
                    <c:redirect url="homeController"/>
                </c:if>
                <!-- post -->
                <c:forEach var="post" items="${requestScope.posts}">
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
                        </div>
                    </div>
                </c:forEach>
                <!-- ./post -->
            </div>
            <!-- ./feed -->
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
    <div class="bottom">
        <form action="homeController" method="get">
            <p align="center">
                <input hidden value="${requestScope.offset}" name="offset">
                <input class="btn-success btn" type="submit" value="Show more...">
            </p>
        </form>
    </div>
<!-- ./footer -->
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/script.js"></script>
</body>
</html>
