import org.junit.Before;
import org.junit.Test;
import ru.leonvsg.education.timesheet.dao.basic.DAOFactory;
import ru.leonvsg.education.timesheet.dao.basic.RatingDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCDAOFactory;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCRatingDAO;
import ru.leonvsg.education.timesheet.entities.Group;
import ru.leonvsg.education.timesheet.entities.Rating;
import ru.leonvsg.education.timesheet.services.EntityServices.RatingService;

import static org.mockito.Mockito.*;

public class RatingServiceTest {

    private RatingService ratingService;
    private RatingDAO ratingDAO;
    private Group group;

    @Before
    public void before(){
        group = mock(Group.class);
        ratingDAO = mock(JDBCRatingDAO.class);
        DAOFactory daoFactory = mock(JDBCDAOFactory.class);
        when(daoFactory.getDAO(Rating.class)).thenReturn(ratingDAO);
        ratingService = new RatingService(daoFactory);
    }

    @Test
    public void getRatingsByGroupTestWithGroupId(){
        Integer id = 0;
        ratingService.getRatingsByGroup(id);
        verify(ratingDAO).getRatingByGroup(id);
    }

    @Test
    public void getRatingsByGroupTestWithGroupObject(){
        ratingService.getRatingsByGroup(group);
        verify(ratingDAO).getRatingByGroup(group);
    }
}
