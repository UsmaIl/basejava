package com.dofler.webapp.web;

import com.dofler.webapp.config.Config;
import com.dofler.webapp.model.*;
import com.dofler.webapp.storage.Storage;
import com.dofler.webapp.util.DateUtil;
import com.dofler.webapp.util.HtmlUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r = storage.get(uuid);
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String typeName = request.getParameter(type.name());
            if (HtmlUtil.isEmpty(typeName)) {
                r.getContacts().remove(type);
            } else {
                r.addContact(type, typeName);
            }
        }
        for (SectionType type : SectionType.values()) {
            String typeName = request.getParameter(type.name());
            String[] typeNames = request.getParameterValues(type.name());
            if (HtmlUtil.isEmpty(typeName) && typeNames.length < 2) {
                r.getSections().remove(type);
            } else {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        r.addSection(type, new TextSection(typeName));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        r.addSection(type, new ListSection(Arrays.asList(typeName.split("\\n"))));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Institution> institutions = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "_url");
                        for (int i = 0; i < typeNames.length; i++) {
                            String name = typeNames[i];
                            if (!HtmlUtil.isEmpty(name)) {
                                List<Place> places = new ArrayList<>();
                                String typeNameN = type.name() + i;
                                String[] startDates = request.getParameterValues(typeNameN + "_startDate");
                                String[] endDates = request.getParameterValues(typeNameN + "_endDate");
                                String[] titles = request.getParameterValues(typeNameN + "_title");
                                String[] descriptions = request.getParameterValues(typeNameN + "_description");
                                for (int j = 0; j < titles.length; j++) {
                                    if (!HtmlUtil.isEmpty(titles[j])) {
                                        places.add(new Place(DateUtil.parse(startDates[j]), DateUtil.parse(endDates[j]), titles[j], descriptions[j]));
                                    }
                                }
                                institutions.add(new Institution(new Link(name, urls[i]), places));
                            }
                        }
                        r.addSection(type, new ListInstitution(institutions));
                        break;
                }
            }
        }
        storage.update(r);
        response.sendRedirect("resume");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = storage.get(uuid);
                for (SectionType type : new SectionType[]{SectionType.EXPERIENCE, SectionType.EDUCATION}) {
                    ListInstitution institutions = (ListInstitution) r.getSection(type);
                    List<Institution> emptyFirstInstitutions = new ArrayList<>();
                    emptyFirstInstitutions.add(Institution.EMPTY);
                    if (institutions != null) {
                        for (Institution institution : institutions.getListInstitution()) {
                            List<Place> emptyFirstPositions = new ArrayList<>();
                            emptyFirstPositions.add(Place.EMPTY);
                            emptyFirstPositions.addAll(institution.getPlaces());
                            emptyFirstInstitutions.add(new Institution(institution.getHomePage(), emptyFirstPositions));
                        }
                    }
                    r.addSection(type, new ListInstitution(emptyFirstInstitutions));
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp"))
                .forward(request, response);
    }
}
