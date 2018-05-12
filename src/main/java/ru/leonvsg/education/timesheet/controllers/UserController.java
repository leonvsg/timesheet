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

public class UserController extends HttpServlet {

    UserService userService = new UserService();
    GroupService groupService = new GroupService();
    CourseService courseService = new CourseService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String token = req.getSession().getAttribute("token").toString();
        String id = req.getParameter("id");
        if (id == null || token == null) {
            resp.sendRedirect(req.getContextPath() + "users");
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
        } else {
            resp.sendRedirect(req.getContextPath() + "users");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String middlename = req.getParameter("middlename");
        String surname = req.getParameter("surname");
        String token = req.getSession().getAttribute("token").toString();
        if (login.isEmpty() && password.isEmpty() && name.isEmpty() && middlename.isEmpty() && surname.isEmpty()){
            resp.sendRedirect(req.getContextPath() + "user?errorMessage=NothingToChange&id=" + id);
            return;
        }
        if (id.isEmpty() || token == null) {
            resp.sendRedirect(req.getContextPath() + "user?errorMessage=InvalidUserId&id=" + id);
            return;
        }
        if (!password.isEmpty() && !userService.isValidPassword(password)){
            resp.sendRedirect(req.getContextPath() + "user?errorMessage=InvalidPassword&id=" + id);
            return;
        }
        if (!login.isEmpty() && !userService.isValidLogin(login)){
            resp.sendRedirect(req.getContextPath() + "user?errorMessage=InvalidLogin&id=" + id);
            return;
        }
        if (!login.isEmpty() && userService.isBusyLogin(login)){
            resp.sendRedirect(req.getContextPath() + "user?errorMessage=LoginIsBusy&id=" + id);
            return;
        }
        User user;
        if (userService.verifyRole(token) == Role.ADMIN){
            user = userService.getUser(Integer.valueOf(id));
        } else {
            user = userService.authenticate(token);
        }
        if (!login.isEmpty()) user.setLogin(login);
        if (!password.isEmpty()) user.setPassword(userService.getHashedPassword(password));
        if (!name.isEmpty()) user.setName(name);
        if (!middlename.isEmpty()) user.setMiddleName(middlename);
        if (!surname.isEmpty()) user.setSurname(surname);
        if (userService.updateUser(user)){
            resp.sendRedirect(req.getContextPath() + "user?errorMessage=Success&id=" + id);
        } else {
            resp.sendRedirect(req.getContextPath() + "user?errorMessage=WTF???&id=" + id);
        }

    }
}
