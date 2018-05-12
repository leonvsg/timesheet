package ru.leonvsg.education.timesheet.services;

import ru.leonvsg.education.timesheet.connections.ConnectionManager;
import ru.leonvsg.education.timesheet.connections.JDBCConnectionManager;
import ru.leonvsg.education.timesheet.dao.basic.GroupDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCGroupDAO;
import ru.leonvsg.education.timesheet.entities.Group;
import ru.leonvsg.education.timesheet.entities.Lesson;

import java.util.List;

public class LessonService {

    ConnectionManager connectionManager = JDBCConnectionManager.getInstance();
    GroupDAO groupDAO = new JDBCGroupDAO(connectionManager);

    public List<Lesson> getLessons(Group group){
        return groupDAO.getLessons(group);
    }

    public List<Lesson> getLessons(Integer groupId){
        return groupDAO.getLessons(groupId);
    }
}
