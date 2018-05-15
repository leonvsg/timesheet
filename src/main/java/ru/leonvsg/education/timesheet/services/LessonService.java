package ru.leonvsg.education.timesheet.services;

import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.dao.basic.DAOFactory;
import ru.leonvsg.education.timesheet.dao.basic.GroupDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCDAOFactory;
import ru.leonvsg.education.timesheet.entities.Group;
import ru.leonvsg.education.timesheet.entities.Lesson;

import java.util.List;

public class LessonService {

    private static final Logger LOGGER = Logger.getLogger(LessonService.class);
    private GroupDAO groupDAO;

    public LessonService() {
        this(JDBCDAOFactory.getDAOFactory());
    }

    public LessonService(DAOFactory daoFactory) {
        groupDAO = daoFactory.getDAO(Group.class);
    }

    public List<Lesson> getLessons(Group group){
        return groupDAO.getLessons(group);
    }

    public List<Lesson> getLessons(Integer groupId){
        return groupDAO.getLessons(groupId);
    }
}
