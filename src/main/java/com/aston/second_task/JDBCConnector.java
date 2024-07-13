package com.aston.second_task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCConnector { // допускает создание только одного соединения за счёт static
    private static final Logger LOGGER = LoggerFactory.getLogger(JDBCConnector.class);
    private static final String PROPERTIES_FILE = "database.properties";
    private static String url;
    private static String user;
    private static String password;
    private static String dbName;

    static {
        try (InputStream input = JDBCConnector.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            Properties properties = new Properties();
            if (input == null) {
                LOGGER.error("Sorry, unable to find " + PROPERTIES_FILE);
            }
            properties.load(input);

            url = properties.getProperty("db.url");
            user = properties.getProperty("db.user");
            password = properties.getProperty("db.password");
            dbName = properties.getProperty("db.name");
        } catch (Exception ex) {
            LOGGER.error("Error loading properties file", ex);
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url + dbName, user, password);
            LOGGER.info("Succesfully connected to DB!");
        } catch (SQLException sqlException) {
            LOGGER.error("Failed to create or connect to the database.", sqlException);
        }
        return connection;
    }


}