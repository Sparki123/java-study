package org.example.jdbc.repository;

import org.example.jdbc.model.entity.CommentEntity;
import org.example.jdbc.util.PgConnectUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentRepository implements CrudRepository<CommentEntity, Long> {

    public static final String SELECT_ALL_COMMENTS = "SELECT * FROM comments";
    private static final String INSERT_COMMENT_QUERY = "INSERT INTO comments (author, comment, post_id) VALUES (?, ?, ?)";
    private static final String UPDATE_COMMENT_QUERY = "UPDATE comments SET author = ?, comment = ? WHERE id = ?";
    private static final String DELETE_COMMENT_QUERY = "DELETE FROM comments WHERE id = ?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM comments WHERE id = ?";
    private static final String FIND_BY_POST_ID_QUERY = "SELECT * FROM comments WHERE post_id = ?";

    @Override
    public List<CommentEntity> findAll() {
        final List<CommentEntity> commentEntities = new ArrayList<>();
        try (Connection connection = PgConnectUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_COMMENTS)) {
            while (resultSet.next()) {
                final CommentEntity commentEntity = CommentEntity.builder()
                    .id(resultSet.getLong("id"))
                    .author(resultSet.getString("author"))
                    .text(resultSet.getString("comment"))
                    .postId(resultSet.getLong("post_id"))
                    .build();
                commentEntities.add(commentEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commentEntities;
    }

    @Override
    public CommentEntity save(final CommentEntity commentEntity) {
        if (commentEntity.getId() == null) {
            return insert(commentEntity);
        } else {
            return update(commentEntity);
        }
    }

    @Override
    public Optional<CommentEntity> findById(final Long id) {
        try (Connection connection = PgConnectUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    final CommentEntity commentEntity = CommentEntity.builder()
                        .id(resultSet.getLong("id"))
                        .author(resultSet.getString("author"))
                        .text(resultSet.getString("comment"))
                        .postId(resultSet.getLong("post_id"))
                        .build();
                    return Optional.of(commentEntity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<CommentEntity> findByPostId(final Long id) {
        final List<CommentEntity> commentEntities = new ArrayList<>();
        try (Connection connection = PgConnectUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_POST_ID_QUERY)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    final CommentEntity commentEntity = CommentEntity.builder()
                        .id(resultSet.getLong("id"))
                        .author(resultSet.getString("author"))
                        .text(resultSet.getString("comment"))
                        .postId(resultSet.getLong("post_id"))
                        .build();
                    commentEntities.add(commentEntity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commentEntities;
    }

    @Override
    public void deleteById(final Long id) {
        try (Connection connection = PgConnectUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_COMMENT_QUERY)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private CommentEntity insert(final CommentEntity commentEntity) {
        try (Connection connection = PgConnectUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_COMMENT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, commentEntity.getAuthor());
            statement.setString(2, commentEntity.getText());
            statement.setLong(3, commentEntity.getPostId());
            statement.executeUpdate();
            connection.commit();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    commentEntity.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Ошибка при получении сгенерированного ключа.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commentEntity;
    }

    private CommentEntity update(final CommentEntity commentEntity) {
        try (Connection connection = PgConnectUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_COMMENT_QUERY)) {
            statement.setString(1, commentEntity.getAuthor());
            statement.setString(2, commentEntity.getText());
            statement.setLong(3, commentEntity.getId());

            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commentEntity;
    }

    public void deleteAll() {
        try (Connection connection = PgConnectUtil.getConnection();
             Statement statement = connection.createStatement()) {
            final String deleteComments = "DELETE FROM comments";
            statement.executeUpdate(deleteComments);

            connection.commit();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
