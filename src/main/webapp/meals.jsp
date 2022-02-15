<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://java.sun.com/jsp/jstl/sql" %>
<html>
<head>
    <title>Meals</title>
    <meta charset="UTF-8" content="text/html">

    <style>
        table {
            width: 800px;
            border: 2px solid black;
        }
        td {
            text-align: center;
            border: 2px solid #202020;
        }
        th {
            border: 2px solid #202020;
            background-color: silver;
        }
    </style>
</head>

<body>
    <a href="${pageContext.request.contextPath}">Home</a>
    <h2>Meals</h2>
    <a href="meals/edit">Add meal</a>
    <br><br>

    <form action="meals">
        <dl>
            От даты (включая) <input type="date" name="startdate" value="${param.startdate}">
            До даты (включая) <input type="date" name="enddate" value="${param.enddate}">
        </dl>

        <dl>
            От времени (включая) <input type="time" name="starttime" value="${param.starttime}">
            До времени (исключая) <input type="time" name="endtime" value="${param.endtime}">
        </dl>

        <button type="submit">ok</button>
    </form>

    <table>

        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th>Operations</th>
        </tr>

        <c:forEach var="meal" items="${meals}">
            <c:url var="updateButton" value="/meals/edit">
                <c:param name="id" value="${meal.id}"/>
            </c:url>

            <c:url var="deleteButton" value="/meals/delete">
                <c:param name="id" value="${meal.id}"/>
            </c:url>

            <tr style="color: <c:out value="${meal.excess ? 'red' : 'green'}"/>">
                <td>${meal.formattedDateTime}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td>
                    <input type="button" value="update" onclick="window.location.href = '${updateButton}'"/>
                    <input type="button" value="delete" onclick="window.location.href = '${deleteButton}'"/>
                </td>
            </tr>
        </c:forEach>

    </table>
</body>
</html>
