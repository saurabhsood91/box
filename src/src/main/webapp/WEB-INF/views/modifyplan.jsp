<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: saurabh
  Date: 4/23/17
  Time: 8:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>NextBox</title>
</head>
<body>

    <h1>NextBox</h1>

    <h2>${message}</h2>

    <div>
        <h4>Available Plans</h4>
        <ul>
            <c:forEach var="plan" items="${plans}">
                <li><a href="/admin/plan/details?id=${plan}">${plan}</a></li>
            </c:forEach>
        </ul>
    </div>

</body>
</html>
