package ru.leonvsg.education.timesheet.services.entity;

import com.google.common.hash.Hashing;
import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.Settings;
import ru.leonvsg.education.timesheet.dao.basic.DAOFactory;
import ru.leonvsg.education.timesheet.dao.basic.SessionDAO;
import ru.leonvsg.education.timesheet.dao.basic.UserDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCDAOFactory;
import ru.leonvsg.education.timesheet.entities.Role;
import ru.leonvsg.education.timesheet.entities.Session;
import ru.leonvsg.education.timesheet.entities.User;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService implements Service {

    private static final Logger LOGGER = Logger.getLogger(LessonService.class);
    private UserDAO userDAO;
    private SessionDAO sessionDAO;

    public UserService() {
        this(JDBCDAOFactory.getDAOFactory());
    }

    public UserService(DAOFactory daoFactory) {
        userDAO = daoFactory.getDAO(User.class);
        sessionDAO = daoFactory.getDAO(Session.class);
    }

    public boolean isBusyLogin(String login) {
        return login == null || userDAO.getUserByLogin(login) != null;
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
        return password != null && password.length() >= 5;
    }

    public List<User> getAllUsers(){
        return userDAO.getAll();
    }

    public User getUser(Integer id){
        return userDAO.read(id);
    }

    public boolean register(String login, String password, String role, String name, String middlename, String surname){
        final String hashedPassword = getHashedPassword(password);
        return userDAO.create(
                new User(null, login, hashedPassword, new Date().toString(),
                        role.toUpperCase(), name, middlename, surname));
    }

    public String getHashedPassword(String password) {
        return Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
    }

    public String authenticate(String login, String password){
        if (login == null || password == null) return null;
        final String hashedPassword = getHashedPassword(password);
        User user = userDAO.getUserByLogin(login);
        if (user == null || !hashedPassword.equals(user.getPassword())) return null;
        String token = UUID.randomUUID().toString();
        if (sessionDAO.create(new Session(user, token))) return token;
        return null;
    }

    public User authenticate(String token){
        return userDAO.getUserByToken(token);
    }

    public Role verifyRole(String token) {
        if (token == null) return null;
        User user = userDAO.getUserByToken(token);
        if (user == null) return null;
        return Role.valueOf(user.getRole());
    }

    public boolean updateUser(User user){
        return userDAO.update(user);
    }

    public List<User> getUsersByGroup(Integer groupId){
        return userDAO.getUsersByGroup(groupId);
    }

}
