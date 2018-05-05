package ru.leonvsg.education.timesheet.connections;

import java.sql.Connection;

public interface ConnectionManager {

    Connection getConnection();
}
