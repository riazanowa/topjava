<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>${not empty meal.id ?  'Edit meal': 'Add meal'}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="css/meals.css" rel="stylesheet" type="text/css">
</head>
<body>
<h1>${not empty meal.id ?  'Edit meal': 'Add meal'}</h1>
<form method="POST"
      action="${pageContext.request.contextPath}/meals${not empty meal.id ? '?mealId='.concat(meal.id) : ''}"
      name="formCreateMeal">
    <p>
        DateTime : <input type="datetime-local" name="datetime"
                          value="${ meal.dateTime}"/>
        <br/>
    </p>
    <p>
        Description : <input type="text" name="description" value="${meal.description }"/>
        <br/>
    </p>
    <p>
        Calories : <input type="number" name="calories" value="${ meal.calories}"/>
        <br/>
    </p>
    <input type="submit" value="Submit"/>
    <button type="button"><a class="btn-cancel" href="${pageContext.request.contextPath}/meals">Cancel</a></button>
</form>
</body>
</html>


