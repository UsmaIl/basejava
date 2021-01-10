package com.dofler.webapp.storage;

import com.dofler.webapp.exception.NotExistStorageException;
import com.dofler.webapp.model.ContactType;
import com.dofler.webapp.model.Resume;
import com.dofler.webapp.sql.SqlHelper;
import org.postgresql.ds.PGSimpleDataSource;

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
                deleteContacts(uuid);
                insertContacts(resume, conn);
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
        return source.perform("" +
                "SELECT * FROM resume r " +
                "LEFT JOIN contact c " +
                "ON r.uuid = c.resume_uuid " +
                "WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            ResultSet resultSet = ps.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume r = new Resume(uuid, resultSet.getString("full_name"));
            do {
                addContact(resultSet, r);
            } while (resultSet.next());

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

    private void deleteContacts(String uuid) {
        source.perform("DELETE FROM contact WHERE resume_uuid = ?", ps -> {
            ps.setString(1, uuid);
            ps.execute();
            return null;
        });
    }
}
