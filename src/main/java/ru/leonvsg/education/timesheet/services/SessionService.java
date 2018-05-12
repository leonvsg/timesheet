package ru.leonvsg.education.timesheet.services;

import ru.leonvsg.education.timesheet.connections.ConnectionManager;
import ru.leonvsg.education.timesheet.connections.JDBCConnectionManager;
import ru.leonvsg.education.timesheet.dao.basic.SessionDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCSessionDAO;

public class SessionService {

    ConnectionManager connectionManager = JDBCConnectionManager.getInstance();
    SessionDAO sessionDAO = new JDBCSessionDAO(connectionManager);

    public boolean invalidateToken(String token){
        return sessionDAO.delete(token);
    }

}
