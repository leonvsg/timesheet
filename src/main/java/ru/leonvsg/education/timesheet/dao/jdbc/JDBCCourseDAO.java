package ru.leonvsg.education.timesheet.dao.jdbc;


import ru.leonvsg.education.timesheet.connections.ConnectionManager;
import ru.leonvsg.education.timesheet.dao.basic.CourseDAO;
import ru.leonvsg.education.timesheet.entities.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCCourseDAO extends JDBCDAO<Course, Integer> implements CourseDAO {

    public JDBCCourseDAO(ConnectionManager connectionManager) {
        super(connectionManager);
    }

    @Override
    protected PreparedStatement getPreparedStatementForInsert(Connection connection, Course course) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO timesheet.courses (coursename, description, duration) " +
                            "VALUES (?, ?, ?)"
            );
            statement.setString(1, course.getName());
            statement.setString(2, course.getDescription());
            statement.setInt(3, course.getDuration());
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
                        "SELECT * FROM timesheet.courses"
                );
            else {
                statement = connection.prepareStatement(
                        "SELECT * FROM timesheet.courses WHERE userid=?"
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
    protected PreparedStatement getPreparedStatementForUpdate(Connection connection, Course course) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "UPDATE timesheet.courses SET coursename=?, description=?, duration=? WHERE courseid=?"
            );
            statement.setString(1, course.getName());
            statement.setString(2, course.getDescription());
            statement.setInt(3, course.getDuration());
            statement.setInt(4, course.getId());
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
                    "DELETE FROM timesheet.courses WHERE courseid=?"
            );
            statement.setInt(1, key);
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            throw new SQLException("Something wrong with PreparedStatement");
        }
        return statement;
    }

    @Override
    protected List<Course> parseResultSet(ResultSet resultSet) throws SQLException {
        List<Course> courses = new ArrayList<>();
        try {
            while (resultSet.next()) {
                courses.add(new Course(
                        resultSet.getInt("courseid"),
                        resultSet.getString("coursename"),
                        resultSet.getString("description"),
                        resultSet.getInt("duration")
                ));
            }
        } catch (Exception e) {
            throw new SQLException("Incorrect ResultSet");
        }
        return courses;
    }

    @Override
    public List<Group> getGroups(Course course) {
        return getGroups(course.getId());
    }

    @Override
    public List<Group> getGroups(Integer courseId) {
        List<Group> groups = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM timesheet.groups WHERE courseid=?");
            statement.setInt(1, courseId);
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
}
