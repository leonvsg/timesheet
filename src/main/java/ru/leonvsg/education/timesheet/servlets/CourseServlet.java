package ru.leonvsg.education.timesheet.servlets;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.leonvsg.education.timesheet.services.CourseService;
import ru.leonvsg.education.timesheet.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CourseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain;charset=utf-8");
        resp.setCharacterEncoding("UTF-8");
        CourseService courseService = new CourseService();
        UserService userService = new UserService();
        String token = req.getParameter("token");
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if (token == null || userService.authenticate(token) == null){
            json.put("result", "failed");
            json.put("error", "Permission denied");
            resp.getWriter().println(json.toString());
            return;
        }
        courseService.getAllCourses().forEach((course -> {
            JSONObject jsonCourse = new JSONObject();
            jsonCourse.put("courseName", course.getName());
            jsonCourse.put("courseDescription", course.getDescription());
            jsonCourse.put("courseDuration", course.getDuration());
            jsonArray.put(jsonCourse);
        }));
        json.put("result", "success");
        json.put("courses", jsonArray);
        resp.getWriter().println(json.toString());
    }
}
