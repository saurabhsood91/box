<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>NextBox</title>
</head>
<body>
    <h1>NextBox</h1>

    <h4>Change Activation Status</h4>
    <div>

    <form action="/admin/changeActivationStatus/changeStatus" method="post">
            <label for="username">Username:</label>
            <input type="text" name="username" />
            <label for="activationStatus">Activation Status:</label>
            <input type="text" name="activationStatus" />
            <input type="submit" value="Change Activation Status" />
    </form>
    </div>
    <h2>${message}</h2>


</body>
</html>
