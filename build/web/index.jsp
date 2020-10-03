<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
    Document   : index
    Created on : Sep 17, 2020, 9:32:31 AM
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
<body class="bg-primary">
<!-- nav -->
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="index.jsp">MiniSociety</a>
        </div>
    </div>
</nav>
<!-- ./nav -->

<!-- main -->
<main class="container">
    <h1 class="text-center">Welcome to MiniSociety! <br><small>Represent your self everywhere</small></h1>

    <div class="row">
        <div class="col-md-6">
            <h4>Login</h4>

            <!-- login form -->
            <a class="error">${loginErrorMsg}</a><br>
            <form action="MainController" method="post">
                <div class="form-group">
                    <input class="form-control" type="text" name="txtEmail" placeholder="Email" title="ex: abc@gmail.com"
                           pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" required>
                </div>

                <div class="form-group">
                    <input class="form-control" type="password" name="txtPassword" placeholder="Password" required>
                </div>

                <div class="form-group">
                    <input class="btn btn-primary" type="submit" name="btnAction" value="Login">
                </div>
            </form>
            <!-- ./login form -->
        </div>
        <div class="col-md-6">
            <h4>Don't have an account yet? Register!</h4>

            <!-- register form -->
            <a class="error">${registerMsg}</a><br>
            <form method="post" action="MainController">
                <div class="form-group">
                    <input class="form-control" type="text" name="txtEmail" placeholder="Email"
                           pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" required maxlength="320">
                </div>

                <div class="form-group">
                    <input class="form-control" type="text" name="txtName" placeholder="Name" required maxlength="200">
                </div>

                <div class="form-group">
                    <input class="form-control" type="password" name="txtPassword" placeholder="Password" required>
                </div>
                <div class="form-group">
                    <input class="btn btn-success" type="submit" name="btnAction" value="Register">
                </div>
            </form>
            <!-- ./register form -->
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