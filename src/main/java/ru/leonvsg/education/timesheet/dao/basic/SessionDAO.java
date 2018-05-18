package ru.leonvsg.education.timesheet.dao.basic;

import ru.leonvsg.education.timesheet.entities.*;
import java.util.List;

public interface SessionDAO extends DAO<Session, String> {

    List<Session> getSessionsByUser(User user);

    List<Session> getSessionsByUser(Integer userId);
}
