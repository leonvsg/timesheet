package ru.leonvsg.education.timesheet.controllers;

import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.entities.*;
import ru.leonvsg.education.timesheet.services.*;
import ru.leonvsg.education.timesheet.services.EntityServices.UserService;
import ru.leonvsg.education.timesheet.services.context.ContextDirector;
import ru.leonvsg.education.timesheet.services.context.ViewContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RatingController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(RatingController.class);
    private final UserService userService = new UserService();
    private final ContextDirector context = new ContextDirector();

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
        ViewContext viewContext = context.getRatingViewContext(user, id);
        req.setAttribute("context", viewContext);
        req.getRequestDispatcher("/rating.jsp").forward(req, resp);
        LOGGER.info("Show rating for group with id=" + id);
    }
}
