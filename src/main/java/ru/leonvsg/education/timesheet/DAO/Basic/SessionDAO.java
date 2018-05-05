package ru.education.timesheet.DAO.Basic;

import ru.education.timesheet.Entities.Session;
import ru.education.timesheet.Entities.User;

public interface SessionDAO extends DAO<Session, String> {
    User getUser(Session session);
}
