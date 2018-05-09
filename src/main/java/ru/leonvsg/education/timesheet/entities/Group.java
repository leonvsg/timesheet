package ru.leonvsg.education.timesheet.entities;

public class Group {

    private Integer id;
    private String name;
    private Integer courseId;
    private Course course;
    private String startDate;
    private String expDate;
    private String description;

    public Group(Integer id, String name, Integer courseId, String startDate, String expDate, String description) {
        this.id = id;
        this.name = name;
        this.courseId = courseId;
        this.startDate = startDate;
        this.expDate = expDate;
        this.description = description;
    }

    public Group(Integer id, String name, Course course, String startDate, String expDate, String description) {
        this.id = id;
        this.name = name;
        this.courseId = course.getId();
        this.course = course;
        this.startDate = startDate;
        this.expDate = expDate;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
