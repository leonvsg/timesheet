package ru.leonvsg.education.timesheet.servlets;

import ru.leonvsg.education.timesheet.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String token = new UserService().authenticate(login, password);
        if (token == null) resp.getWriter().println("Некорректный логин/пароль");
        else resp.getWriter().println("Успешно. Токен: " + token);
    }
}
