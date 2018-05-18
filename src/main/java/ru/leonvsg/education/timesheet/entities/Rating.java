package ru.leonvsg.education.timesheet.entities;

public class Rating implements Entity {

    private Integer id;
    private Integer userId;
    private User user;
    private Integer lessonId;
    private Lesson lesson;
    private Integer value;
    private String description;

    public Rating(int id, int userId, int lessonId, int value, String description) {
        this.id = id;
        this.userId = userId;
        this.lessonId = lessonId;
        this.value = value;
        this.description = description;
    }

    public Rating(int id, User user, Lesson lesson, int value, String description) {
        this.id = id;
        this.user = user;
        this.userId = user.getId();
        this.lesson = lesson;
        this.lessonId = lesson.getId();
        this.value = value;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
