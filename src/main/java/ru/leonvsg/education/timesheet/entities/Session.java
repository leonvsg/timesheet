package ru.leonvsg.education.timesheet.entities;

public class Session implements Entity {

    private Integer userId;
    private User user;
    private String token;

    public Session(User user, String token) {
        this.user = user;
        this.token = token;
        this.userId = user.getId();
    }

    public Session(int userId, String token) {
        this.userId = userId;
        this.token = token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
