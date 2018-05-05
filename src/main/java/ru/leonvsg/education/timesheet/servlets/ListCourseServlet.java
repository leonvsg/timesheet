package ru.leonvsg.education.timesheet.servlets;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.leonvsg.education.timesheet.entities.User;
import ru.leonvsg.education.timesheet.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ListCourseServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain;charset=utf-8");
        resp.setCharacterEncoding("UTF-8");
        UserService userService = new UserService();
        String token = req.getParameter("token");
        JSONObject json = new JSONObject();
        if (token == null){
            json.put("result", "failed");
            json.put("error", "Permission denied");
            resp.getWriter().println(json.toString());
            return;
        }
        User user = userService.authenticate(token);
        if (user == null){
            json.put("result", "failed");
            json.put("error", "Invalid token");
            resp.getWriter().println(json.toString());
            return;
        }
        JSONArray jsonArray = new JSONArray();
        userService.getCourses(user).forEach((group, course) -> {
            JSONObject jsonCourse = new JSONObject();
            jsonCourse.put("groupName", group.getName());
            jsonCourse.put("startDate", group.getStartDate());
            jsonCourse.put("expDate", group.getExpDate());
            jsonCourse.put("groupDescription", group.getDescription());
            jsonCourse.put("courseName", course.getName());
            jsonCourse.put("courseDescription", course.getDescription());
            jsonCourse.put("courseDuration", course.getDuration());
            jsonArray.put(jsonCourse);
        });
        json.put("result", "success");
        json.put("groups", jsonArray);
        resp.getWriter().println(json.toString());
    }
}
