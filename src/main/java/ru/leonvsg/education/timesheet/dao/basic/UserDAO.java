package ru.leonvsg.education.timesheet.dao.basic;

import ru.leonvsg.education.timesheet.entities.*;
import java.util.List;

public interface UserDAO extends DAO<User, Integer> {

    User getUserByLogin(String login);

    User getUserByToken(String token);

    User getUserByRating(Rating rating);

    User getUserByRating(Integer ratingId);

    List<User> getUsersByGroup(Group group);

    List<User> getUsersByGroup(Integer groupId);
}
