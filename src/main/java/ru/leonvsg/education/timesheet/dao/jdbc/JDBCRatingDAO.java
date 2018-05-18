package ru.leonvsg.education.timesheet.dao.jdbc;

import org.apache.log4j.Logger;
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

    private static final Logger LOGGER = Logger.getLogger(JDBCRatingDAO.class);

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
                        "SELECT * FROM timesheet.rating"
                );
            else {
                statement = connection.prepareStatement(
                        "SELECT * FROM timesheet.rating WHERE id=?"
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
                    "DELETE FROM timesheet.rating WHERE id=?"
            );
            statement.setInt(1, key);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new SQLException(INVALID_PREPARED_STATEMENT_MESSAGE);
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
    public List<Rating> getRatingByUser(User user) {
        return getRatingByUser(user.getId());
    }

    @Override
    public List<Rating> getRatingByUser(Integer userId) {
        List<Rating> ratings = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM timesheet.rating WHERE userid=?");
            statement.setInt(1, userId);
            ratings = executeMulti(statement);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return ratings;
    }

    @Override
    public List<Rating> getRatingByLesson(Lesson lesson) {
        return getRatingByLesson(lesson.getId());
    }

    @Override
    public List<Rating> getRatingByLesson(Integer lessonId) {
        List<Rating> ratings = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM timesheet.rating WHERE lessonid=?");
            statement.setInt(1, lessonId);
           ratings = executeMulti(statement);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return ratings;
    }

    @Override
    public List<Rating> getRatingByGroup(Group group) {
        return getRatingByGroup(group.getId());
    }

    @Override
    public List<Rating> getRatingByGroup(Integer groupId) {
        List<Rating> ratings = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT r.id, r.userid, r.lessonid, r.value, r.description " +
                            "FROM timesheet.rating AS r " +
                            "  LEFT JOIN timesheet.lessons AS l ON r.lessonid = l.lessonid " +
                            "WHERE l.groupid=?"
            );
            statement.setInt(1,groupId);
            ratings = executeMulti(statement);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return ratings;
    }

}
