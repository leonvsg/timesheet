import org.junit.Before;
import org.junit.Test;
import ru.leonvsg.education.timesheet.dao.basic.DAOFactory;
import ru.leonvsg.education.timesheet.dao.basic.GroupDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCDAOFactory;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCGroupDAO;
import ru.leonvsg.education.timesheet.entities.Course;
import ru.leonvsg.education.timesheet.entities.Group;
import ru.leonvsg.education.timesheet.entities.User;
import ru.leonvsg.education.timesheet.services.EntityServices.GroupService;

import static org.mockito.Mockito.*;

public class GroupServiceTest {

    private GroupService groupService;
    private GroupDAO groupDAO;
    private Course course;
    private User user;

    @Before
    public void before(){
        user = mock(User.class);
        course = mock(Course.class);
        groupDAO = mock(JDBCGroupDAO.class);
        DAOFactory daoFactory = mock(JDBCDAOFactory.class);
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
        verify(groupDAO).getGroupsByCourse(course);
    }

    @Test
    public void getGroupsTestGroupsByUser(){
        groupService.getGroups(user);
        verify(groupDAO).getGroupsWithCourses(user);
    }
}
