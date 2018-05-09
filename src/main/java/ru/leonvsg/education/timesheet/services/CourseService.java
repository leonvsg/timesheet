package ru.leonvsg.education.timesheet.services;

import ru.leonvsg.education.timesheet.connections.ConnectionManager;
import ru.leonvsg.education.timesheet.connections.JDBCConnectionManager;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCCourseDAO;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCUserDAO;
import ru.leonvsg.education.timesheet.entities.Course;
import ru.leonvsg.education.timesheet.entities.User;
import sun.plugin2.gluegen.runtime.StructAccessor;

import java.util.List;

public class CourseService {

    public boolean addCourse(String name, String description, Integer duration){
        return new JDBCCourseDAO(connectionManager).create(new Course(null, name, description, duration));
    }

    private ConnectionManager connectionManager = JDBCConnectionManager.getInstance();

    public List<Course> getAllCourses(){
        return new JDBCCourseDAO(connectionManager).getAll();
    }

    public Course getCourseById(Integer id){
        return new JDBCCourseDAO(connectionManager).read(id);
    }

    public List<Course> getCoursesByUser(User user){
        return new JDBCUserDAO(connectionManager).getCourses(user);
    }
}
