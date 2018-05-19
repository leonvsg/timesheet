import org.junit.Before;
import org.junit.Test;
import ru.leonvsg.education.timesheet.dao.basic.DAOFactory;
import ru.leonvsg.education.timesheet.dao.basic.LessonDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCDAOFactory;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCLessonDAO;
import ru.leonvsg.education.timesheet.entities.Group;
import ru.leonvsg.education.timesheet.entities.Lesson;
import ru.leonvsg.education.timesheet.services.EntityServices.LessonService;

import static org.mockito.Mockito.*;

public class LessonServiceTest {

    private LessonService lessonService;
    private LessonDAO lessonDAO;
    private Group group;

    @Before
    public void before(){
        lessonDAO = mock(JDBCLessonDAO.class);
        DAOFactory daoFactory = mock(JDBCDAOFactory.class);
        when(daoFactory.getDAO(Lesson.class)).thenReturn(lessonDAO);
        lessonService = new LessonService(daoFactory);
    }

    @Test
    public void getLessonsTestWithGroupId(){
        Integer id = 0;
        lessonService.getLessons(id);
        verify(lessonDAO).getLessonsByGroup(id);
    }

    @Test
    public void getLessonsTestWithGroupObject(){
        lessonService.getLessons(group);
        verify(lessonDAO).getLessonsByGroup(group);
    }
}
