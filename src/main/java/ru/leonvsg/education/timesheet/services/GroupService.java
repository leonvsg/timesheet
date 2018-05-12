package ru.leonvsg.education.timesheet.services;

import ru.leonvsg.education.timesheet.connections.ConnectionManager;
import ru.leonvsg.education.timesheet.connections.JDBCConnectionManager;
import ru.leonvsg.education.timesheet.dao.basic.CourseDAO;
import ru.leonvsg.education.timesheet.dao.basic.GroupDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCCourseDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCGroupDAO;
import ru.leonvsg.education.timesheet.entities.Course;
import ru.leonvsg.education.timesheet.entities.Group;
import ru.leonvsg.education.timesheet.entities.User;

import java.util.List;

public class GroupService {

    private ConnectionManager connectionManager = JDBCConnectionManager.getInstance();
    private CourseDAO courseDAO = new JDBCCourseDAO(connectionManager);
    private GroupDAO groupDAO = new JDBCGroupDAO(connectionManager);

    public List<Group> getGroups(){
        return groupDAO.getGroupsWithCourses();
    }

    public List<Group> getGroups(Course course){
        return courseDAO.getGroups(course);
    }

    public List<Group> getGroups(User user){
        return groupDAO.getGroupsWithCourses(user);
    }
}
