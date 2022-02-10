<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
    <meta charset="UTF-8" content="text/html">

    <style>
        form {
            width: 300px;
            border: 2px solid black;
        }
        input {
            width: 250px;
        }
    </style>
</head>
<body>

<section>
    <h2><a href="${pageContext.request.contextPath}">Home</a></h2>
    <br>
    <h2>Edit Meal</h2>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form method="post" action="${pageContext.request.contextPath}/meals/">
        <dl hidden>
            <dt>Id:</dt>
            <label>
                <input type="number" value="${meal.id}" name="id">
            </label>
        </dl>
        <dl>
            <dt>DateTime:</dt>
            <label>
                <input type="datetime-local" value="${meal.dateTime}" name="dateTime" required>
            </label>
        </dl>
        <dl>
            <dt>Description:</dt>
            <label>
                <input type="text" value="${meal.description}" name="description" required>
            </label>
        </dl>
        <dl>
            <dt>Calories:</dt>
            <label>
                <input type="number" value="${meal.calories}" name="calories" required>
            </label>
        </dl>

        <input style="width: 50px" type="submit" value="ok">
        <input style="width: 50px" type="button" value="cancel" onclick="window.location.href = '${pageContext.request.contextPath}/meals'">
    </form>
</section>
</body>
</html>