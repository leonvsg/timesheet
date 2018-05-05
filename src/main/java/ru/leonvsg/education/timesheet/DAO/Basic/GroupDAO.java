package ru.education.timesheet.DAO.Basic;

import ru.education.timesheet.Entities.Course;
import ru.education.timesheet.Entities.Group;
import ru.education.timesheet.Entities.Lesson;
import ru.education.timesheet.Entities.User;

import java.util.List;

public interface GroupDAO extends DAO<Group, Integer> {

    List<User> getUsers(Group group);

    List<User> getUsers(Integer groupId);

    List<Lesson> getLessons(Group group);

    List<Lesson> getLessons(Integer groupId);

    Course getCourse(Group group);

    Course getCourse(Integer groupId);
}
