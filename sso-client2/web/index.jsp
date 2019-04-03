<%--
  Created by IntelliJ IDEA.
  User: ANS
  Date: 2019/4/2
  Time: 19:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>sso-client2</title>
</head>
<body>
    you had signed in sso-client2!
    <form action="http://sso-server:8081/logout?returnUrl=http://sso-client2:8082/" method="post">
        <input type="submit" value="注销">
    </form>
</body>
</html>
