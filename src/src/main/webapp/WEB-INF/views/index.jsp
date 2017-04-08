<%--
  Created by IntelliJ IDEA.
  User: saurabh
  Date: 3/19/17
  Time: 1:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>NextBox</title>
</head>
<body>
    <h1>${org}</h1>

    <h2>${message}</h2>

    <form action="/login" method="post">
        <label for="username">Username</label>
        <input type="text" name="username"></input>
        <label for="password">Password</label>
        <input type="password" name="password" />
        <input type="submit" value="Login">
        <input type="reset" value="Clear">
    </form>

</body>
</html>
