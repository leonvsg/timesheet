package ru.leonvsg.education.timesheet.services.context;

import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.entities.*;
import ru.leonvsg.education.timesheet.services.EntityServices.*;

import java.util.ArrayList;
import java.util.List;

public class ContextDirector {

    private static final Logger LOGGER = Logger.getLogger(ContextDirector.class);
    private final GroupService groupService = new GroupService();
    private final UserService userService = new UserService();
    private final LessonService lessonService = new LessonService();
    private final RatingService ratingService = new RatingService();
    private final CourseService courseService = new CourseService();

    public ViewContext getRatingViewContext(User user, Integer id){
        List<Lesson> lessons = new ArrayList<>();
        List<User> users =  new ArrayList<>();
        List<Rating> ratings = new ArrayList<>();
        List<Group> groups = groupService.getGroups(user);
        if (id != null){
            lessons = lessonService.getLessons(id);
            if (!user.getRole().equalsIgnoreCase("STUDENT")) users = userService.getUsersByGroup(id);
            else users.add(user);
            ratings = ratingService.getRatingsByGroup(id);
        }
        ContextBuilder builder = new ViewContextBuilder();
        builder.setLessons(lessons);
        builder.setUsers(users);
        builder.setRating(ratings);
        builder.setGroups(groups);
        return builder.gotResult();
    }

    public ViewContext getCourseViewContextByCourseId(String id){
        ContextBuilder contextBuilder = new ViewContextBuilder();
        Course course = null;
        try {
            course = courseService.getCourseById(Integer.valueOf(id));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }
        if (course == null) {
            contextBuilder.setErrorMessage("Course with id=" + id + " not found.");
            return contextBuilder.gotResult();
        }
        contextBuilder.setErrorMessage("Found course: " + course.toString());
        List<Group> groups = groupService.getGroups(course);
        contextBuilder.setGroups(groups);
        contextBuilder.setCourse(course);
        return contextBuilder.gotResult();
    }

    public ViewContext getCourseViewContext(User user, String display){
        ContextBuilder contextBuilder = new ViewContextBuilder();
        List<Course> courses;
        if (display != null && !user.getRole().equalsIgnoreCase("ADMIN") && display.equals("my")){
            courses = courseService.getCoursesByUser(user);
            contextBuilder.setErrorMessage("Show courses by user with id=" + user.getId());
        }
        else {
            courses = courseService.getAllCourses();
            contextBuilder.setErrorMessage("Show all courses");
        }
        contextBuilder.setCourses(courses);
        contextBuilder.setUser(user);
        return contextBuilder.gotResult();
    }

    public ViewContext postCourseViewContext(String token, String name, String description, Integer duration){
        ContextBuilder contextBuilder = new ViewContextBuilder();
        if (userService.verifyRole(token) != Role.ADMIN){
            contextBuilder.setErrorMessage("InvalidPermission");
            return contextBuilder.gotResult();
        }
        if (name == null || name.isEmpty()){
            contextBuilder.setErrorMessage("InvalidCourseName");
            return contextBuilder.gotResult();
        }
        if (duration <= 0){
            contextBuilder.setErrorMessage("InvalidDuration");
            return contextBuilder.gotResult();
        }
        if (courseService.addCourse(name, description, duration)){
            contextBuilder.setErrorMessage("Success");
            return contextBuilder.gotResult();
        }
        else {
            contextBuilder.setErrorMessage("WTF???");
            return contextBuilder.gotResult();
        }
    }
}
