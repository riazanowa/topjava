<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>
<html>
<head>
    <title>Edit meal</title>
    <link href="css/meals.css" rel="stylesheet" type="text/css">
</head>
<body>
<c:if test="${mealId == null}">
    <h1>Create meal</h1>
</c:if>
<c:if test="${mealId != null}">
    <h1>Edit meal</h1>
</c:if>

<form method="POST" action="/topjava/meals" name="formCreateMeal">
    <p>
    DateTime : <input type="datetime-local" name="datetime" value="${f:formatLocalDateTime(meal.dateTime, 'yyyy-MM-dd HH:mm')}" />
    <br/>
    </p>
    <p>
    Description : <input type="text" name="description" value="${meal.description}" />
    <br/>
    </p>
    <p>
    Calories : <input type="number" name="calories" value="${meal.calories}"/>
    <br/>
    </p>
    <input type="submit" value="Submit"/>
    <button type="button"><a class="btn-cancel" href="/topjava/meals">Cancel</a></button>
</form>

</body>
</html>


