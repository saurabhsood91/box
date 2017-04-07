<%--
  Created by IntelliJ IDEA.
  User: saurabh
  Date: 3/19/17
  Time: 2:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <title>Nextbox</title>
</head>
<body>
    <h1>${message}</h1>
    <h2>${user.userName}</h2>
    <form action="/upload" method="post" enctype="multipart/form-data">
        <p>Select a File:</p>
        <input type="file" name="uploadedfile" />
        <input type="hidden" value="${currentDirectory}" name="currentDirectory"/>
        <input type="submit" value="Upload File" />
    </form>

    <div>
        <h3>Create a Directory</h3>
        <form action="/createDir">
            <p>Specify name:</p>
            <input type="text" name="dirName"/>
            <input type="hidden" value="${currentDirectory}" name="currentDirectory"/>
            <input type="submit" value="Create Dir" />
        </form>
    </div>

    <div>
        <h4>Directory Contents</h4>
        <ul>
            <c:forEach var="file" items="${files}">
                <li>${file}</li>
                    <form action="/view">
                        <input type="hidden" name="fileSelected" value="${file}"/>
                        <input type="submit" value="view"/>
                    </form>
                    <form action="/move">
                        <input type="hidden" name="fileSelected" value="${file}"/>
                        <input type="submit" value="move/rename"/>
                    </form>
                    <form action="/delete">
                        <input type="hidden" name="fileSelected" value="${file}"/>
                        <input type="submit" value="delete"/>
                    </form>
            </c:forEach>
        </ul>
    </div>
</body>
</html>
