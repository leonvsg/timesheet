package ru.leonvsg.education.timesheet.services.context;

import ru.leonvsg.education.timesheet.entities.*;
import java.util.List;

public class ViewContextBuilder implements ContextBuilder {

    private String errorMessage;
    private User user;
    private Rating rating;
    private Lesson lesson;
    private Group group;
    private Course course;
    private List<User> users;
    private List<Rating> ratings;
    private List<Lesson> lessons;
    private List<Group> groups;
    private List<Course> courses;

    @Override
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @Override
    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    @Override
    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public void setRating(List<Rating> ratings) {
        this.ratings = ratings;
    }

    @Override
    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    @Override
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public ViewContext gotResult(){
        return new ViewContext(errorMessage, user, rating, lesson, group, course, users, ratings, lessons, groups, courses);
    }
}
