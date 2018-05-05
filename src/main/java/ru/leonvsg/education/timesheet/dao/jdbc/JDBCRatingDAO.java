package ru.leonvsg.education.timesheet.dao.jdbc;

import ru.leonvsg.education.timesheet.connections.ConnectionManager;
import ru.leonvsg.education.timesheet.dao.basic.RatingDAO;
import ru.leonvsg.education.timesheet.entities.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCRatingDAO extends JDBCDAO<Rating, Integer> implements RatingDAO {

    public JDBCRatingDAO(ConnectionManager connectionManager) {
        super(connectionManager);
    }

    @Override
    protected PreparedStatement getPreparedStatementForInsert(Connection connection, Rating rating) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO timesheet.rating (userid, lessonid, value, description) " +
                            "VALUES (?, ?, ?, ?)"
            );
            statement.setInt(1, rating.getUserId());
            statement.setInt(2, rating.getLessonId());
            statement.setInt(3, rating.getValue());
            statement.setString(4, rating.getDescription());
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
                        "SELECT * FROM timesheet.rating"
                );
            else {
                statement = connection.prepareStatement(
                        "SELECT * FROM timesheet.rating WHERE id=?"
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
    protected PreparedStatement getPreparedStatementForUpdate(Connection connection, Rating rating) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "UPDATE timesheet.rating SET userid=?, lessonid=?, value=?, description=? WHERE id=?"
            );
            statement.setInt(1, rating.getUserId());
            statement.setInt(2, rating.getLessonId());
            statement.setInt(3, rating.getValue());
            statement.setString(4, rating.getDescription());
            statement.setInt(5, rating.getId());
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
                    "DELETE FROM timesheet.rating WHERE id=?"
            );
            statement.setInt(1, key);
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            throw new SQLException("Something wrong with PreparedStatement");
        }
        return statement;
    }

    @Override
    protected List<Rating> parseResultSet(ResultSet resultSet) throws SQLException {
        List<Rating> ratings = new ArrayList<>();
        try {
            while (resultSet.next()) {
                ratings.add(new Rating(
                        resultSet.getInt("id"),
                        resultSet.getInt("userid"),
                        resultSet.getInt("lessonid"),
                        resultSet.getInt("value"),
                        resultSet.getString("description")
                ));
            }
        } catch (Exception e) {
            throw new SQLException("Incorrect ResultSet");
        }
        return ratings;
    }

    @Override
    public User getUser(Rating rating) {
        return getUser(rating.getId());
    }

    @Override
    public User getUser(Integer ratingId) {
        User user = null;
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT u.userid, u.login, u.password, u.regdate, u.role, u.name, u.middlename, u.surname " +
                            "FROM timesheet.rating AS r " +
                            "LEFT JOIN timesheet.users AS u ON r.userid = u.userid" +
                            "WHERE r.id=?");
            statement.setInt(1, ratingId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getInt("userid"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("regdate"),
                        resultSet.getString("role"),
                        resultSet.getString("name"),
                        resultSet.getString("middlename"),
                        resultSet.getString("surname")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public Lesson getLesson(Rating rating) {
        return getLesson(rating.getId());
    }

    @Override
    public Lesson getLesson(Integer ratingId) {
        Lesson lesson = null;
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT l.lessonid, l.groupid, l.description, l.date " +
                            "FROM timesheet.rating AS r " +
                            "LEFT JOIN timesheet.lessons AS l ON r.lessonid=l.lessonid" +
                            "WHERE r.id=?");
            statement.setInt(1, ratingId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                lesson = new Lesson(
                        resultSet.getInt("lessonid"),
                        resultSet.getInt("groupid"),
                        resultSet.getString("description"),
                        resultSet.getString("date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lesson;
    }
}
