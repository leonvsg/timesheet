import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.leonvsg.education.timesheet.connections.ConnectionManager;
import ru.leonvsg.education.timesheet.connections.JDBCConnectionManager;
import ru.leonvsg.education.timesheet.dao.basic.SessionDAO;
import ru.leonvsg.education.timesheet.dao.basic.UserDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCSessionDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCUserDAO;
import ru.leonvsg.education.timesheet.entities.User;
import ru.leonvsg.education.timesheet.services.UserService;

import static org.powermock.api.mockito.PowerMockito.*;
import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ConnectionManager.class, JDBCConnectionManager.class, JDBCUserDAO.class, UserDAO.class, JDBCSessionDAO.class})
public class UserServiceTest {

    UserService userService;
    ConnectionManager connectionManager;
    JDBCUserDAO userDAO;
    SessionDAO sessionDAO;
    User user;
    String busyLogin = "BusyLogin";
    String freeLogin = "FreeLogin";
    String nullLogin = null;
    String[] validLogin = {"test@test.test", "test@test.test.test"};
    String[] invalidLogin = {"test", "test@test", "test@test.testtest"};

    @Before
    public void before() throws Exception {
        connectionManager = mock(ConnectionManager.class);
        mockStatic(JDBCConnectionManager.class);
        when(JDBCConnectionManager.getInstance()).thenReturn(connectionManager);
        user = mock(User.class);
        userDAO = mock(JDBCUserDAO.class);
        when(userDAO.read("BusyLogin")).thenReturn(user);
        when(userDAO.read("FreeLogin")).thenReturn(null);
        whenNew(UserDAO.class).withAnyArguments().thenReturn(userDAO);
        userService = new UserService();
    }

    @Test
    public void isBusyLoginTest(){
        verifyStatic();
        assertTrue(userService.isBusyLogin("FreeLogin"));
        verifyStatic();
        assertFalse(userService.isBusyLogin("FreeLogin"));
        verifyStatic();
        assertFalse(userService.isBusyLogin(null));
    }

}
