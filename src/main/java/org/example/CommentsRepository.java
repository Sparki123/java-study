package org.example;

import org.example.model.Comment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;


public class CommentsRepository {

    private static final String url = "jdbc:postgresql://localhost:5432/study-habr";
    private static final String username = "user";
    private static final String password = "user";
    public static void addCommentToPost(int postId, String author, String comment) {
        try (Connection connection = getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO comments (post_id, author, comment) VALUES (?, ?, ?)")) {
            statement.setInt(1, postId);
            statement.setString(2, author);
            statement.setString(3, comment);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateComment(int commentId, String updatedComment) {
        try (Connection connection = getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("UPDATE comments SET comment=? WHERE comment_id=?")) {
            statement.setString(1, updatedComment);
            statement.setInt(2, commentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteComment(int commentId) {
        try (Connection connection = getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM comments WHERE comment_id=?")) {
            statement.setInt(1, commentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Comment> getComments(int postId) {
        List<Comment> comments = new ArrayList<>();
        try (Connection connection = getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM comments WHERE post_id = ?")) {
            statement.setInt(1, postId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Comment comment = new Comment(resultSet.getInt("comment_id"), resultSet.getString("author"), resultSet.getString("comment"));
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }
}
