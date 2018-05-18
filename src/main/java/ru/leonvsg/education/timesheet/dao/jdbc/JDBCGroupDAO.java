package ru.leonvsg.education.timesheet.dao.jdbc;


import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.connections.ConnectionManager;
import ru.leonvsg.education.timesheet.dao.basic.GroupDAO;
import ru.leonvsg.education.timesheet.entities.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCGroupDAO extends JDBCDAO<Group, Integer> implements GroupDAO {

    private static final Logger LOGGER = Logger.getLogger(JDBCGroupDAO.class);

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
                        "SELECT * FROM timesheet.groups"
                );
            else {
                statement = connection.prepareStatement(
                        "SELECT * FROM timesheet.groups WHERE groupid=?"
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
                    "DELETE FROM timesheet.groups WHERE groupid=?"
            );
            statement.setInt(1, key);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new SQLException(INVALID_PREPARED_STATEMENT_MESSAGE);
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
    public Group getGroupByLesson(Lesson lesson) {
        return getGroupByLesson(lesson.getId());
    }

    @Override
    public Group getGroupByLesson(Integer lessonId) {
        Group group = null;
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT g.groupid, g.groupname, g.courseid, g.startdate, g.expdate, g.description " +
                            "FROM timesheet.lessons AS l " +
                            "LEFT JOIN timesheet.groups AS g ON l.groupid = g.groupid" +
                            "WHERE l.lessonid=?");
            statement.setInt(1, lessonId);
            group = execute(statement);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return group;
    }

    @Override
    public List<Group> getGroupsByCourse(Course course) {
        return getGroupsByCourse(course.getId());
    }

    @Override
    public List<Group> getGroupsByCourse(Integer courseId) {
        List<Group> groups = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM timesheet.groups WHERE courseid=?");
            statement.setInt(1, courseId);
            ResultSet resultSet = statement.executeQuery();
            groups = executeMulti(statement);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return groups;
    }

    @Override
    public List<Group> getGroupsByUser(User user) {
        return getGroupsByUser(user.getId());
    }

    @Override
    public List<Group> getGroupsByUser(Integer userId) {
        List<Group> groups = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT g.groupid, g.groupname, g.courseid, g.startdate, g.expdate, g.description " +
                            "FROM timesheet.members AS m " +
                            "LEFT JOIN timesheet.groups AS g ON m.groupid = g.groupid " +
                            "WHERE m.userid=?");
            statement.setInt(1, userId);
            groups = executeMulti(statement);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return groups;
    }

    @Override
    public List<Group> getGroupsWithCourses() {
        List<Group> groups = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT g.groupid, g.groupname, g.courseid, g.startdate, g.expdate, g.description, " +
                            "c.courseid, c.coursename, c.description AS coursedescription, c.duration " +
                            "FROM timesheet.members AS m " +
                            "  LEFT JOIN timesheet.groups AS g ON m.groupid = g.groupid " +
                            "  LEFT JOIN timesheet.courses AS c ON g.courseid = c.courseid "
            );
            ResultSet resultSet = statement.executeQuery();
            groups = parseResultSetWithCourses(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return groups;
    }

    @Override
    public List<Group> getGroupsWithCourses(User user) {
        List<Group> groups = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT g.groupid, g.groupname, g.courseid, g.startdate, g.expdate, g.description, " +
                            "c.courseid, c.coursename, c.description AS coursedescription, c.duration " +
                            "FROM timesheet.members AS m " +
                            "  LEFT JOIN timesheet.groups AS g ON m.groupid = g.groupid " +
                            "  LEFT JOIN timesheet.courses AS c ON g.courseid = c.courseid " +
                            "WHERE m.userid=?"
            );
            statement.setInt(1,user.getId());
            ResultSet resultSet = statement.executeQuery();
            groups = parseResultSetWithCourses(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return groups;
    }

    private List<Group> parseResultSetWithCourses(ResultSet resultSet) throws SQLException {
        List<Group> groups = new ArrayList<>();
        try {
            while (resultSet.next()) {
                groups.add(
                        new Group(
                                resultSet.getInt("groupid"),
                                resultSet.getString("groupname"),
                                new Course(
                                        resultSet.getInt("courseid"),
                                        resultSet.getString("coursename"),
                                        resultSet.getString("coursedescription"),
                                        resultSet.getInt("duration")
                                ),
                                resultSet.getString("startdate"),
                                resultSet.getString("expdate"),
                                resultSet.getString("description")
                        )
                );
            }
        } catch (Exception e) {
            throw new SQLException("Incorrect ResultSet");
        }
        return groups;
    }
}
