package ru.leonvsg.education.timesheet.services.EntityServices;

import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.dao.basic.DAOFactory;
import ru.leonvsg.education.timesheet.dao.basic.LessonDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCDAOFactory;
import ru.leonvsg.education.timesheet.entities.Group;
import ru.leonvsg.education.timesheet.entities.Lesson;

import java.util.List;

public class LessonService {

    private static final Logger LOGGER = Logger.getLogger(LessonService.class);
    private LessonDAO lessonDAO;

    public LessonService() {
        this(JDBCDAOFactory.getDAOFactory());
    }

    public LessonService(DAOFactory daoFactory) {
        lessonDAO = daoFactory.getDAO(Lesson.class);
    }

    public List<Lesson> getLessons(Group group){
        return lessonDAO.getLessonsByGroup(group);
    }

    public List<Lesson> getLessons(Integer groupId){
        return lessonDAO.getLessonsByGroup(groupId);
    }
}
