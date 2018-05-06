package ru.leonvsg.education.timesheet.services;

import com.google.common.hash.Hashing;
import ru.leonvsg.education.timesheet.entities.*;
import ru.leonvsg.education.timesheet.Settings;
import ru.leonvsg.education.timesheet.connections.*;
import ru.leonvsg.education.timesheet.dao.basic.*;
import ru.leonvsg.education.timesheet.dao.jdbc.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {

    private ConnectionManager connectionManager = JDBCConnectionManager.getInstance();

    public boolean isBusyLogin(String login){
        if (login == null) return true;
        UserDAO userDAO = new JDBCUserDAO(connectionManager);
        return userDAO.read(login) != null;
    }

    public boolean isValidLogin(String login){
        if (login == null) return false;
        Pattern regex = Pattern.compile(Settings.VALID_LOGIN_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = regex.matcher(login);
        return matcher.find();
    }

    public boolean isValidRole(String role){
        try {
            Role.valueOf(role.toUpperCase());
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean isValidPassword(String password){
        if (password == null || password.length() < 5) return false;
        return true;
    }

    public List<User> get(){
        return new JDBCUserDAO(connectionManager).getAll();
    }

    public boolean register(String login, String password, String role, String name, String middlename, String surname){
        final String hashedPassword = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
        return new JDBCUserDAO(connectionManager)
                .create(new User(null, login, hashedPassword, new Date().toString(), role.toUpperCase(), name, middlename, surname));
    }

    public String authenticate(String login, String password){
        if (login == null || password == null) return null;
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

    public User authenticate(String token){
        SessionDAO dao = new JDBCSessionDAO(connectionManager);
        return dao.getUser(token);
    }

    public Role verifyRole(String token) {
        if (token == null) return null;
        SessionDAO dao = new JDBCSessionDAO(connectionManager);
        User user = dao.getUser(token);
        if (user == null) return null;
        return Role.valueOf(user.getRole());
    }

}
