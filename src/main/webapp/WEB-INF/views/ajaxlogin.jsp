<%--
  Created by IntelliJ IDEA.
  User: chou6
  Date: 2019-07-16
  Time: 오후 2:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script>
        let csrf = {
            param: '${_csrf.parameterName}',
            value: '${_csrf.token}'
        }
    </script>
    <script type="text/javascript" src="/resources/ajaxlogin.js"></script>
</head>
<body>
    <h1>로그인 페이지</h1>
    <form>
        <div>
            <input id="username" name="username" type="text" autocomplete="true">
        </div>
        <div>
            <input id="password" name="password" type="password" autocomplete="false">
        </div>
    </form>

    <button id="doLogin" type="button">Login</button>
    <div id="message"></div>
    <a href="/">홈으로</a>
</body>
</html>
