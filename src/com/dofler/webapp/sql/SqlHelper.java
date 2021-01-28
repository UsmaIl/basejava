package com.dofler.webapp.sql;

import com.dofler.webapp.exception.StorageException;
import com.dofler.webapp.storage.ConnectionFactory;
import com.dofler.webapp.util.ExceptionUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class SqlHelper {
    private final ConnectionFactory connectionPool;

    public SqlHelper(ConnectionFactory connectionPool) {
        this.connectionPool = connectionPool;
    }

    public <T> T perform(String sql, SQLExecutor<T> executor) {
        Objects.requireNonNull(executor);
        try (Connection conn = connectionPool.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            return executor.execute(preparedStatement);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

    public <T> T performTransaction(SqlTransaction<T> executor) {
        try (Connection conn = connectionPool.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T result = executor.execute(conn);
                conn.commit();
                return result;
            } catch (SQLException e) {
                conn.rollback();
                throw ExceptionUtil.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @FunctionalInterface
    public interface SQLExecutor<T> {
        T execute(PreparedStatement t) throws SQLException;
    }

    @FunctionalInterface
    public interface SqlTransaction<T> {
        T execute(Connection conn) throws SQLException;
    }
}
