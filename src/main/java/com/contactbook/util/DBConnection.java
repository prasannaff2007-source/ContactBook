package com.contactbook.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Creates JDBC connections to MySQL.
 *
 * Settings are read from:
 *   1. Windows environment variables CONTACTBOOK_DB_USER / CONTACTBOOK_DB_PASSWORD (if set), otherwise
 *   2. the file src/main/resources/db.properties
 * so the password is never written inside the Java code.
 */
public class DBConnection {

    private static final String PLACEHOLDER = "PUT_YOUR_MYSQL_PASSWORD_HERE";
    private static final Properties PROPS = loadProperties();

    private DBConnection() {
    }

    private static Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream in = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in != null) {
                props.load(in);
            }
        } catch (IOException e) {
            // Ignore: getConnection() will report missing settings clearly.
        }
        return props;
    }

    private static String setting(String envName, String propName, String defaultValue) {
        String value = System.getenv(envName);
        if (value == null || value.isBlank()) {
            value = PROPS.getProperty(propName);
        }
        if (value == null || value.isBlank()) {
            value = defaultValue;
        }
        return value == null ? null : value.trim();
    }

    public static Connection getConnection() throws SQLException {
        String url = PROPS.getProperty("db.url",
                "jdbc:mysql://localhost:3306/contactbook?serverTimezone=Asia/Kolkata&characterEncoding=UTF-8");
        String user = setting("CONTACTBOOK_DB_USER", "db.user", "root");
        String password = setting("CONTACTBOOK_DB_PASSWORD", "db.password", null);

        if (password == null || password.isEmpty() || PLACEHOLDER.equals(password)) {
            throw new SQLException("Database password is not configured. "
                    + "Set db.password in db.properties or the CONTACTBOOK_DB_PASSWORD environment variable.");
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC driver was not found.", e);
        }

        return DriverManager.getConnection(url, user, password);
    }
}
