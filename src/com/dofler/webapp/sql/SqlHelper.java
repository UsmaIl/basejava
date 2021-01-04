package com.dofler.webapp.sql;

import com.dofler.webapp.exception.ExistStorageException;
import com.dofler.webapp.exception.StorageException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class SqlHelper {
    private final DataSource connectionPool;

    public SqlHelper(DataSource connectionPool) {
        this.connectionPool = connectionPool;
    }

    public <T> T perform(String sql, SQLExecutor<T> executor) {
        Objects.requireNonNull(executor);
        try (Connection conn = connectionPool.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            return executor.execute(preparedStatement);
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new ExistStorageException(null);
            }
            throw new StorageException(e);
        }
    }

    @FunctionalInterface
    public interface SQLExecutor<T> {
        T execute(PreparedStatement t) throws SQLException;
    }
}
