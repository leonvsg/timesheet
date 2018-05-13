package ru.leonvsg.education.timesheet.connections;

import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.Settings;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnectionManager implements ConnectionManager {

    private final static Logger LOGGER = Logger.getLogger(JDBCConnectionManager.class);

    private JDBCConnectionManager(){ }

    private static ConnectionManager connectionManager;

    public static ConnectionManager getInstance(){
        if (connectionManager == null){
            connectionManager = new JDBCConnectionManager();
            LOGGER.debug("Connection manager was created");
        }
        return connectionManager;
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            LOGGER.debug("Try connect to DB driver. Driver name: " + Settings.DB_DRIVER_NAME);
            Class.forName(Settings.DB_DRIVER_NAME);
            LOGGER.debug("Try to get connection");
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
