package ru.leonvsg.education.timesheet.dao.jdbc;

import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.connections.ConnectionManager;
import ru.leonvsg.education.timesheet.dao.basic.UserDAO;
import ru.leonvsg.education.timesheet.entities.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class JDBCUserDAO extends JDBCDAO<User, Integer> implements UserDAO {

    private static final Logger LOGGER = Logger.getLogger(JDBCUserDAO.class);

    public JDBCUserDAO(ConnectionManager connectionManager) {
        super(connectionManager);
    }

    @Override
    protected PreparedStatement getPreparedStatementForInsert(Connection connection, User user) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO timesheet.users (login, password, regdate, role, name, middlename, surname) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRegDate());
            statement.setString(4, user.getRole());
            statement.setString(5, user.getName());
            statement.setString(6, user.getMiddleName());
            statement.setString(7, user.getSurname());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new SQLException(INVALID_PREPARED_STATEMENT_MESSAGE);
        }
        return statement;
    }

    @Override
    protected PreparedStatement getPreparedStatementForSelect(Connection connection, Integer key) throws SQLException {
        PreparedStatement statement = null;
        try {
            if (key == null)
                statement = connection.prepareStatement(
                        "SELECT * FROM timesheet.users"
                );
            else {
                statement = connection.prepareStatement(
                        "SELECT * FROM timesheet.users WHERE userid=?"
                );
                statement.setInt(1, key);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new SQLException(INVALID_PREPARED_STATEMENT_MESSAGE);
        }
        return statement;
    }

    @Override
    protected PreparedStatement getPreparedStatementForUpdate(Connection connection, User user) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "UPDATE timesheet.users SET login=?, password=?, role=?, name=?, middlename=?,surname=? WHERE userid=?"
            );
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            statement.setString(4, user.getName());
            statement.setString(5, user.getMiddleName());
            statement.setString(6, user.getSurname());
            statement.setInt(7, user.getId());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new SQLException(INVALID_PREPARED_STATEMENT_MESSAGE);
        }
        return statement;
    }

    @Override
    protected PreparedStatement getPreparedStatementForDelete(Connection connection, Integer key) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "DELETE FROM timesheet.users WHERE userid=?"
            );
            statement.setInt(1, key);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new SQLException(INVALID_PREPARED_STATEMENT_MESSAGE);
        }
        return statement;
    }

    @Override
    protected List<User> parseResultSet(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        try {
            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getInt("userid"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("regdate"),
                        resultSet.getString("role"),
                        resultSet.getString("name"),
                        resultSet.getString("middlename"),
                        resultSet.getString("surname")
                ));
            }
        } catch (Exception e) {
            throw new SQLException("Incorrect ResultSet");
        }
        return users;
    }

    @Override
    public User getUserByLogin(String login) {
        User user = null;
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM timesheet.users WHERE login=?");
            statement.setString(1, login);
            user = execute(statement);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return user;
    }

    @Override
    public User getUserByToken(String token) {
        User user = null;
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT u.userid, u.login, u.password, u.regdate, u.role, u.name, u.middlename, u.surname " +
                            "FROM timesheet.sessions AS s " +
                            "LEFT JOIN timesheet.users AS u ON s.userid = u.userid " +
                            "WHERE s.token=?");
            statement.setString(1, token);
            user = execute(statement);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return user;
    }

    @Override
    public User getUserByRating(Rating rating) {
        return getUserByRating(rating.getId());
    }

    @Override
    public User getUserByRating(Integer ratingId) {
        User user = null;
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT u.userid, u.login, u.password, u.regdate, u.role, u.name, u.middlename, u.surname " +
                            "FROM timesheet.rating AS r " +
                            "LEFT JOIN timesheet.users AS u ON r.userid = u.userid" +
                            "WHERE r.id=?");
            statement.setInt(1, ratingId);
            user = execute(statement);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return user;
    }

    @Override
    public List<User> getUsersByGroup(Group group) {
        return getUsersByGroup(group.getId());
    }

    @Override
    public List<User> getUsersByGroup(Integer groupId) {
        List<User> users = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT u.userid, u.login, u.password, u.regdate, u.role, u.name, u.middlename, u.surname " +
                            "FROM timesheet.members AS m " +
                            "LEFT JOIN timesheet.users AS u ON m.userid = u.userid " +
                            "WHERE m.groupid=?");
            statement.setInt(1, groupId);
            users = executeMulti(statement);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return users;
    }
}
