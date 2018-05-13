package ru.leonvsg.education.timesheet.controllers;

import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.entities.Role;
import ru.leonvsg.education.timesheet.services.UserService;
import ru.leonvsg.education.timesheet.services.Utils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsersController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UsersController.class);
    private static UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        LOGGER.info("Received GET request with params: " + Utils.requestParamsToString(req));
        String token = req.getSession().getAttribute("token").toString();
        if (userService.verifyRole(token) == Role.ADMIN){
            req.setAttribute("users", userService.get());
            req.getRequestDispatcher("/users.jsp").forward(req, resp);
            LOGGER.info("User is ADMIN. Show users list");
        } else {
            resp.sendRedirect(req.getContextPath() + "user?id=" + userService.authenticate(token).getId());
            LOGGER.info("User is not ADMIN. Send redirect to 'MyPage': " + req.getContextPath() + "user?id=" + userService.authenticate(token).getId());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        LOGGER.info("Received POST request with params: " + Utils.requestParamsToString(req));
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String role = req.getParameter("role");
        String name = req.getParameter("name");
        String middlename = req.getParameter("middlename");
        String surname = req.getParameter("surname");
        String token = req.getSession().getAttribute("token").toString();
        if (userService.verifyRole(token) != Role.ADMIN){
            resp.sendRedirect(req.getContextPath() + "users");
            LOGGER.warn("Can't to create user. Need ADMIN role.");
            LOGGER.info("Send redirect to " + req.getContextPath() + "users");
            return;
        }
        if (!userService.isValidPassword(password)){
            resp.sendRedirect(req.getContextPath() + "users?errorMessage=InvalidPassword");
            LOGGER.warn("Can't to create user. Invalid password");
            LOGGER.info("Send redirect to " + req.getContextPath() + "users?errorMessage=InvalidPassword");
            return;
        }
        if (!userService.isValidRole(role)){
            resp.sendRedirect(req.getContextPath() + "users?errorMessage=InvalidRole");
            LOGGER.warn("Can't to create user. Invalid role");
            LOGGER.info("Send redirect to " + req.getContextPath() + "users?errorMessage=InvalidRole");
            return;
        }
        if (!userService.isValidLogin(login)){
            resp.sendRedirect(req.getContextPath() + "users?errorMessage=InvalidLogin");
            LOGGER.warn("Can't to create user. Invalid login");
            LOGGER.info("Send redirect to " + req.getContextPath() + "users?errorMessage=InvalidLogin");
            return;
        }
        if (userService.isBusyLogin(login)){
            resp.sendRedirect(req.getContextPath() + "users?errorMessage=LoginIsBusy");
            LOGGER.warn("Can't to create user. Login is busy");
            LOGGER.info("Send redirect to " + req.getContextPath() + "users?errorMessage=LoginIsBusy");
            return;
        }
        if (userService.register(login, password, role, name, middlename, surname)){
            resp.sendRedirect(req.getContextPath() + "users?errorMessage=Success");
            LOGGER.info("User created");
            LOGGER.info("Send redirect to " + req.getContextPath() + "users?errorMessage=Success");
        }
        else {
            resp.sendRedirect(req.getContextPath() + "users?errorMessage=WTF???");
            LOGGER.warn("Something went wrong!");
            LOGGER.info("Send redirect to " + req.getContextPath() + "users?errorMessage=WTF???");
        }
    }
}
