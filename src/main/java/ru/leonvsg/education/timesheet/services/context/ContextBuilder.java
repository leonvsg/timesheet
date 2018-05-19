package ru.leonvsg.education.timesheet.services.context;

import ru.leonvsg.education.timesheet.entities.*;
import java.util.List;

public interface ContextBuilder {

    void setErrorMessage(String errorMessage);
    void setUser(User user);
    void setRating(Rating rating);
    void setLesson(Lesson lesson);
    void setGroup(Group group);
    void setCourse(Course course);
    void setUsers(List<User> users);
    void setRating(List<Rating> rating);
    void setLessons(List<Lesson> lessons);
    void setGroups(List<Group> groups);
    void setCourses(List<Course> courses);
    ViewContext gotResult();
}
