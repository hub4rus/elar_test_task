<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Список пользователей</title>
</head>
<body>

    <h3>Все пользователи:</h3>(<a href="add">добавить</a>)
    <ol>
        <c:forEach items="${users}" var="user">
            <li>

                <c:if test="${user.id < 3}">
                   <a style="color:#FF0000">
                </c:if>
                <c:if test="${user.id > 2}">
                   <a style="color:#0000FF">
                </c:if>

                ${user.login} <a style="color:#000000"> &nbsp; : &nbsp; ${user.name} - ${user.id}

                <c:if test="${user.id > 2}">
                   <a href="add?edit=${user.id}">редактировать</a> | <a href="delete?id=${user.id}">удалить</a>
                </c:if>

            </li>
        </c:forEach>
    </ol>

</body>
</html>