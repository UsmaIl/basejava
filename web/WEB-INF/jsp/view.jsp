<%@ page import="com.dofler.webapp.model.ListSection" %>
<%@ page import="com.dofler.webapp.model.ListInstitution" %>
<%@ page import="com.dofler.webapp.model.TextSection" %>
<%@ page import="com.dofler.webapp.util.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css"  href="css/style.css">
    <link rel="stylesheet" type="text/css"  href="css/style-view.css">
    <jsp:useBean id="resume" type="com.dofler.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h1>${resume.fullName}&emsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"
                                                                                      alt="Edit"></a></h1>
    <table>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.dofler.webapp.model.SectionType, com.dofler.webapp.model.AbstractSection>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="com.dofler.webapp.model.AbstractSection"/>
            <tr>
                <td><h2>${type.title}</h2></td>
            </tr>
            <c:choose>
                <c:when test="${type=='OBJECTIVE'}">
                    <tr>
                        <td><%=((TextSection) section).getContent()%>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='PERSONAL'}">
                    <tr>
                        <td><%=((TextSection) section).getContent()%>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                    <tr>
                        <td>
                            <ul>
                                <c:forEach var="section" items="<%=((ListSection) section).getListSection()%>">
                                    <li>${section}</li>
                                </c:forEach>
                            </ul>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="institution" items="<%=((ListInstitution) section).getListInstitution()%>">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${empty institution.homePage.url}">
                                        <h3>${institution.homePage.name}</h3>
                                    </c:when>
                                    <c:otherwise>
                                        <h3><a href="${institution.homePage.url}">${institution.homePage.name}</a></h3>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <c:forEach var="place" items="${institution.places}">
                            <jsp:useBean id="place" type="com.dofler.webapp.model.Place"/>
                            <tr>
                                <td>
                                    <b>Период:&nbsp;</b><%=HtmlUtil.formatDates(place)%><br/>
                                    <b>Название:&nbsp;</b>${place.title}<br/>
                                    <b>Описание:&nbsp;</b>${place.description}<br/>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
    </table>
    <br/>
    <button onclick="window.history.back()">ОК</button>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>