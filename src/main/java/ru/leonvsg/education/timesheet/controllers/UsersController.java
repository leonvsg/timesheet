package ru.leonvsg.education.timesheet.controllers;

import org.json.JSONObject;
import ru.leonvsg.education.timesheet.entities.Role;
import ru.leonvsg.education.timesheet.entities.User;
import ru.leonvsg.education.timesheet.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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

        /*String token = req.getParameter("token");
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if (userService.verifyRole(token) != Role.ADMIN){
            json.put("result", "failed");
            json.put("error", "Permission denied");
            resp.getWriter().println(json.toString());
            return;
        }
        userService.get().forEach(user -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", user.getId());
            jsonObject.put("login", user.getLogin());
            jsonObject.put("regDate", user.getRegDate());
            jsonObject.put("role", user.getRole());
            jsonObject.put("name", user.getName());
            jsonObject.put("middleName", user.getMiddleName());
            jsonObject.put("surname", user.getSurname());
            jsonArray.put(jsonObject);
        });
        json.put("result", "success");
        json.put("users", jsonArray);
        resp.getWriter().println(json.toString());*/
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain;charset=utf-8");
        resp.setCharacterEncoding("UTF-8");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String role = req.getParameter("role");
        String name = req.getParameter("name");
        String middlename = req.getParameter("middlename");
        String surname = req.getParameter("surname");
        String token = req.getParameter("token");
        JSONObject json = new JSONObject();
        if (userService.verifyRole(token) != Role.ADMIN){
            json.put("result", "failed");
            json.put("error", "Permission denied");
            resp.getWriter().println(json.toString());
            return;
        }
        if (!userService.isValidPassword(password)){
            json.put("result", "failed");
            json.put("error", "Invalid password");
            resp.getWriter().println(json.toString());
            return;
        }
        if (!userService.isValidRole(role)){
            json.put("result", "failed");
            json.put("error", "Invalid role");
            resp.getWriter().println(json.toString());
            return;
        }
        if (!userService.isValidLogin(login)){
            json.put("result", "failed");
            json.put("error", "Invalid login");
            resp.getWriter().println(json.toString());
            return;
        }
        if (userService.isBusyLogin(login)){
            json.put("result", "failed");
            json.put("error", "Login is busy");
            resp.getWriter().println(json.toString());
            return;
        }
        if (userService.register(login, password, role, name, middlename, surname)){
            resp.getWriter().println(json.put("result", "success").toString());
        }
        else resp.getWriter().println(json.put("result", "failed").toString());
    }
}
