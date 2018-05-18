package ru.leonvsg.education.timesheet.dao.basic;

import ru.leonvsg.education.timesheet.entities.*;
import java.util.List;

public interface RatingDAO extends DAO<Rating, Integer> {

    List<Rating> getRatingByUser(User user);

    List<Rating> getRatingByUser(Integer userId);

    List<Rating> getRatingByLesson(Lesson lesson);

    List<Rating> getRatingByLesson(Integer lessonId);

    List<Rating> getRatingByGroup(Group group);

    List<Rating> getRatingByGroup(Integer groupId);
}
