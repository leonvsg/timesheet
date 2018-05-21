package ru.leonvsg.education.timesheet.services.context;

import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.entities.*;
import ru.leonvsg.education.timesheet.services.ServiceFactory;
import ru.leonvsg.education.timesheet.services.Utils;
import ru.leonvsg.education.timesheet.services.entity.*;
import ru.leonvsg.education.timesheet.services.verification.Checker;
import ru.leonvsg.education.timesheet.services.verification.Handler;

import java.util.ArrayList;
import java.util.List;

public class ViewContextService {

    private static final Logger LOGGER = Logger.getLogger(ViewContextService.class);
    private final GroupService groupService;
    private final UserService userService;
    private final LessonService lessonService;
    private final RatingService ratingService;
    private final CourseService courseService;

    public ViewContextService() {
        this(EntityServiceFactory.getInstance());
    }

    public ViewContextService(ServiceFactory serviceFactory) {
        groupService = serviceFactory.getService(Group.class);
        userService = serviceFactory.getService(User.class);
        lessonService = serviceFactory.getService(Lesson.class);
        ratingService = serviceFactory.getService(Rating.class);
        courseService = serviceFactory.getService(Course.class);
    }

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
        return builder.getResult();
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
            return contextBuilder.getResult();
        }
        contextBuilder.setErrorMessage("Found course: " + course.toString());
        List<Group> groups = groupService.getGroups(course);
        contextBuilder.setGroups(groups);
        contextBuilder.setCourse(course);
        return contextBuilder.getResult();
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
        return contextBuilder.getResult();
    }

    public ViewContext postCourseViewContext(String token, String name, String description, Integer duration){
        ContextBuilder contextBuilder = new ViewContextBuilder();
        if (userService.verifyRole(token) != Role.ADMIN){
            contextBuilder.setErrorMessage("InvalidPermission");
            return contextBuilder.getResult();
        }
        if (name == null || name.isEmpty()){
            contextBuilder.setErrorMessage("InvalidCourseName");
            return contextBuilder.getResult();
        }
        if (duration <= 0){
            contextBuilder.setErrorMessage("InvalidDuration");
            return contextBuilder.getResult();
        }
        if (courseService.addCourse(name, description, duration)){
            contextBuilder.setErrorMessage("Success");
            return contextBuilder.getResult();
        }
        else {
            contextBuilder.setErrorMessage("WTF???");
            return contextBuilder.getResult();
        }
    }

    public ViewContext getUserViewContext(String token, String id){
        ContextBuilder builder = new ViewContextBuilder();
        if (id == null || token == null) {
            builder.setErrorMessage("ID or token is null.");
            return builder.getResult();
        }
        User user = null;
        if (userService.verifyRole(token) == Role.ADMIN){
            try{
                user = userService.getUser(Integer.valueOf(id));
            }catch (Exception e){
                LOGGER.error(e.getMessage(), e);
            }
        } else {
            user = userService.authenticate(token);
        }
        List<Group> groups = groupService.getGroups(user);
        if (user != null && user.getId().toString().equals(id)){
            builder.setUser(user);
            builder.setGroups(groups);
        } else {
            builder.setErrorMessage("User with id=" + id + " not found, or access denied");
        }
        return builder.getResult();
    }

    public ViewContext postUserViewContext(String login, String password, String id, String name,
                                           String middlename, String surname, String token){
        ContextBuilder builder = new ViewContextBuilder();
        Handler handler = new Handler(
                ()->{
                    if (Utils.isEmptyParams(login, password, name, middlename, surname)){
                        builder.setErrorMessage("NothingToChange");
                        return false;
                    }
                    return true;
                }
        );
        handler.setNextHandler(new Handler(
                ()->{
                    if (id.isEmpty() || token == null) {
                        builder.setErrorMessage("InvalidUserId");
                        return false;
                    }
                    return true;
                }
        )).setNextHandler(new Handler(
                ()->{
                    if (!password.isEmpty() && !userService.isValidPassword(password)){
                        builder.setErrorMessage("InvalidPassword");
                        return false;
                    }
                    return true;
                }
        )).setNextHandler(new Handler(
                ()->{
                    if (!login.isEmpty() && !userService.isValidLogin(login)){
                        builder.setErrorMessage("InvalidLogin");
                        return false;
                    }
                    return true;
                }
        )).setNextHandler(new Handler(
                ()->{
                    if (!login.isEmpty() && userService.isBusyLogin(login)){
                        builder.setErrorMessage("LoginIsBusy");
                        return false;
                    }
                    return true;
                }
        ));
        if (handler.verify()){
            try {
                User user = userService.verifyRole(token) == Role.ADMIN ?
                        userService.getUser(Integer.valueOf(id)) : userService.authenticate(token);
                LOGGER.info("User update from: " + user.toString());
                if (!login.isEmpty()) user.setLogin(login);
                if (!password.isEmpty()) user.setPassword(userService.getHashedPassword(password));
                if (!name.isEmpty()) user.setName(name);
                if (!middlename.isEmpty()) user.setMiddleName(middlename);
                if (!surname.isEmpty()) user.setSurname(surname);
                LOGGER.info("User update to: " + user.toString());
                if (userService.updateUser(user)){
                    builder.setErrorMessage("Success");
                } else {
                    builder.setErrorMessage("WTF???");
                }
            } catch (Exception e){
                LOGGER.error(e.getMessage(), e);
                builder.setErrorMessage("WTF???");
            }
        }
        return builder.getResult();
    }

    public ViewContext getUsersViewContext(String token){
        ContextBuilder builder = new ViewContextBuilder();
        if (userService.verifyRole(token) == Role.ADMIN){
            builder.setUsers(userService.getAllUsers());
        } else {
            builder.setErrorMessage("User is not ADMIN.");
        }
        return builder.getResult();
    }

    public ViewContext postUsersViewContext(String login, String password, String role, String name,
                                            String middlename, String surname, String token){
        ContextBuilder builder = new ViewContextBuilder();

        Handler handler = new Handler(
                ()->{
                    if (userService.verifyRole(token) != Role.ADMIN){
                        builder.setErrorMessage("PermissionDenied");
                        return false;
                    }
                    return true;
                }
        );
        handler.setNextHandler(new Handler(
                ()->{
                    if (!userService.isValidPassword(password)) {
                        builder.setErrorMessage("InvalidPassword");
                        return false;
                    }
                    return true;
                }
        )).setNextHandler(new Handler(
                ()->{
                    if (!userService.isValidRole(role)){
                        builder.setErrorMessage("InvalidRole");
                        return false;
                    }
                    return true;
                }
        )).setNextHandler(new Handler(
                ()->{
                    if (!userService.isValidLogin(login)){
                        builder.setErrorMessage("InvalidLogin");
                        return false;
                    }
                    return true;
                }
        )).setNextHandler(new Handler(
                ()->{
                    if (userService.isBusyLogin(login)){
                        builder.setErrorMessage("LoginIsBusy");
                        return false;
                    }
                    return true;
                }
        )).setNextHandler(new Handler(
                ()->{
                    if (!userService.register(login, password, role, name, middlename, surname)){
                        builder.setErrorMessage("WTF???");
                        return false;
                    }
                    return false;
                }
        ));
        if (handler.verify()){
            builder.setErrorMessage("Success");
        }
        return builder.getResult();
    }
}