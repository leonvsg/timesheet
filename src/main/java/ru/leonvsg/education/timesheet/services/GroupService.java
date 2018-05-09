package ru.leonvsg.education.timesheet.services;

import ru.leonvsg.education.timesheet.connections.ConnectionManager;
import ru.leonvsg.education.timesheet.connections.JDBCConnectionManager;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCCourseDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCGroupDAO;
import ru.leonvsg.education.timesheet.entities.Course;
import ru.leonvsg.education.timesheet.entities.Group;
import java.util.List;

public class GroupService {

    private ConnectionManager connectionManager = JDBCConnectionManager.getInstance();

    public List<Group> getGroups(){
        return new JDBCGroupDAO(connectionManager).getAllGroupsWithCourses();
    }

    public List<Group> getGroups(Course course) {
        return new JDBCCourseDAO(connectionManager).getGroups(course);
    }
}
