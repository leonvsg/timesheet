import org.junit.Before;
import org.junit.Test;
import ru.leonvsg.education.timesheet.dao.basic.CourseDAO;
import ru.leonvsg.education.timesheet.dao.basic.DAOFactory;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCCourseDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCDAOFactory;
import ru.leonvsg.education.timesheet.entities.Course;
import ru.leonvsg.education.timesheet.entities.User;
import ru.leonvsg.education.timesheet.services.entity.CourseService;

import static org.mockito.Mockito.*;

public class CourseServiceTest {

    private CourseService courseService;
    private CourseDAO courseDAO;
    private User user;

    @Before
    public void before(){
        user = mock(User.class);
        courseDAO = mock(JDBCCourseDAO.class);
        DAOFactory daoFactory = mock(JDBCDAOFactory.class);
        when(daoFactory.getDAO(Course.class)).thenReturn(courseDAO);
        courseService = new CourseService(daoFactory);
    }

    @Test
    public void addCourseTest(){
        courseService.addCourse(null, null, null);
        verify(courseDAO).create(any(Course.class));
    }

    @Test
    public void getAllCoursesTest(){
        courseService.getAllCourses();
        verify(courseDAO).getAll();
    }

    @Test
    public void getCourseByIdTest(){
        Integer id = 0;
        courseService.getCourseById(id);
        verify(courseDAO).read(id);
    }

    @Test
    public void getCoursesByUserTest(){
        courseService.getCoursesByUser(user);
        verify(courseDAO).getCoursesByUser(user);
    }
}
