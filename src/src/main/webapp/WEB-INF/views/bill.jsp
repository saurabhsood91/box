<%--
  Created by IntelliJ IDEA.
  User: saurabh
  Date: 4/28/17
  Time: 12:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Nextbox</title>
</head>
<body>

    <h1>Nextbox</h1>

    <div>
        <h4>Bill</h4>
        <p>Your bill is ${bill}</p>
    </div>

    <form action="/returnToHome" method="post">
        <input type="hidden" value="${currentDirectory}" name="currentDirectory"/>
        <input type="submit" value="Return to home"/>
    </form>

</body>
</html>
