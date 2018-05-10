package ru.leonvsg.education.timesheet.controllers;

import ru.leonvsg.education.timesheet.entities.Course;
import ru.leonvsg.education.timesheet.entities.Group;
import ru.leonvsg.education.timesheet.entities.Role;
import ru.leonvsg.education.timesheet.entities.User;
import ru.leonvsg.education.timesheet.services.CourseService;
import ru.leonvsg.education.timesheet.services.GroupService;
import ru.leonvsg.education.timesheet.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CourseController extends HttpServlet {

    CourseService courseService = new CourseService();
    UserService userService = new UserService();
    GroupService groupService = new GroupService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String token = req.getSession().getAttribute("token").toString();
        String id = req.getParameter("id");
        String display = req.getParameter("display");
        if (id != null) {
            Course course = courseService.getCourseById(Integer.valueOf(id));
            List<Group> groups = groupService.getGroups(course);
            req.setAttribute("groups", groups);
            req.setAttribute("course", course);
            req.getRequestDispatcher("/course.jsp").forward(req, resp);
            return;
        }
        List<Course> courses;
        User user = userService.authenticate(token);
        if (display != null && user.getRole().toUpperCase() != "ADMIN" && display.equals("my"))
            courses = courseService.getCoursesByUser(user);
        else courses = courseService.getAllCourses();
        req.setAttribute("courses", courses);
        req.setAttribute("user", user);
        req.getRequestDispatcher("/courses.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        Integer duration = Integer.valueOf(req.getParameter("duration"));
        String token = req.getSession().getAttribute("token").toString();
        if (userService.verifyRole(token) != Role.ADMIN){
            resp.sendRedirect(req.getContextPath() + "course");
            return;
        }
        if (name == null || name.isEmpty()){
            resp.sendRedirect(req.getContextPath() + "course?errorMessage=InvalidCourseName");
            return;
        }
        if (duration <= 0){
            resp.sendRedirect(req.getContextPath() + "course?errorMessage=InvalidDuration");
            return;
        }
        if (courseService.addCourse(name, description, duration)){
            resp.sendRedirect(req.getContextPath() + "course?errorMessage=Success");
        }
        else resp.sendRedirect(req.getContextPath() + "course?errorMessage=WTF???");
    }
}
