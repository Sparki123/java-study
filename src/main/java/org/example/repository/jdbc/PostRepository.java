package org.example.repository.jdbc;

import org.example.model.entity.PostEntity;
import org.example.util.PgConnectUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostRepository implements CrudRepository<PostEntity, Long> {

    public static final String INSERT_POST_QUERY = "INSERT INTO posts (title, content) VALUES (?, ?)";
    public static final String UPDATE_POST_QUERY = "UPDATE posts SET title = ?, content = ? WHERE id = ?";
    public static final String SELECT_ALL_POSTS = "SELECT * FROM posts";
    private static final String POST_BY_ID_QUERY = "SELECT * FROM posts WHERE id = ?";
    private static final String DELETE_POST_QUERY = "DELETE FROM posts WHERE id = ?";

    @Override
    public List<PostEntity> findAll() {
        List<PostEntity> postEntities = new ArrayList<>();
        try (Connection connection = PgConnectUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_POSTS)) {
            while (resultSet.next()) {
                PostEntity postEntity = PostEntity.builder()
                    .id(resultSet.getLong("id"))
                    .title(resultSet.getString("title"))
                    .content(resultSet.getString("content"))
                    .build();
                postEntities.add(postEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postEntities;
    }

    @Override
    public PostEntity save(final PostEntity postEntity) {
        if (postEntity.getId() == null) {
            return insert(postEntity);
        }

        return update(postEntity);
    }

    private PostEntity insert(final PostEntity postEntity) {
        try (Connection connection = PgConnectUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_POST_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, postEntity.getTitle());
            statement.setString(2, postEntity.getContent());
            statement.executeUpdate();
            connection.commit();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                postEntity.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return postEntity;
    }

    private PostEntity update(final PostEntity postEntity) {
        try (Connection connection = PgConnectUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_POST_QUERY)) {
            statement.setString(1, postEntity.getTitle());
            statement.setString(2, postEntity.getContent());
            statement.setLong(3, postEntity.getId());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postEntity;
    }

    @Override
    public Optional<PostEntity> findById(final Long id) {
        try (Connection connection = PgConnectUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(POST_BY_ID_QUERY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(
                    PostEntity.builder()
                        .id(resultSet.getLong("id"))
                        .title(resultSet.getString("title"))
                        .content(resultSet.getString("content"))
                        .build());
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Connection connection = PgConnectUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_POST_QUERY)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll() {
        try (final Connection connection = PgConnectUtil.getConnection();
             final Statement statement = connection.createStatement()) {
            final String deleteComments = "DELETE FROM posts";
            statement.executeUpdate(deleteComments);

            connection.commit();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
