package ru.leonvsg.education.timesheet.dao.basic;

import ru.leonvsg.education.timesheet.entities.*;
import java.util.List;

public interface GroupDAO extends DAO<Group, Integer> {

    Group getGroupByLesson(Lesson lesson);

    Group getGroupByLesson(Integer lessonId);

    List<Group> getGroupsByCourse(Course course);

    List<Group> getGroupsByCourse(Integer courseId);

    List<Group> getGroupsByUser(User user);

    List<Group> getGroupsByUser(Integer userId);

    List<Group> getGroupsWithCourses();

    List<Group> getGroupsWithCourses(User user);
}
