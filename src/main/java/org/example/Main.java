package org.example;

import org.example.util.PgConnectUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(final String[] args) {
        init();
    }

    public static void init() {
        try (Connection connection = PgConnectUtil.getConnection();
             Statement statement = connection.createStatement()) {
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
                                        post_id    INT REFERENCES postEntities (id) ON DELETE CASCADE,
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
