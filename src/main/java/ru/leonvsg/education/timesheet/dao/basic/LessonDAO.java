package ru.leonvsg.education.timesheet.dao.basic;

import ru.leonvsg.education.timesheet.entities.*;
import java.util.List;

public interface LessonDAO extends DAO<Lesson, Integer> {

    Lesson getLessonByRating(Rating rating);

    Lesson getLessonByRating(Integer ratingId);

    List<Lesson> getLessonsByGroup(Group group);

    List<Lesson> getLessonsByGroup(Integer groupId);
}
