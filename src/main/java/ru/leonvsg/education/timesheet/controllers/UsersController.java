package ru.leonvsg.education.timesheet.controllers;

import org.json.JSONObject;
import ru.leonvsg.education.timesheet.entities.Role;
import ru.leonvsg.education.timesheet.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsersController extends HttpServlet {

    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String token = req.getSession().getAttribute("token").toString();
        if (userService.verifyRole(token) == Role.ADMIN){
            req.setAttribute("users", userService.get());
            req.getRequestDispatcher("/users.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "user?id=" + userService.authenticate(token).getId());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String role = req.getParameter("role");
        String name = req.getParameter("name");
        String middlename = req.getParameter("middlename");
        String surname = req.getParameter("surname");
        String token = req.getSession().getAttribute("token").toString();
        if (userService.verifyRole(token) != Role.ADMIN){
            resp.sendRedirect(req.getContextPath() + "users");
            return;
        }
        if (!userService.isValidPassword(password)){
            resp.sendRedirect(req.getContextPath() + "users?errorMessage=InvalidPassword");
            return;
        }
        if (!userService.isValidRole(role)){
            resp.sendRedirect(req.getContextPath() + "users?errorMessage=InvalidRole");
            return;
        }
        if (!userService.isValidLogin(login)){
            resp.sendRedirect(req.getContextPath() + "users?errorMessage=InvalidLogin");
            return;
        }
        if (userService.isBusyLogin(login)){
            resp.sendRedirect(req.getContextPath() + "users?errorMessage=LoginIsBusy");
            return;
        }
        if (userService.register(login, password, role, name, middlename, surname)){
            resp.sendRedirect(req.getContextPath() + "users?errorMessage=Success");
        }
        else resp.sendRedirect(req.getContextPath() + "users?errorMessage=WTF???");
    }
}
