package ru.leonvsg.education.timesheet.controllers;

import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.services.Utils;
import ru.leonvsg.education.timesheet.services.context.ViewContext;
import ru.leonvsg.education.timesheet.services.context.ViewContextService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UserController.class);
    private final ViewContextService viewContextService;

    public UserController() {
        super();
        viewContextService = new ViewContextService();
    }

    public UserController(ViewContextService contextService) {
        super();
        viewContextService = contextService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        LOGGER.info("Received GET request with params: " + Utils.requestParamsToString(req));
        String token = req.getSession().getAttribute("token").toString();
        String id = req.getParameter("id");
        ViewContext context = viewContextService.getUserViewContext(token, id);
        String errorMessage = context.getErrorMessage();
        if (errorMessage == null || errorMessage.equalsIgnoreCase("")){
            req.setAttribute("context", context);
            req.getRequestDispatcher("/user.jsp").forward(req, resp);
            LOGGER.info("Show user with id=" + id);
        } else {
            LOGGER.warn(errorMessage);
            resp.sendRedirect(req.getContextPath() + "users");
            LOGGER.info("Send redirect to " + req.getContextPath() + "users");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        LOGGER.info("Received POST request with params: " + Utils.requestParamsToString(req));
        String id = req.getParameter("id");
        ViewContext context = viewContextService.postUserViewContext(
                req.getParameter("login"),
                req.getParameter("password"),
                id,
                req.getParameter("name"),
                req.getParameter("middlename"),
                req.getParameter("surname"),
                req.getSession().getAttribute("token").toString()
        );
        LOGGER.info("Update user result: " + context.getErrorMessage());
        resp.sendRedirect(req.getContextPath() + "user?errorMessage=" + context.getErrorMessage() + "&id=" + id);
        LOGGER.info("Send redirect to " + req.getContextPath() + "user?errorMessage=" + context.getErrorMessage()+ "&id=" + id);
    }
}
