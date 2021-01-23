<%@ page import="com.dofler.webapp.util.DateUtil" %>
<%@ page import="com.dofler.webapp.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/label-style.css">
    <jsp:useBean id="resume" type="com.dofler.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <h2>Имя</h2>
        <label>
            ФИО: <input type="text" name="fullName" size=30 value="${resume.fullName}">
        </label>
        <h2>Контакты:</h2>
        <c:forEach var="type" items="<%=ContactType.values()%>">
        <label class="contact-label">
            ${type.title}:<input type="text" size=30 value="${resume.getContact(type)}">
        </label><br/>
        </c:forEach>

        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
