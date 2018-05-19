package ru.leonvsg.education.timesheet.controllers;

import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.entities.Group;
import ru.leonvsg.education.timesheet.entities.Role;
import ru.leonvsg.education.timesheet.entities.User;
import ru.leonvsg.education.timesheet.services.EntityServices.GroupService;
import ru.leonvsg.education.timesheet.services.EntityServices.UserService;
import ru.leonvsg.education.timesheet.services.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UserController.class);
    private final UserService userService = new UserService();
    private final GroupService groupService = new GroupService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        LOGGER.info("Received GET request with params: " + Utils.requestParamsToString(req));
        String token = req.getSession().getAttribute("token").toString();
        String id = req.getParameter("id");
        if (id == null || token == null) {
            resp.sendRedirect(req.getContextPath() + "users");
            LOGGER.warn("ID or token is null.");
            LOGGER.info("Send redirect to " + req.getContextPath() + "users");
            return;
        }
        User user;
        if (userService.verifyRole(token) == Role.ADMIN){
            user = userService.getUser(Integer.valueOf(id));
        } else {
            user = userService.authenticate(token);
        }
        List<Group> groups = groupService.getGroups(user);
        if (user != null && user.getId().toString().equals(id)){
            req.setAttribute("user", user);
            req.setAttribute("groups", groups);
            req.getRequestDispatcher("/user.jsp").forward(req, resp);
            LOGGER.info("Show user with id=" + user.getId());
        } else {
            resp.sendRedirect(req.getContextPath() + "users");
            LOGGER.warn("User with id=" + id + " not found, or access denied");
            LOGGER.info("Send redirect to " + req.getContextPath() + "users");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        LOGGER.info("Received POST request with params: " + Utils.requestParamsToString(req));
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String middlename = req.getParameter("middlename");
        String surname = req.getParameter("surname");
        String token = req.getSession().getAttribute("token").toString();
        if (login.isEmpty() && password.isEmpty() && name.isEmpty() && middlename.isEmpty() && surname.isEmpty()){
            resp.sendRedirect(req.getContextPath() + "user?errorMessage=NothingToChange&id=" + id);
            LOGGER.warn("Can't to update user. All params is empty");
            LOGGER.info("Send redirect to " + req.getContextPath() + "user?errorMessage=NothingToChange&id=" + id);
            return;
        }
        if (id.isEmpty() || token == null) {
            resp.sendRedirect(req.getContextPath() + "user?errorMessage=InvalidUserId&id=" + id);
            LOGGER.warn("Can't to update user. Id is empty");
            LOGGER.info("Send redirect to " + req.getContextPath() + "user?errorMessage=InvalidUserId&id=" + id);
            return;
        }
        if (!password.isEmpty() && !userService.isValidPassword(password)){
            resp.sendRedirect(req.getContextPath() + "user?errorMessage=InvalidPassword&id=" + id);
            LOGGER.warn("Can't to update user. Invalid password");
            LOGGER.info("Send redirect to " + req.getContextPath() + "user?errorMessage=InvalidPassword&id=" + id);
            return;
        }
        if (!login.isEmpty() && !userService.isValidLogin(login)){
            resp.sendRedirect(req.getContextPath() + "user?errorMessage=InvalidLogin&id=" + id);
            LOGGER.warn("Can't to update user. Invalid login");
            LOGGER.info("Send redirect to " + req.getContextPath() + "user?errorMessage=InvalidLogin&id=" + id);
            return;
        }
        if (!login.isEmpty() && userService.isBusyLogin(login)){
            resp.sendRedirect(req.getContextPath() + "user?errorMessage=LoginIsBusy&id=" + id);
            LOGGER.warn("Can't to update user. Login is busy");
            LOGGER.info("Send redirect to " + req.getContextPath() + "user?errorMessage=LoginIsBusy&id=" + id);
            return;
        }
        User user;
        if (userService.verifyRole(token) == Role.ADMIN){
            user = userService.getUser(Integer.valueOf(id));
        } else {
            user = userService.authenticate(token);
        }
        LOGGER.info("User update from: " + user.toString());
        if (!login.isEmpty()) user.setLogin(login);
        if (!password.isEmpty()) user.setPassword(userService.getHashedPassword(password));
        if (!name.isEmpty()) user.setName(name);
        if (!middlename.isEmpty()) user.setMiddleName(middlename);
        if (!surname.isEmpty()) user.setSurname(surname);
        LOGGER.info("User update to: " + user.toString());
        if (userService.updateUser(user)){
            resp.sendRedirect(req.getContextPath() + "user?errorMessage=Success&id=" + id);
            LOGGER.info("User updated");
            LOGGER.info("Send redirect to " + req.getContextPath() + "user?errorMessage=Success&id=" + id);
        } else {
            resp.sendRedirect(req.getContextPath() + "user?errorMessage=WTF???&id=" + id);
            LOGGER.warn("Something went wrong!");
            LOGGER.info("Send redirect to " + req.getContextPath() + "user?errorMessage=WTF???");
        }

    }
}
