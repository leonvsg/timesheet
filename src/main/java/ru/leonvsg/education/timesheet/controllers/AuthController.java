package ru.leonvsg.education.timesheet.controllers;

import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.entities.Session;
import ru.leonvsg.education.timesheet.entities.User;
import ru.leonvsg.education.timesheet.services.ServiceFactory;
import ru.leonvsg.education.timesheet.services.entity.EntityServiceFactory;
import ru.leonvsg.education.timesheet.services.entity.SessionService;
import ru.leonvsg.education.timesheet.services.entity.UserService;
import ru.leonvsg.education.timesheet.services.Utils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AuthController.class);
    private final UserService userService;
    private final SessionService sessionService;

    public AuthController() {
        super();
        userService = EntityServiceFactory.getInstance().getService(User.class);
        sessionService = EntityServiceFactory.getInstance().getService(Session.class);

    }

    public AuthController(ServiceFactory serviceFactory) {
        super();
        userService = serviceFactory.getService(User.class);
        sessionService = serviceFactory.getService(Session.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        LOGGER.info("Received GET request with params: " + Utils.requestParamsToString(req));
        String exit = req.getParameter("exit");
        if (exit != null && exit.equals("true")){
            String token = req.getSession().getAttribute("token").toString();
            sessionService.invalidateToken(token);
            req.getSession().invalidate();
            resp.sendRedirect(req.getContextPath() + "/");
            LOGGER.info("Redirect to: " + req.getContextPath() + "/");
        } else {
            req.getRequestDispatcher("/auth.jsp").forward(req, resp);
            LOGGER.info("Param \"exit\" is null or not equals true. Show authentication form.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        LOGGER.info("Received POST request with params: " + Utils.requestParamsToString(req));
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        LOGGER.info("Try to generate token.");
        String token = userService.authenticate(login, password);
        LOGGER.info("Token = " + token);
        if (token != null){
            User user = userService.authenticate(token);
            req.getSession().setAttribute("token", token);
            req.getSession().setAttribute("userName", user.getName());
            req.getSession().setAttribute("userSurname", user.getSurname());
            req.getSession().setAttribute("userId", user.getId());
            req.getSession().setAttribute("userLogin", user.getLogin());
            resp.sendRedirect(req.getContextPath() + "/timesheet/");
            LOGGER.info("Authentication succeeded. Send redirect to " + req.getContextPath() + "/timesheet/");
        } else {
            resp.sendRedirect(req.getContextPath() + "/auth?errorMessage=authenticationFail");
            LOGGER.warn("Authentication failed. Send redirect to " +
                    req.getContextPath() + "/auth?errorMessage=authenticationFail");
        }
    }
}
