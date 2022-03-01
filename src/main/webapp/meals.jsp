<%--
  Created by IntelliJ IDEA.
  User: katri
  Date: 15.02.2022
  Time: 17:28
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <title>Meals</title>
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
    <c:forEach  var="mealTo" items="${requestScope.mealToList}">
        <tr class="${mealTo.excess ? 'red' : 'green'}">
            <td><javatime:parseLocalDateTime value="${mealTo.dateTime}" pattern="yyyy-MM-ddTHH:mm:ss" var="parsedDate" />
                <c:out value="${parsedDate}"/></td>
            <td><c:out value="${mealTo.description}"/></td>
            <td><c:out value="${mealTo.calories}"/></td>
            <td><a>Update</a></td>
            <td><a>Delete</a></td>

        </tr>
    </c:forEach>

</table>
</body>
</html>
