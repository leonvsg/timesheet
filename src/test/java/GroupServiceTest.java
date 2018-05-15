import org.junit.Before;
import org.junit.Test;
import ru.leonvsg.education.timesheet.dao.basic.CourseDAO;
import ru.leonvsg.education.timesheet.dao.basic.DAOFactory;
import ru.leonvsg.education.timesheet.dao.basic.GroupDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCCourseDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCDAOFactory;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCGroupDAO;
import ru.leonvsg.education.timesheet.entities.Course;
import ru.leonvsg.education.timesheet.entities.Group;
import ru.leonvsg.education.timesheet.entities.User;
import ru.leonvsg.education.timesheet.services.GroupService;

import static org.mockito.Mockito.*;

public class GroupServiceTest {

    private GroupService groupService;
    private CourseDAO courseDAO;
    private GroupDAO groupDAO;
    private Course course;
    private User user;

    @Before
    public void before(){
        user = mock(User.class);
        course = mock(Course.class);
        courseDAO = mock(JDBCCourseDAO.class);
        groupDAO = mock(JDBCGroupDAO.class);
        DAOFactory daoFactory = mock(JDBCDAOFactory.class);
        when(daoFactory.getDAO(Course.class)).thenReturn(courseDAO);
        when(daoFactory.getDAO(Group.class)).thenReturn(groupDAO);
        groupService = new GroupService(daoFactory);
    }

    @Test
    public void getGroupsTestAllGroupsWithCourses(){
        groupService.getGroups();
        verify(groupDAO).getGroupsWithCourses();
    }

    @Test
    public void getGroupsTestGroupsByCourse(){
        groupService.getGroups(course);
        verify(courseDAO).getGroups(course);
    }

    @Test
    public void getGroupsTestGroupsByUser(){
        groupService.getGroups(user);
        verify(groupDAO).getGroupsWithCourses(user);
    }
}
