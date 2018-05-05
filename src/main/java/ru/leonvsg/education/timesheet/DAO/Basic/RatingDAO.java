package ru.education.timesheet.DAO.Basic;

import ru.education.timesheet.Entities.Lesson;
import ru.education.timesheet.Entities.Rating;
import ru.education.timesheet.Entities.User;

public interface RatingDAO extends DAO<Rating, Integer> {
    User getUser(Rating rating);

    User getUser(Integer ratingId);

    Lesson getLesson(Rating rating);

    Lesson getLesson(Integer ratingId);
}
