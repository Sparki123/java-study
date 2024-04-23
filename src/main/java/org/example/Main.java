package org.example;

import org.example.model.hiberentity.Post;
import org.example.util.PgConnectUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure();

        try(SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Post post = Post.builder()
                    .title("hello_200")
                    .content("hello_200")
                    .build();
            session.persist(post);
            session.getTransaction().commit();
        }
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
