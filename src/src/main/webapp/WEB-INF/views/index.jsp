<%--
  Created by IntelliJ IDEA.
  User: saurabh
  Date: 3/19/17
  Time: 1:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<input>
<head>
    <title>NextBox</title>
</head>
<>
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

    <form action="/signup" method="post">
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
