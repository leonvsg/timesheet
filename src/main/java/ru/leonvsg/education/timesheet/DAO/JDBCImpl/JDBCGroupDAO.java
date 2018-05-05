package ru.education.timesheet.DAO.JDBCImpl;

import ru.education.timesheet.ConnectionManager.ConnectionManager;
import ru.education.timesheet.DAO.Basic.GroupDAO;
import ru.education.timesheet.Entities.Course;
import ru.education.timesheet.Entities.Group;
import ru.education.timesheet.Entities.Lesson;
import ru.education.timesheet.Entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCGroupDAO extends JDBCDAO<Group, Integer> implements GroupDAO {

    public JDBCGroupDAO(ConnectionManager connectionManager) {
        super(connectionManager);
    }

    @Override
    protected PreparedStatement getPreparedStatementForInsert(Connection connection, Group group) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO timesheet.groups (groupname, courseid, startdate, expdate, description) " +
                            "VALUES (?, ?, ?, ?, ?)"
            );
            statement.setString(1, group.getName());
            statement.setInt(2, group.getCourseId());
            statement.setString(3, group.getStartDate());
            statement.setString(4, group.getExpDate());
            statement.setString(5, group.getDescription());
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
                        "SELECT * FROM timesheet.groups"
                );
            else {
                statement = connection.prepareStatement(
                        "SELECT * FROM timesheet.groups WHERE groupid=?"
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
    protected PreparedStatement getPreparedStatementForUpdate(Connection connection, Group group) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "UPDATE timesheet.groups SET groupname=?, courseid=?, startdate=?, expdate=?, description=? WHERE groupid=?"
            );
            statement.setString(1, group.getName());
            statement.setInt(2, group.getCourseId());
            statement.setString(3, group.getStartDate());
            statement.setString(4, group.getExpDate());
            statement.setString(5, group.getDescription());
            statement.setInt(6, group.getId());
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
                    "DELETE FROM timesheet.groups WHERE groupid=?"
            );
            statement.setInt(1, key);
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            throw new SQLException("Something wrong with PreparedStatement");
        }
        return statement;
    }

    @Override
    protected List<Group> parseResultSet(ResultSet resultSet) throws SQLException {
        List<Group> groups = new ArrayList<>();
        try {
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
        } catch (Exception e) {
            throw new SQLException("Incorrect ResultSet");
        }
        return groups;
    }

    @Override
    public List<User> getUsers(Group group) {
        return getUsers(group.getId());
    }

    @Override
    public List<User> getUsers(Integer groupId) {
        List<User> users = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT u.userid, u.login, u.password, u.regdate, u.role, u.name, u.middlename, u.surname " +
                            "FROM timesheet.members AS m " +
                            "LEFT JOIN timesheet.users AS u ON m.userid = u.userid " +
                            "WHERE m.groupid=?");
            statement.setInt(1, groupId);
            ResultSet resultSet = statement.executeQuery();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<Lesson> getLessons(Group group) {
        return getLessons(group.getId());
    }

    @Override
    public List<Lesson> getLessons(Integer groupId) {
        List<Lesson> lessons = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM timesheet.lessons WHERE groupid=?");
            statement.setInt(1, groupId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                lessons.add(new Lesson(
                        resultSet.getInt("lessonid"),
                        resultSet.getInt("groupid"),
                        resultSet.getString("description"),
                        resultSet.getString("date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lessons;
    }

    @Override
    public Course getCourse(Group group) {
        return getCourse(group.getId());
    }

    @Override
    public Course getCourse(Integer groupId) {
        Course course = null;
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT c.courseid, c.coursename, c.description, c.duration " +
                            "FROM timesheet.groups AS g " +
                            "LEFT JOIN timesheet.courses AS c ON g.courseid = c.courseid" +
                            "WHERE g.groupid=?");
            statement.setInt(1, groupId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                course = new Course(
                        resultSet.getInt("courseid"),
                        resultSet.getString("coursename"),
                        resultSet.getString("description"),
                        resultSet.getInt("duration")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return course;
    }
}
