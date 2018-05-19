package ru.leonvsg.education.timesheet.services.EntityServices;

import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.dao.basic.DAOFactory;
import ru.leonvsg.education.timesheet.dao.basic.RatingDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCDAOFactory;
import ru.leonvsg.education.timesheet.entities.Group;
import ru.leonvsg.education.timesheet.entities.Rating;

import java.util.List;

public class RatingService {

    private static final Logger LOGGER = Logger.getLogger(LessonService.class);
    private RatingDAO ratingDAO;

    public RatingService() {
        this(JDBCDAOFactory.getDAOFactory());
    }

    public RatingService(DAOFactory factory) {
        ratingDAO = factory.getDAO(Rating.class);
    }

    public List<Rating> getRatingsByGroup(Group group){
        return ratingDAO.getRatingByGroup(group);
    }

    public List<Rating> getRatingsByGroup(Integer groupId){
        return ratingDAO.getRatingByGroup(groupId);
    }
}
