package ru.leonvsg.education.timesheet.controllers;

import ru.leonvsg.education.timesheet.entities.*;
import ru.leonvsg.education.timesheet.services.GroupService;
import ru.leonvsg.education.timesheet.services.LessonService;
import ru.leonvsg.education.timesheet.services.RatingService;
import ru.leonvsg.education.timesheet.services.UserService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RatingController extends HttpServlet {

    private GroupService groupService = new GroupService();
    private UserService userService = new UserService();
    private LessonService lessonService = new LessonService();
    private RatingService ratingService = new RatingService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String token = req.getSession().getAttribute("token").toString();
        User user = userService.authenticate(token);
        if (user.getRole().toUpperCase().equals(Role.ADMIN.toString())){
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }
        Integer id = req.getParameter("group") != null ? Integer.valueOf(req.getParameter("group")) : null;
        List<Lesson> lessons = new ArrayList<>();
        List<User> users =  new ArrayList<>();
        List<Rating> ratings = new ArrayList<>();
        List<Group> groups = new ArrayList<>();
        if (id != null){
            lessons = lessonService.getLessons(id);
            if (!user.getRole().toUpperCase().equals("STUDENT")) users = userService.getUsersByGroup(id);
            else users.add(user);
            ratings = ratingService.getRatingsByGroup(id);
        }
        req.setAttribute("lessons", lessons);
        req.setAttribute("users", users);
        req.setAttribute("ratings", ratings);
        req.setAttribute("groups", groupService.getGroups(user));
        req.setAttribute("user", user);
        req.getRequestDispatcher("/rating.jsp").forward(req, resp);
    }
}
