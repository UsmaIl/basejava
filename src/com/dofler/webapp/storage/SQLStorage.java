package com.dofler.webapp.storage;

import com.dofler.webapp.exception.NotExistStorageException;
import com.dofler.webapp.exception.StorageException;
import com.dofler.webapp.model.AbstractSection;
import com.dofler.webapp.model.ContactType;
import com.dofler.webapp.model.Resume;
import com.dofler.webapp.model.SectionType;
import com.dofler.webapp.sql.SqlHelper;
import com.dofler.webapp.util.XmlParser;
import org.postgresql.ds.PGSimpleDataSource;

import javax.xml.bind.JAXBException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SQLStorage implements Storage {
    //public final ConnectionFactory connectionFactory;
    public final SqlHelper source;

    public SQLStorage(String dbUrl, String dbUser, String dbPassword) {
        //connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setApplicationName(dbUrl);
        dataSource.setUser(dbUser);
        dataSource.setPassword(dbPassword);
        source = new SqlHelper(dataSource);
    }

    @Override
    public void clear() {
        source.perform("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume resume) {
        source.performTransaction(conn -> {
            String uuid = resume.getUuid();
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }

            if (!resume.getContacts().equals(get(resume.getUuid()).getContacts())) {
                deleteContacts(conn, uuid);
                insertContacts(resume, conn);
            }

            if (!resume.getSections().equals(get(resume.getUuid()).getSections())) {
                deleteSections(conn, uuid);
                insertSections(resume, conn);
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        source.performTransaction(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            insertContacts(resume, conn);
            insertSections(resume, conn);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        source.perform("DELETE FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return source.performTransaction(conn -> {
            Resume r;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume WHERE uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                r = new Resume(uuid, rs.getString("full_name"));
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContact(rs, r);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(rs, r);
                }
            } catch (JAXBException e) {
                throw new StorageException(e);
            }
            return r;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return source.perform("" +
                "SELECT * " +
                "FROM resume r " +
                "LEFT JOIN contact c ON r.uuid = c.resume_uuid " +
                "ORDER BY full_name, uuid", ps -> {
            ResultSet resultSet = ps.executeQuery();
            Map<String, Resume> map = new LinkedHashMap<>();
            while (resultSet.next()) {
                String uuid = resultSet.getString("uuid");
                Resume resume = map.get(uuid);
                if (resume == null) {
                    resume = new Resume(uuid, resultSet.getString("full_name"));
                    map.put(uuid, resume);
                }
                addContact(resultSet, resume);
            }
            return new ArrayList<>(map.values());
        });
    }

    @Override
    public int size() {
        return source.perform("SELECT count(*) FROM resume", ps -> {
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        });
    }

    private void addContact(ResultSet resultSet, Resume r) throws SQLException {
        String value = resultSet.getString("value");
        if (value != null) {
            ContactType type = ContactType.valueOf(resultSet.getString("type"));
            r.addContact(type, value);
        }
    }

    private void addSection(ResultSet rs, Resume r) throws SQLException, JAXBException {
        String section = rs.getString("value");
        if (section != null) {
            SectionType type = SectionType.valueOf(rs.getString("type"));
            XmlParser xmlParser = new XmlParser(AbstractSection.class);
            r.addSection(type, xmlParser.unmarshall(section));
        }
    }

    private void insertContacts(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, entry.getKey().name());
                XmlParser xmlParser = new XmlParser(AbstractSection.class);
                ps.setString(3, xmlParser.marshall(entry.getValue()));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(Connection conn, String uuid) throws SQLException {
        delete(conn, uuid, "DELETE FROM contact WHERE resume_uuid=?");
    }

    private void deleteSections(Connection conn, String uuid) throws SQLException {
        delete(conn, uuid, "DELETE FROM section WHERE resume_uuid=?");
    }

    private void delete(Connection conn, String uuid, String sql) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, uuid);
            ps.execute();
        };
    }
}
