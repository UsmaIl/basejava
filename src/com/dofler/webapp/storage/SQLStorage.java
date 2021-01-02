package com.dofler.webapp.storage;

import com.dofler.webapp.exception.ExistStorageException;
import com.dofler.webapp.exception.NotExistStorageException;
import com.dofler.webapp.exception.StorageException;
import com.dofler.webapp.model.Resume;
import com.dofler.webapp.sql.JDBCTemplate;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLStorage implements Storage {
    //public final ConnectionFactory connectionFactory;
    public final JDBCTemplate source;

    public SQLStorage(String dbUrl, String dbUser, String dbPassword) {
        //connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setApplicationName(dbUrl);
        dataSource.setUser(dbUser);
        dataSource.setPassword(dbPassword);
        source = new JDBCTemplate(dataSource);
    }

    @Override
    public void clear() {
        try {
            source.preparedStatement("DELETE FROM resume",
                    (JDBCTemplate.SQLConsumer<? super PreparedStatement>) PreparedStatement::execute);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void update(Resume resume) {
        try {
            source.preparedStatement("UPDATE resume SET full_name = ? WHERE uuid = ?", ps -> {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            });
        } catch (SQLException e) {
            throw new StorageException(e);
        }

    }

    @Override
    public void save(Resume resume) {
        try {
            source.preparedStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            });
        }
        catch (SQLException e) {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void delete(String uuid) {
        try {
            source.preparedStatement("DELETE FROM resume r WHERE r.uuid =?",
                    ps -> {
                        ps.setString(1, uuid);
                        if (ps.executeUpdate() == 0) {
                            throw new NotExistStorageException(uuid);
                        }
                    });
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public Resume get(String uuid) {
        try {
            return source.preparedStatement("SELECT * FROM resume r WHERE r.uuid =?",
                    ps -> {
                        ps.setString(1, uuid);
                        ResultSet resultSet = ps.executeQuery();
                        if (!resultSet.next()) {
                            throw new NotExistStorageException(uuid);
                        }
                        return new Resume(uuid, resultSet.getString("full_name"));
                    });
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        try {
            return source.preparedStatement("SELECT * FROM resume r ORDER BY full_name,uuid", ps -> {
                ResultSet resultSet = ps.executeQuery();
                List<Resume> resumes = new ArrayList<>();
                while (resultSet.next()) {
                    resumes.add(new Resume(resultSet.getString("uuid"), resultSet.getString("full_name")));
                }
                return resumes;
            });
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public int size() {
        try {
            return source.preparedStatement("SELECT count(*) FROM resume", ps -> {
                ResultSet resultSet = ps.executeQuery();
                return resultSet.next() ? resultSet.getInt(1) : 0;
            });
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
