import org.junit.Before;
import org.junit.Test;
import ru.leonvsg.education.timesheet.dao.basic.DAOFactory;
import ru.leonvsg.education.timesheet.dao.basic.GroupDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCDAOFactory;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCGroupDAO;
import ru.leonvsg.education.timesheet.entities.Group;
import ru.leonvsg.education.timesheet.services.RatingService;

import static org.mockito.Mockito.*;

public class RatingServiceTest {

    private RatingService ratingService;
    private GroupDAO groupDAO;
    private Group group;

    @Before
    public void before(){
        group = mock(Group.class);
        groupDAO = mock(JDBCGroupDAO.class);
        DAOFactory daoFactory = mock(JDBCDAOFactory.class);
        when(daoFactory.getDAO(Group.class)).thenReturn(groupDAO);
        ratingService = new RatingService(daoFactory);
    }

    @Test
    public void getRatingsByGroupTestWithGroupId(){
        Integer id = 0;
        ratingService.getRatingsByGroup(id);
        verify(groupDAO).getRating(id);
    }

    @Test
    public void getRatingsByGroupTestWithGroupObject(){
        ratingService.getRatingsByGroup(group);
        verify(groupDAO).getRating(group);
    }
}
