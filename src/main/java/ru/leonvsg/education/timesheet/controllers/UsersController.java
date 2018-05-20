package ru.leonvsg.education.timesheet.controllers;

import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.entities.Role;
import ru.leonvsg.education.timesheet.entities.User;
import ru.leonvsg.education.timesheet.services.ServiceFactory;
import ru.leonvsg.education.timesheet.services.context.ViewContext;
import ru.leonvsg.education.timesheet.services.context.ViewContextService;
import ru.leonvsg.education.timesheet.services.entity.EntityServiceFactory;
import ru.leonvsg.education.timesheet.services.entity.UserService;
import ru.leonvsg.education.timesheet.services.Utils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsersController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UsersController.class);
    private final UserService userService;
    private final ViewContextService viewContextService;

    public UsersController() {
        super();
        ServiceFactory serviceFactory = EntityServiceFactory.getInstance();
        userService = serviceFactory.getService(User.class);
        viewContextService = new ViewContextService();
    }

    public UsersController(ServiceFactory serviceFactory, ViewContextService contextService) {
        super();
        userService = serviceFactory.getService(User.class);
        viewContextService = contextService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        LOGGER.info("Received GET request with params: " + Utils.requestParamsToString(req));
        String token = req.getSession().getAttribute("token").toString();
        ViewContext context = viewContextService.getUsersViewContext(token);
        if (context.getErrorMessage() == null || context.getErrorMessage().isEmpty()){
            req.setAttribute("context", context);
            req.getRequestDispatcher("/users.jsp").forward(req, resp);
            LOGGER.info("User is ADMIN. Show users list");
        } else {
            LOGGER.warn(context.getErrorMessage());
            resp.sendRedirect(req.getContextPath() + "user?id=" + userService.authenticate(token).getId());
            LOGGER.info("User is not ADMIN. Send redirect to 'MyPage': " + req.getContextPath() + "user?id=" + userService.authenticate(token).getId());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        LOGGER.info("Received POST request with params: " + Utils.requestParamsToString(req));
        ViewContext context = viewContextService.postUsersViewContext(
                req.getParameter("login"),
                req.getParameter("password"),
                req.getParameter("role"),
                req.getParameter("name"),
                req.getParameter("middlename"),
                req.getParameter("surname"),
                req.getSession().getAttribute("token").toString()
        );
        String errorMessage = context.getErrorMessage();
        LOGGER.warn("Create user result: " + errorMessage);
        resp.sendRedirect(req.getContextPath() + "users?errorMessage=" + errorMessage);
        LOGGER.info("Send redirect to " + req.getContextPath() + "users?errorMessage=" + errorMessage);
    }
}
