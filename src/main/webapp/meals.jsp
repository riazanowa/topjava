<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>
<html>
<head>
    <title>Meals</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="css/meals.css" rel="stylesheet" type="text/css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h1>Meals</h1>
<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach var="mealTo" items="${mealToList}">
        <tr class="${mealTo.excess ? 'red' : 'green'}">
            <td>${f:formatLocalDateTime(mealTo.dateTime)}</td>
            <td>${mealTo.description}</td>
            <td>${mealTo.calories}</td>
            <td><a href="${pageContext.request.contextPath}/meals?action=edit&mealId=${mealTo.id}">Update</a></td>
            <td><a href="${pageContext.request.contextPath}/meals?action=delete&mealId=${mealTo.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<p>
    <button type="button"><a class="btn-cancel" href="${pageContext.request.contextPath}/meals?action=add">Add meal</a>
    </button>
</p>
</body>
</html>
