package ru.leonvsg.education.timesheet.services;

import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.dao.basic.DAOFactory;
import ru.leonvsg.education.timesheet.dao.basic.GroupDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCDAOFactory;
import ru.leonvsg.education.timesheet.entities.Group;
import ru.leonvsg.education.timesheet.entities.Rating;

import java.util.List;

public class RatingService {

    private static final Logger LOGGER = Logger.getLogger(LessonService.class);
    private GroupDAO groupDAO;

    public RatingService() {
        this(JDBCDAOFactory.getDAOFactory());
    }

    public RatingService(DAOFactory factory) {
        groupDAO = factory.getDAO(Group.class);
    }

    public List<Rating> getRatingsByGroup(Group group){
        return groupDAO.getRating(group);
    }

    public List<Rating> getRatingsByGroup(Integer groupId){
        return groupDAO.getRating(groupId);
    }
}
