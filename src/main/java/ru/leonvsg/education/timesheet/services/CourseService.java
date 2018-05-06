package ru.leonvsg.education.timesheet.services;

import ru.leonvsg.education.timesheet.connections.ConnectionManager;
import ru.leonvsg.education.timesheet.connections.JDBCConnectionManager;
import ru.leonvsg.education.timesheet.dao.jdbc.JDBCCourseDAO;
import ru.leonvsg.education.timesheet.entities.Course;

import java.util.List;

public class CourseService {

    private ConnectionManager connectionManager = JDBCConnectionManager.getInstance();

    public List<Course> getAllCourses(){
        return new JDBCCourseDAO(connectionManager).getAll();
    }
}
