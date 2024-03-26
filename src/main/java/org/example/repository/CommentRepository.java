package org.example.repository;

import org.example.model.entity.Comment;
import org.example.model.entity.Post;
import org.example.util.PgConnectUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.DriverManager.getConnection;

public class CommentRepository implements CrudRepository<Comment, Long> {

    private static final String INSERT_COMMENT_QUERY = "INSERT INTO comments (author, comment) VALUES (?, ?)";
    private static final String UPDATE_COMMENT_QUERY = "UPDATE comments SET author = ?, comment = ? WHERE id = ?";
    private static final String DELETE_COMMENT_QUERY = "DELETE FROM comments WHERE id = ?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM comments WHERE id = ?";

    public static final String SELECT_ALL_COMMENTS = "SELECT * FROM comments";

    @Override
    public List<Comment> findAll() {
        List<Comment> comments = new ArrayList<>();
        try (Connection connection = PgConnectUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_COMMENTS)) {
            while (resultSet.next()) {
                Comment comment = Comment.builder()
                    .id(resultSet.getLong("id"))
                    .author(resultSet.getString("author"))
                    .comment(resultSet.getString("comment"))
                    .build();
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    @Override
    public Comment save(final Comment comment) {
        if (comment.getId() == null) {
            return insert(comment);
        } else {
            return update(comment);
        }
    }

    @Override
    public Optional<Comment> findById(Long id) {
        try (Connection connection = PgConnectUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Comment comment = Comment.builder()
                        .id(resultSet.getLong("id"))
                        .author(resultSet.getString("author"))
                        .comment(resultSet.getString("comment"))
                        .build();
                    return Optional.of(comment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {
        try (Connection connection = PgConnectUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_COMMENT_QUERY)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Comment insert(final Comment comment) {
        try (Connection connection = PgConnectUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_COMMENT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, comment.getAuthor());
            statement.setString(2, comment.getComment());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    comment.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Ошибка при получении сгенерированного ключа.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comment;
    }

    private Comment update(final Comment comment) {
        try (Connection connection = PgConnectUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_COMMENT_QUERY)) {
            statement.setString(1, comment.getAuthor());
            statement.setString(2, comment.getComment());
            statement.setLong(3, comment.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comment;
    }
}
