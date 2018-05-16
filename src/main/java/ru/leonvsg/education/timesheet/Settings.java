package ru.leonvsg.education.timesheet;

public class Settings {

    private Settings() { }

    public static final String DB_DRIVER_NAME = "org.postgresql.Driver";
    public static final String DB_CONNECTION_URL = "jdbc:postgresql://educationpsqldb.postgres.database.azure.com:5432/education";
    public static final String DB_CONNECTION_USERNAME = "timesheet@educationpsqldb";
    public static final String DB_CONNECTION_PASSWORD = "timesheet";
    public static final String VALID_LOGIN_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
}
