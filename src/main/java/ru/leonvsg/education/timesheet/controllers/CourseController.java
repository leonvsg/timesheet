package ru.leonvsg.education.timesheet.controllers;

import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.entities.User;
import ru.leonvsg.education.timesheet.services.ServiceFactory;
import ru.leonvsg.education.timesheet.services.entity.EntityServiceFactory;
import ru.leonvsg.education.timesheet.services.entity.UserService;
import ru.leonvsg.education.timesheet.services.Utils;
import ru.leonvsg.education.timesheet.services.context.ViewContextService;
import ru.leonvsg.education.timesheet.services.context.ViewContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CourseController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CourseController.class);

    private final UserService userService;
    private final ViewContextService viewContextService;

    public CourseController() {
        super();
        userService = EntityServiceFactory.getInstance().getService(User.class);
        viewContextService = new ViewContextService();
    }

    public CourseController(ServiceFactory serviceFactory, ViewContextService contextService) {
        super();
        userService = serviceFactory.getService(User.class);
        viewContextService = contextService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        LOGGER.info("Received GET request with params: " + Utils.requestParamsToString(req));
        String token = req.getSession().getAttribute("token").toString();
        String id = req.getParameter("id");
        String display = req.getParameter("display");
        ViewContext context;
        if (id == null) {
            LOGGER.info("Course's ID is null.");
            User user = userService.authenticate(token);
            LOGGER.info("User's role is " + user.getRole() + ". Param display is " + display);
            context = viewContextService.getCourseViewContext(user, display);
            LOGGER.info(context.getErrorMessage());
            req.setAttribute("context", context);
            req.getRequestDispatcher("/courses.jsp").forward(req, resp);
            return;
        }
        context = viewContextService.getCourseViewContextByCourseId(id);
        LOGGER.info(context.getErrorMessage());
        if (context.getCourse() == null){
            LOGGER.info("Send redirect to req.getContextPath() + \"course\"");
            resp.sendRedirect(req.getContextPath() + "course");
        }else {
            req.setAttribute("context", context);
            req.getRequestDispatcher("/course.jsp").forward(req, resp);
            LOGGER.info("Show course with id=" + id);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        LOGGER.info("Received POST request with params: " + Utils.requestParamsToString(req));
        ViewContext context = viewContextService.postCourseViewContext(
                req.getSession().getAttribute("token").toString(),
                req.getParameter("name"),
                req.getParameter("description"),
                req.getParameter("duration"));
        String errorMessage = context.getErrorMessage();
        resp.sendRedirect(req.getContextPath() + "course?errorMessage=" + errorMessage);
        LOGGER.info("Add course result: " + errorMessage);
        LOGGER.info("Send redirect to " + req.getContextPath() + "course?errorMessage=" + errorMessage);
    }
}
