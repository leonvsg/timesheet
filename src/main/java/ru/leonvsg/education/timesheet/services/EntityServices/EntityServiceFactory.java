package ru.leonvsg.education.timesheet.services.EntityServices;

import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.dao.basic.DAOFactory;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCDAOFactory;
import ru.leonvsg.education.timesheet.entities.*;
import java.util.HashMap;
import java.util.Map;

public class EntityServiceFactory implements ServiceFactory{

    private static final Logger LOGGER = Logger.getLogger(EntityServiceFactory.class);
    private static ServiceFactory serviceFactory;
    private final Map<Class, ServiceCreator> entities;
    private DAOFactory daoFactory;

    private EntityServiceFactory(){
        daoFactory = JDBCDAOFactory.getDAOFactory();
        entities = new HashMap<>();
        entities.put(Course.class, ()->new CourseService(daoFactory));
        entities.put(Group.class, ()->new GroupService(daoFactory));
        entities.put(Lesson.class, ()->new LessonService(daoFactory));
        entities.put(Rating.class, ()->new RatingService(daoFactory));
        entities.put(Session.class, ()->new SessionService(daoFactory));
        entities.put(User.class, ()->new UserService(daoFactory));
    }

    public static ServiceFactory getInstance(){
        if (serviceFactory == null){
            serviceFactory = new EntityServiceFactory();
        }
        return serviceFactory;
    }

    @Override
    public Service getService(Class clazz) throws ClassCastException, RuntimeException {
        ServiceCreator creator = entities.get(clazz);
        if (creator == null) throw new RuntimeException("Class is not entity");
        return creator.create();
    }

    private interface ServiceCreator{
        Service create();
    }
}
