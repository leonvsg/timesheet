package ru.education.timesheet.DAO.JDBCImpl;

import ru.education.timesheet.ConnectionManager.ConnectionManager;
import ru.education.timesheet.DAO.Basic.LessonDAO;
import ru.education.timesheet.Entities.Group;
import ru.education.timesheet.Entities.Lesson;
import ru.education.timesheet.Entities.Rating;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCLessonDAO extends JDBCDAO<Lesson, Integer> implements LessonDAO {

    public JDBCLessonDAO(ConnectionManager connectionManager) {
        super(connectionManager);
    }

    @Override
    protected PreparedStatement getPreparedStatementForInsert(Connection connection, Lesson lesson) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO timesheet.lessons (groupid, description, date) " +
                            "VALUES (?, ?, ?)"
            );
            statement.setInt(1, lesson.getGroupId());
            statement.setString(2, lesson.getDescription());
            statement.setString(3, lesson.getDate());
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
                        "SELECT * FROM timesheet.lessons"
                );
            else {
                statement = connection.prepareStatement(
                        "SELECT * FROM timesheet.lessons WHERE lessonid=?"
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
    protected PreparedStatement getPreparedStatementForUpdate(Connection connection, Lesson lesson) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "UPDATE timesheet.lessons SET groupid=?, description=?, date=? WHERE lessonid=?"
            );
            statement.setInt(1, lesson.getGroupId());
            statement.setString(2, lesson.getDescription());
            statement.setString(3, lesson.getDate());
            statement.setInt(4, lesson.getId());
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
                    "DELETE FROM timesheet.lessons WHERE lessonid=?"
            );
            statement.setInt(1, key);
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            throw new SQLException("Something wrong with PreparedStatement");
        }
        return statement;
    }

    @Override
    protected List<Lesson> parseResultSet(ResultSet resultSet) throws SQLException {
        List<Lesson> lessons = new ArrayList<>();
        try {
            while (resultSet.next()) {
                lessons.add(new Lesson(
                        resultSet.getInt("lessonid"),
                        resultSet.getInt("groupid"),
                        resultSet.getString("description"),
                        resultSet.getString("date")
                ));
            }
        } catch (Exception e) {
            throw new SQLException("Incorrect ResultSet");
        }
        return lessons;
    }

    @Override
    public Group getGroup(Lesson lesson) {
        return getGroup(lesson.getId());
    }

    @Override
    public Group getGroup(Integer lessonId) {
        Group group = null;
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT g.groupid, g.groupname, g.courseid, g.startdate, g.expdate, g.description " +
                            "FROM timesheet.lessons AS l " +
                            "LEFT JOIN timesheet.groups AS g ON l.groupid = g.groupid" +
                            "WHERE l.lessonid=?");
            statement.setInt(1, lessonId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                group = new Group(
                        resultSet.getInt("groupid"),
                        resultSet.getString("groupname"),
                        resultSet.getInt("courseid"),
                        resultSet.getString("startdate"),
                        resultSet.getString("expdate"),
                        resultSet.getString("description")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return group;
    }

    @Override
    public List<Rating> getRatings(Lesson lesson) {
        return getRatings(lesson.getId());
    }

    @Override
    public List<Rating> getRatings(Integer lessonId) {
        List<Rating> ratings = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM timesheet.rating WHERE lessonid=?");
            statement.setInt(1, lessonId);
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
}
