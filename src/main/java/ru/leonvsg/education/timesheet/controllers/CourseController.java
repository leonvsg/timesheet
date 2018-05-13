package ru.leonvsg.education.timesheet.controllers;

import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.entities.Course;
import ru.leonvsg.education.timesheet.entities.Group;
import ru.leonvsg.education.timesheet.entities.Role;
import ru.leonvsg.education.timesheet.entities.User;
import ru.leonvsg.education.timesheet.services.CourseService;
import ru.leonvsg.education.timesheet.services.GroupService;
import ru.leonvsg.education.timesheet.services.UserService;
import ru.leonvsg.education.timesheet.services.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CourseController extends HttpServlet {

    private final static Logger LOGGER = Logger.getLogger(CourseController.class);
    private CourseService courseService = new CourseService();
    private UserService userService = new UserService();
    private GroupService groupService = new GroupService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        LOGGER.info("Received GET request with params: " + Utils.requestParamsToString(req));
        String token = req.getSession().getAttribute("token").toString();
        String id = req.getParameter("id");
        String display = req.getParameter("display");
        if (id != null) {
            LOGGER.info("ID not null. Try to get course by ID");
            Course course = courseService.getCourseById(Integer.valueOf(id));
            if (course == null) {
                LOGGER.info("Course with id=" + id + " not found. Send redirect to req.getContextPath() + \"course\"");
                resp.sendRedirect(req.getContextPath() + "course");
                return;
            }
            LOGGER.info("Found course: " + course.toString());
            List<Group> groups = groupService.getGroups(course);
            req.setAttribute("groups", groups);
            req.setAttribute("course", course);
            req.getRequestDispatcher("/course.jsp").forward(req, resp);
            LOGGER.info("Show course with id=" + id);
            return;
        }
        LOGGER.info("Course's ID is null.");
        List<Course> courses;
        User user = userService.authenticate(token);
        LOGGER.info("User's role is " + user.getRole() + ". Param display is " + display);
        if (display != null && !user.getRole().toUpperCase().equals("ADMIN") && display.equals("my")){
            courses = courseService.getCoursesByUser(user);
            LOGGER.info("Show courses by user with id=" + user.getId());
        }
        else {
            courses = courseService.getAllCourses();
            LOGGER.info("Show all courses");
        }
        req.setAttribute("courses", courses);
        req.setAttribute("user", user);
        req.getRequestDispatcher("/courses.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        LOGGER.info("Received POST request with params: " + Utils.requestParamsToString(req));
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        Integer duration = Integer.valueOf(req.getParameter("duration"));
        String token = req.getSession().getAttribute("token").toString();
        if (userService.verifyRole(token) != Role.ADMIN){
            resp.sendRedirect(req.getContextPath() + "course");
            LOGGER.warn("Don't have permission to add course!");
            LOGGER.info("Send redirect to " + req.getContextPath() + "course");
            return;
        }
        if (name == null || name.isEmpty()){
            resp.sendRedirect(req.getContextPath() + "course?errorMessage=InvalidCourseName");
            LOGGER.warn("Can't to add course. Invalid course's name");
            LOGGER.info("Send redirect to " + req.getContextPath() + "course?errorMessage=InvalidCourseName");
            return;
        }
        if (duration <= 0){
            resp.sendRedirect(req.getContextPath() + "course?errorMessage=InvalidDuration");
            LOGGER.warn("Can't to add course. Invalid course's duration");
            LOGGER.info("Send redirect to " + req.getContextPath() + "course?errorMessage=InvalidDuration");
            return;
        }
        if (courseService.addCourse(name, description, duration)){
            resp.sendRedirect(req.getContextPath() + "course?errorMessage=Success");
            LOGGER.info("Course added");
            LOGGER.info("Send redirect to " + req.getContextPath() + "course?errorMessage=Success");
        }
        else {
            resp.sendRedirect(req.getContextPath() + "course?errorMessage=WTF???");
            LOGGER.warn("Something went wrong!");
            LOGGER.info("Send redirect to " + req.getContextPath() + "course?errorMessage=WTF???");
        }
    }
}
