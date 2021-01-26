<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/style-table.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table>
        <tr>
            <th>Имя</th>
            <th>Тел.</th>
            <th>Мессенджер</th>
            <th>Почта</th>
            <th>Профиль LinkedIn</th>
            <th>Профиль GitHub</th>
            <th>Профиль Stackoverflow</th>
            <th>Домашняя страница</th>
            <th></th>
            <th></th>
        </tr>
        <jsp:useBean id="resumes" scope="request" type="java.util.List"/>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.dofler.webapp.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <c:forEach var="contactEntry" items="${resume.contacts}">
                    <jsp:useBean id="contactEntry"
                                 type="java.util.Map.Entry<com.dofler.webapp.model.ContactType, java.lang.String>"/>
                    <td><%=contactEntry.getKey().toHtml(contactEntry.getValue())%></td>

                </c:forEach>
                <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="" alt="Delete"></a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="" alt="Edit"></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>