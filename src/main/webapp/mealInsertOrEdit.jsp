<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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

<form method="POST" action="/meals" name="formCreateMeal">
    <javatime:parseLocalDateTime value="${meal.dateTime}" pattern="yyyy-MM-ddTHH:mm:ss" var="parsedDate" />"/>
    DateTime : <input type="datetime-local" name="datetime" value="<c:out value="${parsedDate}"/>" />
    <br/>
    Description : <input type="text" name="description" value="<c:out value="${meal.description}"/>" />
    <br/>
    Calories : <input type="number" name="calories" value="<c:out value="${meal.calories}"/>"/>
    <br/>
    <input type="submit" value="Submit"/>
    <a class="btn-cancel" href="/meals">Cancel</a>
</form>

</body>
</html>


