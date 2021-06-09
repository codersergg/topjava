<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Form Meal</title>
</head>
<body>
<h2>Form Meal</h2>
<form action="meals.jsp?action=edit" method="post">
    date: <input type="datetime-local" name="dateTime" value="${meal.dateTime}"/><br>
    description: <input type="text" name="description" value="${meal.description}"/><br>
    calories: <input type="text" name="calories" value="${meal.calories}"/><br>
    <input type="submit" value="Submit">
</form>
</body>
</html>
