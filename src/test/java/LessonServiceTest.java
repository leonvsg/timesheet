import org.junit.Before;
import org.junit.Test;
import ru.leonvsg.education.timesheet.dao.basic.DAOFactory;
import ru.leonvsg.education.timesheet.dao.basic.GroupDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCDAOFactory;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCGroupDAO;
import ru.leonvsg.education.timesheet.entities.Group;
import ru.leonvsg.education.timesheet.services.LessonService;

import static org.mockito.Mockito.*;

public class LessonServiceTest {

    private LessonService lessonService;
    private GroupDAO groupDAO;
    private Group group;

    @Before
    public void before(){
        groupDAO = mock(JDBCGroupDAO.class);
        DAOFactory daoFactory = mock(JDBCDAOFactory.class);
        when(daoFactory.getDAO(Group.class)).thenReturn(groupDAO);
        lessonService = new LessonService(daoFactory);
    }

    @Test
    public void getLessonsTestWithGroupId(){
        Integer id = 0;
        lessonService.getLessons(id);
        verify(groupDAO).getLessons(id);
    }

    @Test
    public void getLessonsTestWithGroupObject(){
        lessonService.getLessons(group);
        verify(groupDAO).getLessons(group);
    }
}
