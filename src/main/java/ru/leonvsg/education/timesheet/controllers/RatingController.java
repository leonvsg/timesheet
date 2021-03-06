package ru.leonvsg.education.timesheet.controllers;

import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.entities.Role;
import ru.leonvsg.education.timesheet.entities.User;
import ru.leonvsg.education.timesheet.services.ServiceFactory;
import ru.leonvsg.education.timesheet.services.Utils;
import ru.leonvsg.education.timesheet.services.context.ViewContext;
import ru.leonvsg.education.timesheet.services.context.ViewContextService;
import ru.leonvsg.education.timesheet.services.entity.EntityServiceFactory;
import ru.leonvsg.education.timesheet.services.entity.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RatingController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(RatingController.class);
    private final UserService userService;
    private final ViewContextService viewContextService;

    public RatingController() {
        super();
        userService = EntityServiceFactory.getInstance().getService(User.class);
        viewContextService = new ViewContextService();
    }

    public RatingController(ServiceFactory serviceFactory, ViewContextService contextService) {
        super();
        userService = serviceFactory.getService(User.class);
        viewContextService = contextService;
    }

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
        ViewContext viewContext = viewContextService.getRatingViewContext(user, id);
        req.setAttribute("context", viewContext);
        req.getRequestDispatcher("/rating.jsp").forward(req, resp);
        LOGGER.info("Show rating for group with id=" + id);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        LOGGER.info("Received POST request with params: " + Utils.requestParamsToString(req));
        String groupId = req.getParameter("group");
        ViewContext context = viewContextService.postRatingViewContext(
                req.getParameter("student"),
                req.getParameter("lesson"),
                req.getParameter("value"),
                req.getParameter("description"),
                groupId,
                req.getSession().getAttribute("token").toString()
        );
        LOGGER.info("Rate result: " + context.getErrorMessage());
        resp.sendRedirect(req.getContextPath() + "rating?errorMessage=" + context.getErrorMessage() + "&group=" + groupId);
        LOGGER.info("Send redirect to " + req.getContextPath() + "rating?errorMessage=" + context.getErrorMessage() + "&group=" + groupId);
    }
}
