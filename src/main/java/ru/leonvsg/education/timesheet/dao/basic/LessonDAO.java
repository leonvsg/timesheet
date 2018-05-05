package ru.leonvsg.education.timesheet.dao.basic;


import ru.leonvsg.education.timesheet.entities.*;
import java.util.List;

public interface LessonDAO extends DAO<Lesson, Integer> {

    Group getGroup(Lesson lesson);

    Group getGroup(Integer lessonId);

    List<Rating> getRatings(Lesson lesson);

    List<Rating> getRatings(Integer lessonId);
}
