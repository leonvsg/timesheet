package ru.leonvsg.education.timesheet.entities;

public class Lesson {

    private Integer id;
    private Integer groupId;
    private Group group;
    private String description;
    private String date;

    public Lesson(int id, int groupId, String description, String date) {
        this.id = id;
        this.groupId = groupId;
        this.description = description;
        this.date = date;
    }

    public Lesson(int id, Group group, String description, String date) {
        this.id = id;
        this.group = group;
        this.groupId = group.getId();
        this.description = description;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
