package ru.leonvsg.education.timesheet.services;

import ru.leonvsg.education.timesheet.connections.ConnectionManager;
import ru.leonvsg.education.timesheet.connections.JDBCConnectionManager;
import ru.leonvsg.education.timesheet.dao.basic.GroupDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCGroupDAO;
import ru.leonvsg.education.timesheet.entities.Group;
import ru.leonvsg.education.timesheet.entities.Rating;

import java.util.List;

public class RatingService {

    ConnectionManager connectionManager = JDBCConnectionManager.getInstance();
    GroupDAO groupDAO = new JDBCGroupDAO(connectionManager);

    public List<Rating> getRatingsByGroup(Group group){
        return getRatingsByGroup(group.getId());
    }

    public List<Rating> getRatingsByGroup(Integer groupId){
        return groupDAO.getRating(groupId);
    }
}
