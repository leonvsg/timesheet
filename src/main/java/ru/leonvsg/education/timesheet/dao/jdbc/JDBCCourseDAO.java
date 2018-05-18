package ru.leonvsg.education.timesheet.dao.jdbc;

import org.apache.log4j.Logger;
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

    private static final Logger LOGGER = Logger.getLogger(JDBCCourseDAO.class);

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
                        "SELECT * FROM timesheet.courses"
                );
            else {
                statement = connection.prepareStatement(
                        "SELECT * FROM timesheet.courses WHERE courseid=?"
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
                    "DELETE FROM timesheet.courses WHERE courseid=?"
            );
            statement.setInt(1, key);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new SQLException(INVALID_PREPARED_STATEMENT_MESSAGE);
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
    public Course getCourseByGroup(Group group) {
        return getCourseByGroup(group.getId());
    }

    @Override
    public Course getCourseByGroup(Integer groupId) {
        Course course = null;
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT c.courseid, c.coursename, c.description, c.duration " +
                            "FROM timesheet.groups AS g " +
                            "LEFT JOIN timesheet.courses AS c ON g.courseid = c.courseid" +
                            "WHERE g.groupid=?");
            statement.setInt(1, groupId);
            course = executor(statement);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return course;
    }

    @Override
    public List<Course> getCoursesByUser(User user){
        return getCoursesByUser(user.getId());
    }

    @Override
    public List<Course> getCoursesByUser(Integer userId){
        List<Course> courses = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT c.courseid, c.coursename, c.description, c.duration " +
                            "FROM timesheet.members AS m " +
                            "  LEFT JOIN timesheet.groups AS g ON m.groupid = g.groupid " +
                            "  LEFT JOIN timesheet.courses AS c ON g.courseid = c.courseid " +
                            "WHERE m.userid=? GROUP BY c.courseid");
            statement.setInt(1, userId);
            courses = multiExecutor(statement);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return courses;
    }

}
