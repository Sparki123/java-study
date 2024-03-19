package org.example.util;

import lombok.experimental.UtilityClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@UtilityClass
public class PgConnectUtil {
    private static final String url = "jdbc:postgresql://localhost:5432/study-habr";
    private static final String username = "user";
    private static final String password = "user";

    public static Connection getConnection() {
        try {
            final Connection connection = DriverManager.getConnection(url, getProperties());
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Connection to database failed", e);
        }
    }

    private static Properties getProperties() {
        Properties props = new Properties();
        props.setProperty("user", username);
        props.setProperty("password", password);
        return props;
    }
}
