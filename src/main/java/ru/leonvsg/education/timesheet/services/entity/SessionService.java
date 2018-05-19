package ru.leonvsg.education.timesheet.services.entity;

import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.dao.basic.DAOFactory;
import ru.leonvsg.education.timesheet.dao.basic.SessionDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCDAOFactory;
import ru.leonvsg.education.timesheet.entities.Session;

public class SessionService implements Service {

    private static final Logger LOGGER = Logger.getLogger(SessionService.class);
    private SessionDAO sessionDAO;

    public SessionService(){
        this(JDBCDAOFactory.getDAOFactory());
    }

    public SessionService(DAOFactory daoFactory){
        sessionDAO = daoFactory.getDAO(Session.class);
    }

    public boolean invalidateToken(String token){
        return sessionDAO.delete(token);
    }

}
