package ru.leonvsg.education.timesheet;

public class Settings {

    private Settings() { }

    public final static String DB_DRIVER_NAME = "org.postgresql.Driver";
    public final static String DB_CONNECTION_URL = "jdbc:postgresql://localhost:5432/education";
    public final static String DB_CONNECTION_USERNAME = "postgres";
    public final static String DB_CONNECTION_PASSWORD = "8497692";

    public final static String VALID_LOGIN_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
}
