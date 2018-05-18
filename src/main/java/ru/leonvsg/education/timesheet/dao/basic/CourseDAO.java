package ru.leonvsg.education.timesheet.dao.basic;

import ru.leonvsg.education.timesheet.entities.*;
import java.util.List;

public interface CourseDAO extends DAO<Course, Integer> {

    Course getCourseByGroup(Group group);

    Course getCourseByGroup(Integer groupId);

    List<Course> getCoursesByUser(User user);

    List<Course> getCoursesByUser(Integer userId);
}
