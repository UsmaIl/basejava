<%@ page import="com.dofler.webapp.util.DateUtil" %>
<%@ page import="com.dofler.webapp.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/style-edit.css">
    <jsp:useBean id="resume" type="com.dofler.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form class="input-form" method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <h2>Имя</h2>
        <ul class="names-ul">
            <li class="name-li">
                <label for="full_name">ФИО:</label>
                <input name="full_name" type="text" size=30 value="${resume.fullName}">
            </li>
        </ul>
        <h2>Контакты:</h2>
        <ul class="contacts-ul">
            <c:forEach var="type" items="<%=ContactType.values()%>">
                <c:set var="sectionName" value="${type.name().toLowerCase()}"/>
                <jsp:useBean id="sectionName" type="java.lang.String"/>
                <li class="contact-li">
                    <label for="<%=sectionName%>">${type.title}:</label>
                    <input name="<%=sectionName%>" type="text" size="30" value="${resume.getContact(type)}">
                </li>
            </c:forEach>
        </ul>
        <h2>О себе:</h2>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="com.dofler.webapp.model.AbstractSection"/>
            <h3>${type.title}</h3>
            <c:choose>
                <c:when test="${type=='OBJECTIVE'}">
                    <label>
                        <input type="text" name="${type}" size="80" value="<%=section%>">
                    </label>
                </c:when>
                <c:when test="${type=='PERSONAL'}">
                    <label>
                        <textarea name="${type}" cols="60" rows="5"><%=section%></textarea>
                    </label>
                </c:when>
                <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                    <label>
                        <textarea name="${type}" cols=60
                                  rows=5><%=String.join("\n", ((ListSection) section).getListSection())%></textarea>
                    </label>
                </c:when>
                <c:when test="${type == 'EXPERIENCE' || type == 'EDUCATION'}">
                    <c:forEach var="institution" items="<%=((ListInstitution) section).getListInstitution()%>"
                               varStatus="counter">
                        <ul class="institutions-ul">
                            <li class="institution-li">
                                <label for="${type}_name}">Название учереждения:</label>
                                <input type="text" name="${type}_name}" value="${institution.homePage.name}">
                            </li>
                            <li class="institution-li">
                                <label for="${type}_url">Сайт учереждения:</label>
                                <input type="text" name="${type}_url" value="${institution.homePage.url}">
                            </li>
                        </ul>
                        <ul class="places-ul">
                        <c:forEach var="place" items="${institution.places}">
                            <jsp:useBean id="place" type="com.dofler.webapp.model.Place"/>
                            <li class="place-li">
                                <label for="${type}${counter.index}_startDate">Начальная дата:</label>
                                <input type="date" name="${type}${counter.index}_startDate">
                            </li>
                            <li class="place-li">
                                <label for="${type}${counter.index}_endDate">Конечная дата:</label>
                                <input type="date" name="${type}${counter.index}_endDate"
                                       value="<%=place.getEndDate()%>" placeholder="MM/yyyy">
                            </li>
                            <li class="place-li">
                                <label for="${type}${counter.index}_title">Должность:</label>
                                <input type="text" name=${type}${counter.index}_title"
                                       value="${place.title}">
                            </li>
                            <li class="place-li">
                                <label for="${type}${counter.index}_description">Описание:</label>
                                <textarea name="${type}${counter.index}_description" rows=5>${place.description}</textarea>
                            </li>
                        </ul>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <ul class="buttons-ul">
            <li class="button-li">
                <button onclick="window.history.back()">Отменить</button>
                <button type="submit">Сохранить</button>
            </li>
        </ul>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
