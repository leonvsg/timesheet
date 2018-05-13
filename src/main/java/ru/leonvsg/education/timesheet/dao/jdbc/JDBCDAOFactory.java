package ru.leonvsg.education.timesheet.dao.jdbc;

import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.connections.ConnectionManager;
import ru.leonvsg.education.timesheet.connections.JDBCConnectionManager;
import ru.leonvsg.education.timesheet.dao.basic.DAO;
import ru.leonvsg.education.timesheet.dao.basic.DAOFactory;
import ru.leonvsg.education.timesheet.entities.*;

import java.util.HashMap;
import java.util.Map;

public class JDBCDAOFactory implements DAOFactory {

    private static final Logger LOGGER = Logger.getLogger(JDBCDAOFactory.class);
    private static DAOFactory daoFactory;
    private final Map<Class, DAOCreator> entities;
    private ConnectionManager connectionManager;

    private JDBCDAOFactory(){
        connectionManager = JDBCConnectionManager.getInstance();
        entities = new HashMap<>();
        entities.put(Course.class, ()->new JDBCCourseDAO(connectionManager));
        entities.put(Group.class, ()->new JDBCGroupDAO(connectionManager));
        entities.put(Lesson.class, ()->new JDBCLessonDAO(connectionManager));
        entities.put(Rating.class, ()->new JDBCRatingDAO(connectionManager));
        entities.put(Session.class, ()->new JDBCSessionDAO(connectionManager));
        entities.put(User.class, ()->new JDBCUserDAO(connectionManager));
    }

    public static DAOFactory getDAOFactory(){
        if (daoFactory == null){
            daoFactory = new JDBCDAOFactory();
        }
        return daoFactory;
    }

    @Override
    public DAO getDAO(Class clazz) throws ClassCastException, RuntimeException {
        DAOCreator daoCreator = entities.get(clazz);
        if (daoCreator == null) throw new RuntimeException("Class is not entity");
        return daoCreator.create();
    }

    private interface DAOCreator{
        DAO create();
    }

}
