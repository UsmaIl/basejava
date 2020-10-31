import com.dofler.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class ResumeTestData {
    public static void main(String[] args) {
        var myContact = new HashMap<ContactType, String>();
        myContact.put(ContactType.NAME, "Григорий");
        myContact.put(ContactType.PHONE_NUMBER, "+7(921) 855-0482");
        myContact.put(ContactType.MESSENGER, "Skype: grigory.kislin");
        myContact.put(ContactType.MAIL, "gkislin@yandex.ru");
        myContact.put(ContactType.LINKEDIN, "[Ссылка]Профиль LinkedIn");
        myContact.put(ContactType.GITHUB, "[Ссылка]Профиль GitHub");
        myContact.put(ContactType.STACKOVERFLOW, "[Ссылка]Профиль Stackoverflow");
        myContact.put(ContactType.HOME_PAGE, "[Ссылка]Домашняя страница");

        var myPositions = new HashMap<SectionType, Contentable>();
        myPositions.put(SectionType.OBJECTIVE, new TextSection(
                "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        myPositions.put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        var myAchievements = new ArrayList<TextSection>();
        myAchievements.add(new TextSection("С 2013 года: разработка проектов " +
                "\"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). " +
                "Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". " +
                "Организация онлайн стажировок и ведение проектов. Более 1000 выпускников."));
        myAchievements.add(new TextSection("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk."));
        myAchievements.add(new TextSection("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. " +
                "Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера."));

        myPositions.put(SectionType.ACHIEVEMENT, new Section<>(myAchievements));

        var myQualifications = new ArrayList<TextSection>();
        myQualifications.add(new TextSection("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2"));
        myQualifications.add(new TextSection("Version control: Subversion, Git, Mercury, ClearCase, Perforce"));
        myQualifications.add(new TextSection("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle"));
        myQualifications.add(new TextSection("MySQL, SQLite, MS SQL, HSQLDB"));
        myQualifications.add(new TextSection("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,"));
        myPositions.put(SectionType.QUALIFICATIONS, new Section<>(myQualifications));

        var myJobs = new ArrayList<Institution>();
        myJobs.add(new Institution(new Link("Java Online Projects", "http://javaops.ru/"),
                LocalDate.of(2013, 10, 1),
                LocalDate.now(),
                "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок."));
        myJobs.add(new Institution(new Link("Wrike", "https://www.wrike.com/"),
                LocalDate.of(2014, 10, 1),
                LocalDate.of(2016, 1, 1),
                "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, " +
                        "Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                        "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        myJobs.add(new Institution(new Link("RIT Center", null),
                LocalDate.of(2012, 4, 1),
                LocalDate.of(2014, 10, 1),
                "Java архитектор",
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, " +
                        "ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. " +
                        "Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, " +
                        "1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html)." +
                        " Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. " +
                        "Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, " +
                        "xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"));
        myPositions.put(SectionType.EXPERIENCE, new Section<>(myJobs));

        var myEducations = new ArrayList<Institution>();
        myEducations.add(new Institution(new Link("Coursera", "https://www.coursera.org/course/progfun"),
                LocalDate.of(2013, 3, 1),
                LocalDate.of(2013, 5, 1),
                "\"Functional Programming Principles in Scala\" by Martin Odersky",
                ""));
        myEducations.add(new Institution(new Link("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366"),
                LocalDate.of(2011, 3, 1),
                LocalDate.of(2011, 4, 1),
                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.",
                ""));
        myEducations.add(new Institution(new Link("Siemens AG", "http://www.siemens.ru/"),
                LocalDate.of(2005, 1, 1),
                LocalDate.of(2005, 4, 1),
                "3 месяца обучения мобильным IN сетям (Берлин)",
                ""));
        myPositions.put(SectionType.EDUCATION, new Section<>(myEducations));

        var myResume = new Resume("Григорий Кислин");
        myResume.setContacts(myContact);
        myResume.setSections(myPositions);


    }
}
