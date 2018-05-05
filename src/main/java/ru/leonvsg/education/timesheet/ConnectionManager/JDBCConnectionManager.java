package ru.education.timesheet.ConnectionManager;

import ru.education.timesheet.Settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnectionManager implements ConnectionManager {

    private JDBCConnectionManager(){ }

    private static ConnectionManager connectionManager;

    public static ConnectionManager getInstance(){
        if (connectionManager == null){
            connectionManager = new JDBCConnectionManager();
        }
        return connectionManager;
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(Settings.DB_DRIVER_NAME);
            connection = DriverManager.getConnection(
                    Settings.DB_CONNECTION_URL,
                    Settings.DB_CONNECTION_USERNAME,
                    Settings.DB_CONNECTION_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(System.out);
        }
        return connection;
    }
}
