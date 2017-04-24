<%--
  Created by IntelliJ IDEA.
  User: saurabh
  Date: 4/23/17
  Time: 7:32 PM
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

    <form action="/admin/plan/insert" method="post">
        <h4>Add A Plan</h4>
        <div>
            <label for="ratePerGB">Rate Per GB:</label>
            <input type="text" name="ratePerGB" />
            <label for="maxSpace">Maximum Space in GB:</label>
            <input type="text" name="maxSpace" />
            <input type="submit" value="Add Plan" />
        </div>
    </form>

</body>
</html>
