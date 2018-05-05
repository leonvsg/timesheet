package ru.leonvsg.education.timesheet.entities;

public class User {

    private Integer id;
    private String login;
    private String password;
    private String regDate;
    private String role;
    private String name;
    private String middleName;
    private String surname;

    public User(Integer id, String login, String password, String regDate, String role, String name, String middleName, String surname) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.regDate = regDate;
        this.role = role;
        this.name = name;
        this.middleName = middleName;
        this.surname = surname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

}
