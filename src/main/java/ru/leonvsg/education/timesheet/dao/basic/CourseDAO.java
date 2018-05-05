package ru.leonvsg.education.timesheet.dao.basic;

import ru.leonvsg.education.timesheet.entities.*;
import java.util.List;

public interface CourseDAO extends DAO<Course, Integer> {

    List<Group> getGroups(Course course);

    List<Group> getGroups(Integer courseId);
}
