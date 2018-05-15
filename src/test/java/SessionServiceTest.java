import org.junit.Before;
import org.junit.Test;
import ru.leonvsg.education.timesheet.dao.basic.DAOFactory;
import ru.leonvsg.education.timesheet.dao.basic.SessionDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCDAOFactory;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCSessionDAO;
import ru.leonvsg.education.timesheet.entities.Session;
import ru.leonvsg.education.timesheet.services.SessionService;

import static org.mockito.Mockito.*;

public class SessionServiceTest {

    private SessionService sessionService;
    private SessionDAO sessionDAO;

    @Before
    public void before(){
        sessionDAO = mock(JDBCSessionDAO.class);
        DAOFactory daoFactory = mock(JDBCDAOFactory.class);
        when(daoFactory.getDAO(Session.class)).thenReturn(sessionDAO);
        sessionService = new SessionService(daoFactory);
    }

    @Test
    public void invalidateTokenTest(){
        String token = "token";
        sessionService.invalidateToken(token);
        verify(sessionDAO).delete(token);
    }
}
