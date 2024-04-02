package org.example;

import org.example.model.entity.Comment;
import org.example.model.entity.Post;
import org.example.repository.CommentRepository;
import org.example.repository.PostRepository;
import org.example.service.PostService;
import org.example.util.PgConnectUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

//todo: junit 5 with databases
//todo: map struct -- DONE

//todo: jacoco для подсчета покрытия
//todo: spotbugs-maven-plugin

//todo: maven, docker

public class Main {
    public static void main(final String[] args) {
        init();

        final CommentRepository commentRepository = new CommentRepository();
        final PostRepository postRepository = new PostRepository();

        final PostService postService = new PostService(postRepository);

        final Post post1 = Post.builder()
            .title("Title")
            .content("Content")
            .build();
        final Post post2 = Post.builder()
            .title("Title")
            .content("Content")
            .build();

        postService.save(post1);
        postService.save(post2);

        commentRepository.save(Comment.builder()
            .author("Author")
            .text("Comment")
            .postId(post1.getId())
            .build());
        commentRepository.save(Comment.builder()
            .author("Author")
            .text("Comment")
            .postId(post1.getId())
            .build());
        final Post postWithComments = postService.getPostWithComments(1L);

        System.out.println(postWithComments);
    }

    private static void init() {
        try (final Connection connection = PgConnectUtil.getConnection();
             final Statement statement = connection.createStatement()) {
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
