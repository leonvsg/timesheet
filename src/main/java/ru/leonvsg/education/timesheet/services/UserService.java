package ru.leonvsg.education.timesheet.services;

import com.google.common.hash.Hashing;
import ru.leonvsg.education.timesheet.Settings;
import ru.leonvsg.education.timesheet.connections.ConnectionManager;
import ru.leonvsg.education.timesheet.connections.JDBCConnectionManager;
import ru.leonvsg.education.timesheet.dao.basic.SessionDAO;
import ru.leonvsg.education.timesheet.dao.basic.UserDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCSessionDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCUserDAO;
import ru.leonvsg.education.timesheet.entities.Session;
import ru.leonvsg.education.timesheet.entities.User;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

public class UserService {

    private ConnectionManager connectionManager = JDBCConnectionManager.getInstance();

    public boolean register(String login, String password, String role, String name, String middlename, String surname){
        final String hashedPassword = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
        return new JDBCUserDAO(connectionManager)
                .create(new User(null, login, hashedPassword, new Date().toString(), role, name, middlename, surname));
    }

    public String authenticate(String login, String password){
        UserDAO userDAO = new JDBCUserDAO(connectionManager);
        final String hashedPassword = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
        User user = userDAO.read(login);
        if (user == null || !hashedPassword.equals(user.getPassword())) return null;
        String token = UUID.randomUUID().toString();
        SessionDAO sessionDAO = new JDBCSessionDAO(connectionManager);
        if (sessionDAO.create(new Session(user, token))) return token;
        return null;
    }

    public boolean authenticate(String token){
        return true;
    }

    public boolean delete(User user){
        return true;
    }

    public boolean delete(Integer userId){
        return true;
    }

    public boolean verify (User user) throws VerifyUserException {
        return true;
    }

    public class VerifyUserException extends RuntimeException {

        public VerifyUserException(String message) {
            super(message);
        }
    }
}
