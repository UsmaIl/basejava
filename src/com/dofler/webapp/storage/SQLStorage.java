package com.dofler.webapp.storage;

import com.dofler.webapp.exception.NotExistStorageException;
import com.dofler.webapp.model.Resume;
import com.dofler.webapp.sql.SqlHelper;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
        source.perform("UPDATE resume SET full_name = ? WHERE uuid = ?", ps -> {
            String uuid = resume.getUuid();
            ps.setString(1, resume.getFullName());
            ps.setString(2, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        source.perform("INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        source.perform("DELETE FROM resume r WHERE r.uuid =?",
                ps -> {
                    ps.setString(1, uuid);
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                });
    }

    @Override
    public Resume get(String uuid) {
        return source.perform("SELECT * FROM resume r WHERE r.uuid =?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet resultSet = ps.executeQuery();
                    if (!resultSet.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    return new Resume(uuid, resultSet.getString("full_name"));
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        return source.perform("SELECT * FROM resume r ORDER BY full_name,uuid", ps -> {
            ResultSet resultSet = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (resultSet.next()) {
                resumes.add(new Resume(resultSet.getString("uuid"), resultSet.getString("full_name")));
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        return source.perform("SELECT count(*) FROM resume", ps -> {
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        });
    }
}
