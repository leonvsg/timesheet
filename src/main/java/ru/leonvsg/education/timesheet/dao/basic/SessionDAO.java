package ru.leonvsg.education.timesheet.dao.basic;

import ru.leonvsg.education.timesheet.entities.*;

public interface SessionDAO extends DAO<Session, String> {
    User getUser(Session session);
}
