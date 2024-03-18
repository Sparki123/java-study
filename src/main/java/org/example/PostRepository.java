package org.example;

import org.example.model.Comment;
import org.example.model.Post;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class PostRepository {

    private static final String url = "jdbc:postgresql://localhost:5432/study-habr";
    private static final String username = "user";
    private static final String password = "user";
    public static List<Post> getPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection connection = getConnection(url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM posts")) {
            while (resultSet.next()) {
                Post post = new Post(resultSet.getInt("post_id"), resultSet.getString("title"), resultSet.getString("content"));
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public static int addPost(String title, String content) {
        int postId = -1;
        try (Connection connection = getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO posts (title, content) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, title);
            statement.setString(2, content);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                postId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postId;
    }

    public static void updatePost(int postId, String newTitle, String newContent) {
        try (Connection connection = getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("UPDATE posts SET title=?, content=? WHERE post_id=?")) {
            statement.setString(1, newTitle);
            statement.setString(2, newContent);
            statement.setInt(3, postId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Post getPostById(int postId) {
        Post post = null;
        try (Connection connection = getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM posts WHERE post_id = ?")) {
            statement.setInt(1, postId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                post = new Post(resultSet.getInt("post_id"), resultSet.getString("title"), resultSet.getString("content"));
            } else {
                System.out.println("Поста по ID: " + postId + " нет");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    public static Post getPostWithComments(int postId) {
        Post post = getPostById(postId);
        if (post != null) {
            try (Connection connection = getConnection(url, username, password);
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM comments WHERE post_id = ?")) {
                statement.setInt(1, postId);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Comment comment = new Comment(resultSet.getInt("comment_id"), resultSet.getString("author"), resultSet.getString("comment"));
                    post.addComment(comment);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return post;
    }
}
