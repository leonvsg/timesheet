package ru.leonvsg.education.timesheet.services;

import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.dao.basic.CourseDAO;
import ru.leonvsg.education.timesheet.dao.basic.DAOFactory;
import ru.leonvsg.education.timesheet.dao.basic.GroupDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCDAOFactory;
import ru.leonvsg.education.timesheet.entities.Course;
import ru.leonvsg.education.timesheet.entities.Group;
import ru.leonvsg.education.timesheet.entities.User;

import java.util.List;

public class GroupService {

    private static final Logger LOGGER = Logger.getLogger(GroupService.class);
    private CourseDAO courseDAO;
    private GroupDAO groupDAO;

    public GroupService() {
        this(JDBCDAOFactory.getDAOFactory());
    }

    public GroupService(DAOFactory daoFactory) {
        courseDAO = daoFactory.getDAO(Course.class);
        groupDAO = daoFactory.getDAO(Group.class);
    }

    public List<Group> getGroups(){
        return groupDAO.getGroupsWithCourses();
    }

    public List<Group> getGroups(Course course){
        return courseDAO.getGroups(course);
    }

    public List<Group> getGroups(User user){
        return groupDAO.getGroupsWithCourses(user);
    }
}
