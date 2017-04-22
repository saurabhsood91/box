<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: saurabh
  Date: 4/6/17
  Time: 5:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>NextBox</title>
</head>
<body>
    <div>
        <div>
            <b>File: ${fileToShare}</b>
        </div>
        <div>
            Share File with: ${userToShare}
        </div>
        <div>
            <p>Find user by username</p>
            <form method="get" action="/finduser">
                <input type="hidden" name="fileToShare" value="${fileToShare}" />
                <input type="text" name="usernameToSearch" />
                <input type="submit" value="Find" />
            </form>
        </div>
        <c:if test="${not empty userToShare}">
            <form action="/share" method="post">
                <input type="hidden" value="${fileToShare}" name="fileToShare"/>
                <input type="hidden" value="${userToShare}" name="userToShare"/>
                <input type="submit" value="Share File" />
            </form>
        </c:if>
    </div>
</body>
</html>
