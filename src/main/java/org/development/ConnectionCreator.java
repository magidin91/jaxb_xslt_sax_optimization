package org.development;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * The class is responsible for creating a connection to the database
 */
public class ConnectionCreator {
    /**
     * Сreates a connection to the database
     */
    public Connection init() {
        try (InputStream in = ConnectionCreator.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            return DriverManager.getConnection(config.getProperty("url"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
