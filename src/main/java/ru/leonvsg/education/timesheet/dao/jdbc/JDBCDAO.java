package ru.leonvsg.education.timesheet.dao.jdbc;

import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.connections.ConnectionManager;
import ru.leonvsg.education.timesheet.dao.basic.DAO;
import ru.leonvsg.education.timesheet.dao.basic.EntityPersistanceException;

import java.sql.*;
import java.util.List;

public abstract class JDBCDAO<ENTITY, KEY> implements DAO<ENTITY, KEY> {

    private static final Logger LOGGER = Logger.getLogger(JDBCDAO.class);
    protected static final String INVALID_PREPARED_STATEMENT_MESSAGE = "Something wrong with PreparedStatement";
    protected ConnectionManager connectionManager;

    protected JDBCDAO(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    protected abstract PreparedStatement getPreparedStatementForInsert(Connection connection, ENTITY entity) throws SQLException;

    protected abstract PreparedStatement getPreparedStatementForSelect(Connection connection, KEY key) throws SQLException;

    protected abstract PreparedStatement getPreparedStatementForUpdate(Connection connection, ENTITY entity) throws SQLException;

    protected abstract PreparedStatement getPreparedStatementForDelete(Connection connection, KEY key) throws SQLException;

    protected abstract List<ENTITY> parseResultSet(ResultSet resultSet) throws SQLException;

    @Override
    public boolean create(ENTITY entity) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = getPreparedStatementForInsert(connection, entity);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e);
            return false;
        }
    }

    @Override
    public ENTITY read(KEY key) throws EntityPersistanceException {
        List<ENTITY> entities = null;
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = getPreparedStatementForSelect(connection, key);
            ResultSet resultSet = statement.executeQuery();
            entities = parseResultSet(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        if (entities == null || entities.isEmpty()) {
            return null;
        }
        if (entities.size() > 1) {
            throw new EntityPersistanceException("Received more than one record");
        }
        return entities.iterator().next();
    }

    @Override
    public boolean update(ENTITY entity) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = getPreparedStatementForUpdate(connection, entity);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e);
            return false;
        }
    }

    @Override
    public boolean delete(KEY key) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = getPreparedStatementForDelete(connection, key);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e);
            return false;
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
            LOGGER.error(e);
        }
        return entities;
    }
}
