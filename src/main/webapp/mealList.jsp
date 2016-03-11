<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal List</title>
</head>
<body style="background: #dcdcdc">
<h2>Meal List</h2>
<table style="border: 1px solid; width: 500px; text-align:center">
    <thead style="background:#75ff96">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan="3"></th>
    </tr>
    </thead>
    <tbody>


    <c:forEach items="${meals}" var="meals">
    <c:if test="${meals.exceed eq false}">
    <tr style="color: green">
        </c:if>
        <c:if test="${meals.exceed eq true}">
    <tr style="color: red">
        </c:if>

        <td><c:out value="${meals.date}"/></td>
        <td><c:out value="${meals.description}"/></td>
        <td><c:out value="${meals.calories}"/></td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
