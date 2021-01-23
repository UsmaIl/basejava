package com.dofler.webapp.web;

import com.dofler.webapp.config.Config;
import com.dofler.webapp.model.ContactType;
import com.dofler.webapp.model.Resume;
import com.dofler.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;
    private List<Resume> resumes;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
        resumes = storage.getAllSorted();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r = storage.get(uuid);
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
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
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp"))
                .forward(request, response);

        /*Writer writer = response.getWriter();
        writer.write("<html>\n" +
                "   <head>\n" +
                "       <meta charset=UTF-8\">\n" +
                "       <title>Список всех резюме</title>\n" +
                "       <link rel=\"stylesheet\" href=\"css/style-table.css\">\n" +
                "   </head>\n" +
                "   <body>\n" +
                "       <section>\n" +
                "           <table>\n" +
                "               <caption>Контакты</caption>\n" +
                "               <tr>\n" +
                "                   <th>Имя</th>\n" +
                "                   <th>Тел.</th>\n" +
                "                   <th>Мессенджер</th>\n" +
                "                   <th>Почта</th>\n" +
                "                   <th>Профиль LinkedIn</th>\n" +
                "                   <th>Профиль GitHub</th>\n" +
                "                   <th>Профиль Stackoverflow</th>\n" +
                "                   <th>Домашняя страница</th>\n" +
                "               </tr>\n");
        for (Resume resume : resumes) {
            writer.write("               <tr>\n");
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                ContactType key = entry.getKey();
                String value = entry.getValue();
                if (key.ordinal() > 3) {
                    writer.write("                   <td><a href=\"" + value + "\">" + key.getTitle() + "</a></td>\n");
                } else {
                    writer.write("                   <td>" + value + "</td>\n");
                }
            }
        }
        writer.write("              </tr>\n" +
                "           </table>\n<br><br>\n" +
                "           <table>\n" +
                "               <caption>Секции</caption>\n" +
                "               <tr>\n" +
                "                   <th>Позиция</th>\n" +
                "                   <th>Личные качества</th>\n" +
                "                   <th>Достижения</th>\n" +
                "                   <th>Квалификация</th>\n" +
                "                   <th>Опыт работы</th>\n" +
                "                   <th>Образование</th>\n" +
                "               </tr>\n");
        for (Resume resume : resumes) {
            writer.write("               <tr>\n");
            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
                writer.write("                   <td>" + entry.getValue() + "</td>\n");
            }
            writer.write("               </tr>\n");
        }
        writer.write("          </table>\n" +
                "       </section>\n" +
                "   </body>\n" +
                "</html>\n");*/
    }
}
