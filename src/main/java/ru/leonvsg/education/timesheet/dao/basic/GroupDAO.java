package ru.leonvsg.education.timesheet.dao.basic;

import ru.leonvsg.education.timesheet.entities.*;
import java.util.List;
import java.util.Map;

public interface GroupDAO extends DAO<Group, Integer> {

    List<User> getUsers(Group group);

    List<User> getUsers(Integer groupId);

    List<Lesson> getLessons(Group group);

    List<Lesson> getLessons(Integer groupId);

    Course getCourse(Group group);

    Course getCourse(Integer groupId);

    List<Group> getGroupsWithCourses();

    List<Group> getGroupsWithCourses(User user);

    List<Rating> getRating(Group group);

    List<Rating> getRating(Integer groupId);
}
