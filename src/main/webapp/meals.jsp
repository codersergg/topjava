<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table border="2" cellspacing="0" cellpadding="3">

    <tr style="text-align: center">
        <td><h3>Date</h3></td>
        <td><h3>Description</h3></td>
        <td><h3>Calories</h3></td>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
        <fmt:formatDate pattern="yyyy.MM.dd HH:mm" value="${parsedDateTime}" var="dateTime"/>
        <tr style="color: ${meal.excess ? 'green' : 'red'}">
            <td>${dateTime}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
