<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Степан
  Date: 04.04.2016
  Time: 22:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <link type="text/css" rel="stylesheet" href="style.css"/>
  <title>rest page</title>
</head>

<body>

<c:if test="${not empty msg}">
    <strong>Message:</strong> ${msg}
</c:if>

<form id="addClient" action="${pageContext.request.contextPath}/add" method="post">
  <fieldset>
    <legend>add client</legend>
    <label for="firstName">first name</label>
    <input id="firstName" name="firstName" size="30"/>
    <label for="lastName">last name</label>
    <input id="lastName" name="lastName" size="30"/>
    <label for="gender">sex</label>
    <select id="gender" name="gender">
      <option value="M">male</option>
      <option value="F">female</option>
    </select>
    <label for="country">country</label>
    <input id="country" name="country" size="30">
    <label for="city">city</label>
    <input id="city" name="city" size="30">
    <button type="submit">add</button>
  </fieldset>
</form>

<form id="removeClient" action="${pageContext.request.contextPath}/remove" method="post">
  <fieldset>
    <legend>remove client</legend>
    <label for="remClientId">client's id</label>
    <input id="remClientId" name="remClientId" size="10"/>
    <label for="remFirstName">first name</label>
    <input id="remFirstName" name="remFirstName" size="30"/>
    <label for="remLastName">last name</label>
    <input id="remLastName" name="remLastName" size="30"/>
    <button type="submit">remove</button>
  </fieldset>
</form>

</body>
</html>
