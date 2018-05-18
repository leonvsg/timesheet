package ru.leonvsg.education.timesheet.dao.basic;

import java.sql.SQLException;

public class EntityPersistanceException extends SQLException {

    public EntityPersistanceException(String message) {
        super(message);
    }
}
