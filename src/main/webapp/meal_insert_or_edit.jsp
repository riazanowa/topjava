<%--
  Created by IntelliJ IDEA.
  User: katri
  Date: 17.02.2022
  Time: 14:32
  To change this template use File | Settings | File Templates.
--%>
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
<h1>Edit meal</h1>
<form method="POST" action="/meals" name="formCreateMeal">
    DateTime : <input type="datetime-local" name="datetime"/>
    <br/>
    Description : <input type="text" name="description"/>
    <br/>
    Calories : <input type="number" name="calories"/>
    <br/>
    <input type="submit" value="Submit"/>
</form>

</body>
</html>


