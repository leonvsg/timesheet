package ru.education.timesheet.DAO.Basic;

import ru.education.timesheet.Entities.Course;
import ru.education.timesheet.Entities.Group;

import java.util.List;

public interface CourseDAO extends DAO<Course, Integer> {

    List<Group> getGroups(Course course);

    List<Group> getGroups(Integer courseId);
}
