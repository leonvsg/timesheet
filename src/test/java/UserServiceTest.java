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
    private String nullLogin = null;
    private String[] validLogins = {"test@test.test", "test@test.test.test"};
    private String[] invalidLogins = {"test", "test@test", "test@test.testtest"};

    @Before
    public void before() throws Exception {
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
        assertTrue(userService.isBusyLogin(nullLogin));
    }

    @Test
    public void isValidLoginTest(){
        for (String login: validLogins) {
            assertTrue(userService.isValidLogin(login));
        }
        for (String login: invalidLogins) {
            assertFalse(userService.isValidLogin(login));
        }
    }
}
