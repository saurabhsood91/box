<%--
  Created by IntelliJ IDEA.
  User: aniru
  Date: 4/28/2017
  Time: 1:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c-rt"  uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Credit Card</title>
</head>
<body>
<%
    String[] calendarMonths = { "January","February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    Integer[] yearOfExpiry = {2017,2018,2019,2020,2021,2022,2023,2024};
%>
<h1>${message}</h1>
<form action="/saveCreditCard" method="post">
    <label for="cardNumber">Card Number</label>
    <input type="text" name="cardNumber" size="16" maxlength="16" ></input><br>
    <input type="hidden" name="userId" value="${userId}"></input><br>
    <label for="nameOnCard">Name on card</label>
    <input type="text" name="nameOnCard"></input><br>
    <div>
        <label for="expiry">Expiration date</label>
        <select name="monthOfTheYear">
            <c-rt:forEach var="month" items="<%= calendarMonths %>">
                <option value="${i+1}">${month} (${i+1})</option>
                <c:set var="i" value="${i+1}"/>
            </c-rt:forEach>
        </select>
        <select name="yearExpiry">
            <c-rt:forEach var="year" items="<%= yearOfExpiry %>">
                <option value="${year}">${year}</option>
            </c-rt:forEach>
        </select>
    </div>

    <input type="submit" value="Add card"></input>
</form>
</body>
</html>
