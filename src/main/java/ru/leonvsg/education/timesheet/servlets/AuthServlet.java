package ru.leonvsg.education.timesheet.servlets;

import org.json.JSONObject;
import ru.leonvsg.education.timesheet.services.UserService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain;charset=utf-8");
        resp.setCharacterEncoding("UTF-8");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String token = new UserService().authenticate(login, password);
        JSONObject json = new JSONObject();
        if (token == null) {
            json.put("result", "failed");
            resp.getWriter().println(json.toString());
        }
        else {
            json.put("result", "success");
            json.put("token", token);
            resp.getWriter().println(json.toString());
        }
    }
}
