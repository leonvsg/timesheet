package ru.leonvsg.education.timesheet.dao.jdbc;

import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.connections.ConnectionManager;
import ru.leonvsg.education.timesheet.dao.basic.SessionDAO;
import ru.leonvsg.education.timesheet.entities.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCSessionDAO extends JDBCDAO<Session, String> implements SessionDAO {

    private static final Logger LOGGER = Logger.getLogger(JDBCSessionDAO.class);

    public JDBCSessionDAO(ConnectionManager connectionManager) {
        super(connectionManager);
    }

    @Override
    protected PreparedStatement getPreparedStatementForInsert(Connection connection, Session session) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO timesheet.sessions (userid, token) " +
                            "VALUES (?, ?)"
            );
            statement.setInt(1, session.getUserId());
            statement.setString(2, session.getToken());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new SQLException(INVALID_PREPARED_STATEMENT_MESSAGE);
        }
        return statement;
    }

    @Override
    protected PreparedStatement getPreparedStatementForSelect(Connection connection, String s) throws SQLException {
        PreparedStatement statement = null;
        try {
            if (s == null)
                statement = connection.prepareStatement(
                        "SELECT * FROM timesheet.sessions"
                );
            else {
                statement = connection.prepareStatement(
                        "SELECT * FROM timesheet.sessions WHERE token=?"
                );
                statement.setString(1, s);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new SQLException(INVALID_PREPARED_STATEMENT_MESSAGE);
        }
        return statement;
    }

    @Override
    protected PreparedStatement getPreparedStatementForUpdate(Connection connection, Session session) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "UPDATE timesheet.sessions SET userid=? WHERE token=?"
            );
            statement.setInt(1, session.getUserId());
            statement.setString(2, session.getToken());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new SQLException(INVALID_PREPARED_STATEMENT_MESSAGE);
        }
        return statement;
    }

    @Override
    protected PreparedStatement getPreparedStatementForDelete(Connection connection, String s) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "DELETE FROM timesheet.sessions WHERE token=?"
            );
            statement.setString(1, s);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new SQLException(INVALID_PREPARED_STATEMENT_MESSAGE);
        }
        return statement;
    }

    @Override
    protected List<Session> parseResultSet(ResultSet resultSet) throws SQLException {
        List<Session> sessions = new ArrayList<>();
        try {
            while (resultSet.next()) {
                sessions.add(new Session(
                        resultSet.getInt("userid"),
                        resultSet.getString("token")
                ));
            }
        } catch (Exception e) {
            throw new SQLException("Incorrect ResultSet");
        }
        return sessions;
    }

    @Override
    public List<Session> getSessionsByUser(User user) {
        return getSessionsByUser(user.getId());
    }

    @Override
    public List<Session> getSessionsByUser(Integer userId) {
        List<Session> sessions = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT token FROM timesheet.sessions WHERE userid=?");
            statement.setInt(1, userId);
            sessions = executeMulti(statement);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return sessions;
    }

}
