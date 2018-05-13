package ru.leonvsg.education.timesheet.controllers;

import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.entities.*;
import ru.leonvsg.education.timesheet.services.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RatingController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(RatingController.class);
    private final GroupService groupService = new GroupService();
    private final UserService userService = new UserService();
    private final LessonService lessonService = new LessonService();
    private final RatingService ratingService = new RatingService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        LOGGER.info("Received GET request with params: " + Utils.requestParamsToString(req));
        String token = req.getSession().getAttribute("token").toString();
        User user = userService.authenticate(token);
        if (user.getRole().equalsIgnoreCase(Role.ADMIN.toString())){
            resp.sendRedirect(req.getContextPath() + "/");
            LOGGER.warn("User is ADMIN. Redirect to " + req.getContextPath() + "/");
            return;
        }
        Integer id = req.getParameter("group") != null ? Integer.valueOf(req.getParameter("group")) : null;
        List<Lesson> lessons = new ArrayList<>();
        List<User> users =  new ArrayList<>();
        List<Rating> ratings = new ArrayList<>();
        List<Group> groups = groupService.getGroups(user);
        if (id != null){
            lessons = lessonService.getLessons(id);
            if (!user.getRole().equalsIgnoreCase("STUDENT")) users = userService.getUsersByGroup(id);
            else users.add(user);
            ratings = ratingService.getRatingsByGroup(id);
        }
        req.setAttribute("lessons", lessons);
        req.setAttribute("users", users);
        req.setAttribute("ratings", ratings);
        req.setAttribute("groups", groups);
        req.setAttribute("user", user);
        req.getRequestDispatcher("/rating.jsp").forward(req, resp);
        LOGGER.info("Show rating for group with id=" + id);
    }
}
