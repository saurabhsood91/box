<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: aniru
  Date: 4/28/2017
  Time: 11:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Setup Admin Account</title>
</head>
<body>
<form action="/createAdmin" method="post">
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
    <label > Select Plan </label>
    <select name="selectedPlan">
        <<c:forEach var="plan" items="${plans}">
            <option value="${plan.id}">Rate:\$${plan.rate}, Space: ${plan.space} GB</option>
        </c:forEach>
    </select><br>
    <input type="submit" value="submit"></input>
</body>
</html>
