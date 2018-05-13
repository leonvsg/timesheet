package ru.leonvsg.education.timesheet.dao.basic;

import ru.leonvsg.education.timesheet.entities.*;
import java.util.List;

public interface UserDAO extends DAO<User, Integer> {
    User read(String login);

    List<Rating> getRating(User user);

    List<Rating> getRating(Integer userId);

    List<Group> getGroups(User user);

    List<Group> getGroups(Integer userId);

    List<Session> getSessions(User user);

    List<Session> getSessions(Integer userId);

    List<Course> getCourses(User user);

    List<Course> getCourses(Integer userId);
}
