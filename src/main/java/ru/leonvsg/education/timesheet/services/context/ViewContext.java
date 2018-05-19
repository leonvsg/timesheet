package ru.leonvsg.education.timesheet.services.context;

import ru.leonvsg.education.timesheet.entities.*;
import java.util.List;

public class ViewContext {

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

    public ViewContext(String errorMessage, User user, Rating rating, Lesson lesson,
                       Group group, Course course, List<User> users, List<Rating> ratings,
                       List<Lesson> lessons, List<Group> groups, List<Course> courses) {
        this.errorMessage = errorMessage;
        this.user = user;
        this.rating = rating;
        this.lesson = lesson;
        this.group = group;
        this.course = course;
        this.users = users;
        this.ratings = ratings;
        this.lessons = lessons;
        this.groups = groups;
        this.courses = courses;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public User getUser() {
        return user;
    }

    public Rating getRating() {
        return rating;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public Group getGroup() {
        return group;
    }

    public Course getCourse() {
        return course;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public List<Course> getCourses() {
        return courses;
    }
}
