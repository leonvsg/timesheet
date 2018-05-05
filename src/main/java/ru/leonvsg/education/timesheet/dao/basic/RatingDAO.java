package ru.leonvsg.education.timesheet.dao.basic;

import ru.leonvsg.education.timesheet.entities.*;

public interface RatingDAO extends DAO<Rating, Integer> {
    User getUser(Rating rating);

    User getUser(Integer ratingId);

    Lesson getLesson(Rating rating);

    Lesson getLesson(Integer ratingId);
}
