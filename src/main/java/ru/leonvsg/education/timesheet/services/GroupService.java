package ru.leonvsg.education.timesheet.services;

import ru.leonvsg.education.timesheet.connections.ConnectionManager;
import ru.leonvsg.education.timesheet.connections.JDBCConnectionManager;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCUserDAO;
import ru.leonvsg.education.timesheet.entities.*;
import java.util.Map;

public class GroupService {

    private ConnectionManager connectionManager = JDBCConnectionManager.getInstance();

    public Map<Group, Course> getGroups(User user){
        if (user == null) return null;
        return new JDBCUserDAO(connectionManager).getCourses(user);
    }

    public Map<Group, Course> getGroups(){
        return new JDBCUserDAO(connectionManager).getGroups();
    }
}
