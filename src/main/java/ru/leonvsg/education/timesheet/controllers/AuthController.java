package ru.leonvsg.education.timesheet.controllers;

import ru.leonvsg.education.timesheet.entities.User;
import ru.leonvsg.education.timesheet.services.SessionService;
import ru.leonvsg.education.timesheet.services.UserService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthController extends HttpServlet {

    UserService userService = new UserService();
    SessionService sessionService = new SessionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String exit = req.getParameter("exit");
        if (exit != null && exit.equals("true")){
            String token = req.getSession().getAttribute("token").toString();
            sessionService.invalidateToken(token);
            req.getSession().invalidate();
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            req.getRequestDispatcher("/auth.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String token = userService.authenticate(login, password);
        if (token != null){
            User user = userService.authenticate(token);
            req.getSession().setAttribute("token", token);
            req.getSession().setAttribute("userName", user.getName());
            req.getSession().setAttribute("userSurname", user.getSurname());
            req.getSession().setAttribute("userId", user.getId());
            req.getSession().setAttribute("userLogin", user.getLogin());
            resp.sendRedirect(req.getContextPath() + "/timesheet/");
        } else resp.sendRedirect(req.getContextPath() + "/auth?errorMessage=authenticationFail");

        /*JSONObject json = new JSONObject();
        if (token == null) {
            json.put("result", "failed");
            resp.getWriter().println(json.toString());
        }
        else {
            json.put("result", "success");
            json.put("token", token);
            resp.getWriter().println(json.toString());
        }*/
    }
}
