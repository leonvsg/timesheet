package ru.leonvsg.education.timesheet.controllers;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.leonvsg.education.timesheet.entities.*;
import ru.leonvsg.education.timesheet.services.GroupService;
import ru.leonvsg.education.timesheet.services.UserService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class GroupController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*resp.setContentType("text/plain;charset=utf-8");
        resp.setCharacterEncoding("UTF-8");
        UserService userService = new UserService();
        GroupService groupService = new GroupService();
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
        Map<Group, Course> groups;
        if (Role.valueOf(user.getRole()) == Role.ADMIN) groups = groupService.getGroups();
        else groups = groupService.getGroups(user);
        groups.forEach((group, course) -> {
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
        resp.getWriter().println(json.toString());*/
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}