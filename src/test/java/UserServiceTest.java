import org.junit.Before;
import org.junit.Test;
import ru.leonvsg.education.timesheet.dao.basic.DAOFactory;
import ru.leonvsg.education.timesheet.dao.basic.GroupDAO;
import ru.leonvsg.education.timesheet.dao.basic.SessionDAO;
import ru.leonvsg.education.timesheet.dao.basic.UserDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCDAOFactory;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCGroupDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCSessionDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCUserDAO;
import ru.leonvsg.education.timesheet.entities.Group;
import ru.leonvsg.education.timesheet.entities.Session;
import ru.leonvsg.education.timesheet.entities.User;
import ru.leonvsg.education.timesheet.services.UserService;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserService userService;
    private UserDAO userDAO;
    private SessionDAO sessionDAO;
    private GroupDAO groupDAO;
    private User user;
    private DAOFactory daoFactory;
    private String busyLogin = "BusyLogin";
    private String freeLogin = "FreeLogin";

    @Before
    public void before() {
        user = mock(User.class);
        userDAO = mock(JDBCUserDAO.class);
        sessionDAO = mock(JDBCSessionDAO.class);
        groupDAO = mock(JDBCGroupDAO.class);
        daoFactory = mock(JDBCDAOFactory.class);
        when(daoFactory.getDAO(User.class)).thenReturn(userDAO);
        when(daoFactory.getDAO(Group.class)).thenReturn(groupDAO);
        when(daoFactory.getDAO(Session.class)).thenReturn(sessionDAO);
        when(userDAO.read(busyLogin)).thenReturn(user);
        when(userDAO.read(freeLogin)).thenReturn(null);
        userService = new UserService(daoFactory);
    }

    @Test
    public void isBusyLoginTest(){
        assertTrue(userService.isBusyLogin(busyLogin));
        assertFalse(userService.isBusyLogin(freeLogin));
        assertTrue(userService.isBusyLogin(null));
    }

    @Test
    public void isValidLoginTest(){
        String[] validLogins = {"test@test.test", "test@test.test.test"};
        for (String login: validLogins) assertTrue(userService.isValidLogin(login));
        String[] invalidLogins = {"test", "test@test", "test@test.testtest"};
        for (String login: invalidLogins) assertFalse(userService.isValidLogin(login));
    }

    @Test
    public void isValidRoleTest(){
        String[] validRoles = {"ADMIN", "student", "LeCtOr"};
        for (String role : validRoles) assertTrue(userService.isValidRole(role));
        String invalidRole = "role";
        assertFalse(userService.isValidRole(invalidRole));
    }

    @Test
    public void isValidPasswordTest(){
        String[] invalidPasswords = {"pass", null};
        for (String password: invalidPasswords) assertFalse(userService.isValidPassword(password));
        String validPassword = "password";
        assertTrue(userService.isValidPassword(validPassword));
    }

    @Test
    public void getAllUsersTest(){
        userService.getAllUsers();
        verify(userDAO).getAll();
    }

    @Test
    public void getUserTest(){
        Integer id = 0;
        userService.getUser(id);
        verify(userDAO).read(id);
    }
}
