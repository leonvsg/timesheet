package ru.leonvsg.education.timesheet.dao.jdbc;


import ru.leonvsg.education.timesheet.connections.ConnectionManager;
import ru.leonvsg.education.timesheet.dao.basic.UserDAO;
import ru.leonvsg.education.timesheet.entities.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCUserDAO extends JDBCDAO<User, Integer> implements UserDAO {

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
            statement.setString(3, user.getRegDate().toString());
            statement.setString(4, user.getRole());
            statement.setString(5, user.getName());
            statement.setString(6, user.getMiddleName());
            statement.setString(7, user.getSurname());
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            throw new SQLException("Something wrong with PreparedStatement");
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
            e.printStackTrace(System.out);
            throw new SQLException("Something wrong with PreparedStatement");
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
            e.printStackTrace(System.out);
            throw new SQLException("Something wrong with PreparedStatement");
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
            e.printStackTrace(System.out);
            throw new SQLException("Something wrong with PreparedStatement");
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
    public List<Rating> getRating(User user) {
        return getRating(user.getId());
    }

    @Override
    public List<Rating> getRating(Integer userId) {
        List<Rating> ratings = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM timesheet.rating WHERE userid=?");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ratings.add(new Rating(
                        resultSet.getInt("id"),
                        resultSet.getInt("userId"),
                        resultSet.getInt("lessonid"),
                        resultSet.getInt("value"),
                        resultSet.getString("description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ratings;
    }

    @Override
    public List<Group> getGroups(User user) {
        return getGroups(user.getId());
    }

    @Override
    public List<Group> getGroups(Integer userId) {
        List<Group> groups = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT g.groupid, g.groupname, g.courseid, g.startdate, g.expdate, g.description " +
                            "FROM timesheet.members AS m " +
                            "LEFT JOIN timesheet.groups AS g ON m.groupid = g.groupid " +
                            "WHERE m.userid=?");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                groups.add(new Group(
                        resultSet.getInt("groupid"),
                        resultSet.getString("groupname"),
                        resultSet.getInt("courseid"),
                        resultSet.getString("startdate"),
                        resultSet.getString("expdate"),
                        resultSet.getString("description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

    @Override
    public List<Session> getSessions(User user) {
        return getSessions(user.getId());
    }

    @Override
    public List<Session> getSessions(Integer userId) {
        List<Session> sessions = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT token FROM timesheet.sessions WHERE userid=?");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                sessions.add(new Session(
                        userId,
                        resultSet.getString("token")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessions;
    }
}
