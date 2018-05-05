package ru.education.timesheet.DAO.Basic;

import ru.education.timesheet.Entities.Group;
import ru.education.timesheet.Entities.Lesson;
import ru.education.timesheet.Entities.Rating;

import java.util.List;

public interface LessonDAO extends DAO<Lesson, Integer> {

    Group getGroup(Lesson lesson);

    Group getGroup(Integer lessonId);

    List<Rating> getRatings(Lesson lesson);

    List<Rating> getRatings(Integer lessonId);
}
