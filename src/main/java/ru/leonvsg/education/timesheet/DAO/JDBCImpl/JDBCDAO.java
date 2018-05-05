package ru.education.timesheet.DAO.JDBCImpl;

import ru.education.timesheet.ConnectionManager.ConnectionManager;
import ru.education.timesheet.DAO.Basic.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class JDBCDAO<ENTITY, KEY> implements DAO<ENTITY, KEY> {

    protected ConnectionManager connectionManager;

    public JDBCDAO(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    protected abstract PreparedStatement getPreparedStatementForInsert(Connection connection, ENTITY entity) throws SQLException;

    protected abstract PreparedStatement getPreparedStatementForSelect(Connection connection, KEY key) throws SQLException;

    protected abstract PreparedStatement getPreparedStatementForUpdate(Connection connection, ENTITY entity) throws SQLException;

    protected abstract PreparedStatement getPreparedStatementForDelete(Connection connection, KEY key) throws SQLException;

    protected abstract List<ENTITY> parseResultSet(ResultSet resultSet) throws SQLException;

    @Override
    public void create(ENTITY entity) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = getPreparedStatementForInsert(connection, entity);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public ENTITY read(KEY key) throws SQLException {
        List<ENTITY> entities = null;
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = getPreparedStatementForSelect(connection, key);
            ResultSet resultSet = statement.executeQuery();
            entities = parseResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        if (entities == null || entities.size() == 0) {
            return null;
        }
        if (entities.size() > 1) {
            throw new SQLException("Received more than one record");
        }
        return entities.iterator().next();
    }

    @Override
    public void update(ENTITY entity) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = getPreparedStatementForUpdate(connection, entity);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void delete(KEY key) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = getPreparedStatementForDelete(connection, key);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public List<ENTITY> getAll() {
        List<ENTITY> entities = null;
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = getPreparedStatementForSelect(connection, null);
            ResultSet resultSet = statement.executeQuery();
            entities = parseResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        if (entities == null || entities.size() == 0) {
            return null;
        }
        return entities;
    }
}
