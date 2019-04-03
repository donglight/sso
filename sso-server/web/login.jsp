<%--
  Created by IntelliJ IDEA.
  User: ANS
  Date: 2019/4/2
  Time: 19:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>sso-login</title>
</head>
<body>
    <form action="login?returnUrl=${param.returnUrl}&JSESSIONID=${param.JSESSIONID}" method="post">
        <input type="text" name="username">
        <input type="submit">
    </form>
</body>
</html>
