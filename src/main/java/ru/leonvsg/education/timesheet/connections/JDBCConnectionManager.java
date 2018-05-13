package ru.leonvsg.education.timesheet.connections;

import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.Settings;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnectionManager implements ConnectionManager {

    private static final Logger LOGGER = Logger.getLogger(JDBCConnectionManager.class);
    private static ConnectionManager connectionManager;

    private JDBCConnectionManager(){ }

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
            LOGGER.error(e);
        }
        return connection;
    }
}
