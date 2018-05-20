package ru.leonvsg.education.timesheet.services.entity;

import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.dao.basic.DAOFactory;
import ru.leonvsg.education.timesheet.dao.basic.GroupDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCDAOFactory;
import ru.leonvsg.education.timesheet.entities.Course;
import ru.leonvsg.education.timesheet.entities.Group;
import ru.leonvsg.education.timesheet.entities.User;
import ru.leonvsg.education.timesheet.services.Service;

import java.util.List;

public class GroupService implements Service {

    private static final Logger LOGGER = Logger.getLogger(GroupService.class);
    private GroupDAO groupDAO;

    public GroupService() {
        this(JDBCDAOFactory.getDAOFactory());
    }

    public GroupService(DAOFactory daoFactory) {
        groupDAO = daoFactory.getDAO(Group.class);
    }

    public List<Group> getGroups(){
        return groupDAO.getGroupsWithCourses();
    }

    public List<Group> getGroups(Course course){
        return groupDAO.getGroupsByCourse(course);
    }

    public List<Group> getGroups(User user){
        return groupDAO.getGroupsWithCourses(user);
    }
}
