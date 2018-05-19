import org.junit.Before;
import org.junit.Test;
import ru.leonvsg.education.timesheet.dao.basic.DAOFactory;
import ru.leonvsg.education.timesheet.dao.basic.SessionDAO;
import ru.leonvsg.education.timesheet.dao.basic.UserDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCDAOFactory;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCSessionDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCUserDAO;
import ru.leonvsg.education.timesheet.entities.Role;
import ru.leonvsg.education.timesheet.entities.Session;
import ru.leonvsg.education.timesheet.entities.User;
import ru.leonvsg.education.timesheet.services.EntityServices.UserService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private UserDAO userDAO;
    private SessionDAO sessionDAO;
    private User user;
    private String login = "Login";
    private String freeLogin = "FreeLogin";
    private String token = "Token";
    private String password = "Password";
    private String hashedPassword = "e7cf3ef4f17c3999a94f2c6f612e8a888e5b1026878e4e19398b23bd38ec221a";

    @Before
    public void before() {
        user = mock(User.class);
        when(user.getRole()).thenReturn("ADMIN");
        when(user.getPassword()).thenReturn(hashedPassword);
        userDAO = mock(JDBCUserDAO.class);
        when(userDAO.getUserByLogin(login)).thenReturn(user);
        when(userDAO.getUserByLogin(freeLogin)).thenReturn(null);
        when(userDAO.getUserByToken(token)).thenReturn(user);
        when(userDAO.getUserByToken(null)).thenReturn(null);
        sessionDAO = mock(JDBCSessionDAO.class);
        when(sessionDAO.create(any())).thenReturn(true);
        DAOFactory daoFactory = mock(JDBCDAOFactory.class);
        when(daoFactory.getDAO(User.class)).thenReturn(userDAO);
        when(daoFactory.getDAO(Session.class)).thenReturn(sessionDAO);
        userService = new UserService(daoFactory);
    }

    @Test
    public void isBusyLoginTest(){
        assertTrue(userService.isBusyLogin(login));
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
        assertTrue(userService.isValidPassword(password));
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

    @Test
    public void register(){
        String role = "ROLE";
        userService.register(login, password, role, null, null, null);
        verify(userDAO).create(any(User.class));
    }

    @Test
    public void getHashedPasswordTest(){
        assertEquals(userService.getHashedPassword(password), hashedPassword);
    }

    @Test
    public void authenticateWithLoginAndPassword(){
        assertNotNull(userService.authenticate(login, password));
        assertNull(userService.authenticate(null, null));
        assertNull(userService.authenticate(null, password));
        assertNull(userService.authenticate(login, null));
    }

    @Test
    public void authenticateTestWithToken(){
        userService.authenticate(token);
        verify(userDAO).getUserByToken(token);
    }

    @Test
    public void verifyRoleTest(){
        assertEquals(Role.ADMIN, userService.verifyRole(token));
        assertNull(userService.verifyRole(null));
        assertNull(userService.verifyRole(""));
    }

    @Test
    public void updateUserTest(){
        userService.updateUser(user);
        verify(userDAO).update(user);
    }

    @Test
    public void getUsersByGroupTest(){
        Integer groupId = 0;
        userService.getUsersByGroup(groupId);
        verify(userDAO).getUsersByGroup(groupId);
    }
}
