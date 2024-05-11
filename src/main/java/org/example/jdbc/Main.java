package org.example.jdbc;

import org.example.jdbc.util.PgConnectUtil;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure();
    }

    public static void init() {
        try (Connection connection = PgConnectUtil.getConnection();
             Statement statement = connection.createStatement()) {
            final String dropCommentTable = "DROP TABLE IF EXISTS comments;";
            statement.execute(dropCommentTable);

            final String dropPostTable = "DROP TABLE IF EXISTS posts;";
            statement.execute(dropPostTable);

            final String createPostTable = """
                                    CREATE TABLE IF NOT EXISTS posts
                                    (
                                        id         SERIAL PRIMARY KEY,
                                        title      VARCHAR(255),
                                        content    TEXT,
                                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                                    );
                """;
            statement.execute(createPostTable);

            final String createCommentTable = """
                                    CREATE TABLE IF NOT EXISTS comments
                                    (
                                        id         SERIAL PRIMARY KEY,
                                        post_id    INT REFERENCES posts (id) ON DELETE CASCADE,
                                        author     VARCHAR(100),
                                        comment    TEXT,
                                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                                    );
                """;
            statement.execute(createCommentTable);
            connection.commit();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
