<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Page for Authtorization</title>
</head>
<body>

<div class="container" style="width: 300px;">
    <c:url value="/j_spring_security_check" var="loginUrl" />
    <form action="${loginUrl}" method="post">
        <h2 class="form-signin-heading">Please sign in</h2>
        <input type="text" class="form-control" name="j_username" placeholder="Login" required autofocus value="bigcat">
        <input type="password" class="form-control" name="j_password" placeholder="Password" required value="black">
        <button class="btn btn-lg btn-primary btn-block" type="submit">Auth</button>
    </form>
</div>

</body>
</html>