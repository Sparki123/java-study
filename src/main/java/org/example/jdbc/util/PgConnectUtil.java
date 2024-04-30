package org.example.jdbc.util;

import lombok.experimental.UtilityClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@UtilityClass
public class PgConnectUtil {
    private static final String URL = "jdbc:postgresql://localhost:5432/study-habr";
    private static final String USER_NAME = "user";
    private static final String PASSWORD = "user";

    public static Connection getConnection() {
        try {
            final Connection connection = DriverManager.getConnection(URL, getProperties());
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Connection to database failed", e);
        }
    }

    private static Properties getProperties() {
        final Properties props = new Properties();
        props.setProperty("user", USER_NAME);
        props.setProperty("password", PASSWORD);
        return props;
    }
}
