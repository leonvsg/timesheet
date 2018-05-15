package ru.leonvsg.education.timesheet.services;

import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.dao.basic.CourseDAO;
import ru.leonvsg.education.timesheet.dao.basic.DAOFactory;
import ru.leonvsg.education.timesheet.dao.basic.UserDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCDAOFactory;
import ru.leonvsg.education.timesheet.entities.Course;
import ru.leonvsg.education.timesheet.entities.User;

import java.util.List;

public class CourseService {

    private static final Logger LOGGER = Logger.getLogger(CourseService.class);
    private CourseDAO courseDAO;
    private UserDAO userDAO;

    public CourseService() {
        this(JDBCDAOFactory.getDAOFactory());
    }

    public CourseService(DAOFactory daoFactory) {
        courseDAO = daoFactory.getDAO(Course.class);
        userDAO = daoFactory.getDAO(User.class);
    }

    public boolean addCourse(String name, String description, Integer duration){
        return courseDAO.create(new Course(null, name, description, duration));
    }

    public List<Course> getAllCourses(){
        return courseDAO.getAll();
    }

    public Course getCourseById(Integer id){
        return courseDAO.read(id);
    }

    public List<Course> getCoursesByUser(User user){
        return userDAO.getCourses(user);
    }
}
