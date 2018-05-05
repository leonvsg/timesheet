package ru.leonvsg.education.timesheet.dao.basic;

import ru.leonvsg.education.timesheet.entities.*;
import java.util.List;

public interface GroupDAO extends DAO<Group, Integer> {

    List<User> getUsers(Group group);

    List<User> getUsers(Integer groupId);

    List<Lesson> getLessons(Group group);

    List<Lesson> getLessons(Integer groupId);

    Course getCourse(Group group);

    Course getCourse(Integer groupId);
}
