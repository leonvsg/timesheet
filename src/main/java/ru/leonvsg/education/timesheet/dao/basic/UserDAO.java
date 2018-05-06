package ru.leonvsg.education.timesheet.dao.basic;

import ru.leonvsg.education.timesheet.entities.*;
import java.util.List;
import java.util.Map;

public interface UserDAO extends DAO<User, Integer> {
    User read(String login);

    List<Rating> getRating(User user);

    List<Rating> getRating(Integer userId);

    List<Group> getGroups(User user);

    List<Group> getGroups(Integer userId);

    Map<Group, Course> getGroups();

    List<Session> getSessions(User user);

    List<Session> getSessions(Integer userId);

    Map<Group, Course> getCourses(User user);

    Map<Group, Course> getCourses(Integer userId);
}
