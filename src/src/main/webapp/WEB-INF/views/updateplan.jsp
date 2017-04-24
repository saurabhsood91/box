<%--
  Created by IntelliJ IDEA.
  User: saurabh
  Date: 4/23/17
  Time: 9:04 PM
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
        <h4>Update Plan</h4>
        <form action="/admin/plan/modify" method="post">
            <input type="hidden" name="id" value="${id}"/>
            <label for="rate">Rate</label>
            <input type="text" name="rate" value="${rate}" />
            <label for="space">Space in GB</label>
            <input type="text" name="space" value="${space}" />
            <input type="submit" value="Modify Plan" />
        </form>
    </div>

</body>
</html>
