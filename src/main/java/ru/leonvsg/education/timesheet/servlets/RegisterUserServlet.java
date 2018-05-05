package ru.leonvsg.education.timesheet.servlets;

import org.json.JSONObject;
import ru.leonvsg.education.timesheet.Role;
import ru.leonvsg.education.timesheet.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain;charset=utf-8");
        resp.setCharacterEncoding("UTF-8");
        UserService userService = new UserService();
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
        if (!userService.isValidLogin(login)){
            json.put("result", "failed");
            json.put("error", "Invalid login format");
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
