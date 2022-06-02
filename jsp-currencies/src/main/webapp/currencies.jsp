<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page
    import="java.util.*, java.text.*, com.epam.rd.jsp.currencies.CurrenciesOfCurrentTestCase"%>

<jsp:useBean id="currencies"
    class="com.epam.rd.jsp.currencies.CurrenciesOfCurrentTestCase"
    scope="request" />
    <link href="style.css" rel="stylesheet" type="text/css">
<html lang="en" xml:lang="en">
    <head>
        <title>Currencies</title>
    </head>
    <body>
        <h1>Currencies</h1>
        <ul>
            <c:forEach var="currency" items="${currencies.currencies}">
                <li><c:out value="${currency}"/></li>
            </c:forEach>
        </ul>
    </body>
</html>
