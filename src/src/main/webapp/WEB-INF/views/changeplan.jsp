<%--
  Created by IntelliJ IDEA.
  User: milroy
  Date: 4/26/17
  Time: 11:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>NextBox</title>
</head>
<body>
<h1>NextBox</h1>

<div>
    <h2>Current Plan</h2>
    <p>Current rate: ${rate} units per month</p>
    <p>Current space limit: ${space} GB</p>
</div>

<div>
    <h3>Update Plan</h3>
    <form action="/userPlanChange" method="post">
        <label>Input new space in GB:</label>
        <input type="text" name="space" value="${space}" />
        <input type="submit" value="Modify Plan" />
    </form>
</div>

<form action="/returnToHome" method="post">
    <input type="hidden" value="${currentDirectory}" name="currentDirectory"/>
    <input type="submit" value="Return to home"/>
</form>
</body>
</html>