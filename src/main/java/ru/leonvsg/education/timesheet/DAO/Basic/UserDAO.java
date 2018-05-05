package ru.education.timesheet.DAO.Basic;

import ru.education.timesheet.Entities.Group;
import ru.education.timesheet.Entities.Rating;
import ru.education.timesheet.Entities.Session;
import ru.education.timesheet.Entities.User;

import java.util.List;

public interface UserDAO extends DAO<User, Integer> {
    List<Rating> getRating(User user);

    List<Rating> getRating(Integer userId);

    List<Group> getGroups(User user);

    List<Group> getGroups(Integer userId);

    List<Session> getSessions(User user);

    List<Session> getSessions(Integer userId);
}
