import com.dofler.webapp.model.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ResumeTestData {
    public static void main(String[] args) {
        var myContact = new LinkedHashMap<ContactType, String>();
        myContact.put(ContactType.NAME, "Григорий Кислин");
        myContact.put(ContactType.PHONE_NUMBER, "+7(921) 855-0482");
        myContact.put(ContactType.MESSENGER, "Skype: grigory.kislin");
        myContact.put(ContactType.MAIL, "gkislin@yandex.ru");
        myContact.put(ContactType.LINKEDIN, "[Ссылка]Профиль LinkedIn");
        myContact.put(ContactType.GITHUB, "[Ссылка]Профиль GitHub");
        myContact.put(ContactType.STACKOVERFLOW, "[Ссылка]Профиль Stackoverflow");
        myContact.put(ContactType.HOME_PAGE, "[Ссылка]Домашняя страница");

        var myPositions = new LinkedHashMap<SectionType, Section>();
        myPositions.put(SectionType.OBJECTIVE, new TextSection(
                "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        myPositions.put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        var myAchievements = new ArrayList<String>();
        myAchievements.add("С 2013 года: разработка проектов " +
                "\"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). " +
                "Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". " +
                "Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        myAchievements.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        myAchievements.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. " +
                "Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");

        myPositions.put(SectionType.ACHIEVEMENT, new ListSection(myAchievements));

        var myQualifications = new ArrayList<String>();
        myQualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        myQualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        myQualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");
        myQualifications.add("MySQL, SQLite, MS SQL, HSQLDB");
        myQualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        myPositions.put(SectionType.QUALIFICATIONS, new ListSection(myQualifications));

        var myJobs = new ArrayList<Institution>();
        myJobs.add(new Institution(new Link("Java Online Projects", "http://javaops.ru/"),
                List.of(new Place(2013, 10, "Автор проекта.",
                        "Создание, организация и проведение Java онлайн проектов и стажировок."))));
        myJobs.add(new Institution(new Link("Wrike", "https://www.wrike.com/"),
                List.of(new Place(2014, 10, 2016, 1,
                        "Старший разработчик (backend)",
                        "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, " +
                                "Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                                "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."))));
        myJobs.add(new Institution(new Link("RIT Center", null),
                List.of(new Place(2012, 4, 2014, 10,
                        "Java архитектор",
                        "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, " +
                                "ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. " +
                                "Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, " +
                                "1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html)." +
                                " Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. " +
                                "Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, " +
                                "xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"))));
        myPositions.put(SectionType.EXPERIENCE, new ListInstitution(myJobs));

        var myEducations = new ArrayList<Institution>();
        myEducations.add(new Institution(new Link("Coursera", "https://www.coursera.org/course/progfun"),
                List.of(new Place(2013, 3, 2013, 5,
                        "\"Functional Programming Principles in Scala\" by Martin Odersky",
                        ""))));
        myEducations.add(new Institution(new Link("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366"),
                List.of(new Place(2011, 3, 2011, 4,
                        "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.",
                        ""))));
        myEducations.add(new Institution(new Link("Siemens AG", "http://www.siemens.ru/"),
                List.of(new Place(2005, 1, 2005, 4,
                        "3 месяца обучения мобильным IN сетям (Берлин)", ""))));
        myEducations.add(new Institution(new Link("Санкт-Петербургский национальный исследовательский университет " +
                "информационных технологий, механики и оптики", "http://www.ifmo.ru/"),
                List.of(new Place(1993, 9, 1996, 7,
                                "Аспирантура (программист С, С++)", ""),
                        new Place(1987, 9, 1993, 3, "Инженер (программист Fortran, C)", ""))));
        myPositions.put(SectionType.EDUCATION, new ListInstitution(myEducations));

        var myResume = new Resume("Григорий Кислин");
        myResume.setContacts(myContact);
        myResume.setSections(myPositions);

        System.out.println(myResume);
    }

    public Resume of(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        var contact = new LinkedHashMap<ContactType, String>();
        contact.put(ContactType.NAME, "Иван Иванов");
        contact.put(ContactType.PHONE_NUMBER, "+0(000) 000-0000");
        contact.put(ContactType.MESSENGER, "Skype: ivan.ivanov");
        contact.put(ContactType.MAIL, "ivanov@mail.ru");
        contact.put(ContactType.LINKEDIN, "[Ссылка]Профиль LinkedIn");
        contact.put(ContactType.GITHUB, "[Ссылка]Профиль GitHub");
        contact.put(ContactType.STACKOVERFLOW, "[Ссылка]Профиль Stackoverflow");
        contact.put(ContactType.HOME_PAGE, "[Ссылка]Домашняя страница");

        resume.setContacts(contact);

        var positions = new LinkedHashMap<SectionType, Section>();
        positions.put(SectionType.OBJECTIVE, new TextSection("Работяга"));
        positions.put(SectionType.PERSONAL, new TextSection("Трудолюбивый"));
        positions.put(SectionType.ACHIEVEMENT, new TextSection("10000 лет опыта"));
        positions.put(SectionType.QUALIFICATIONS, new TextSection("Java SE"));
        positions.put(SectionType.EXPERIENCE, new Institution(new Link("ТОПРАБОТА", "http://topwork.ru/"),
                List.of(new Place(1990, 12, "Это лучшая работа в мире", "Бог"))));
        positions.put(SectionType.EDUCATION, new Institution(new Link("ТОПВУЗ", "http://topvuz.ru/"),
                List.of(new Place(1980, 12, "Лучший вуз планеты", ""))));

        resume.setSections(positions);
        return resume;
    }
}
