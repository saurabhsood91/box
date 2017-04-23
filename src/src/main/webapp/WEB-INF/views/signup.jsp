<%--
  Created by IntelliJ IDEA.
  User: aniru
  Date: 4/21/2017
  Time: 2:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Account</title>
</head>
<body>
    <form action="/createAccount" method="post">
        <label for="firstname">First Name</label>
        <input type="text" name="firstname"></input><br>
        <label for="lastname">Last Name</label>
        <input type="text" name="lastname"></input><br>
        <label for="email">Email</label>
        <input type="text" name="email"></input><br>
        <label for="username">Username</label>
        <input type="text" name="username"></input><br>
        <label for="password">Password</label>
        <input type="password" name="password" /><br>
        <input type="submit" value="submit"></input>
    </form>
</body>
</html>
