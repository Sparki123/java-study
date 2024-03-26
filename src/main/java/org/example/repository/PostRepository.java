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

public class PostRepository implements CrudRepository<Post, Long> {

    public static final String INSERT_POST_QUERY = "INSERT INTO posts (title, content) VALUES (?, ?)";
    public static final String UPDATE_POST_QUERY = "UPDATE posts SET title = ?, content = ? WHERE id = ?";
    public static final String SELECT_ALL_POSTS = "SELECT * FROM posts";
    public static final String SELECT_POST_WITH_COMMENTS = "SELECT * FROM posts LEFT JOIN comments ON posts.id = comments.post_id  WHERE posts.id = ?";
    private static final String POST_BY_ID_QUERY = "SELECT * FROM posts WHERE post_id = ?";
    private static final String DELETE_POST_QUERY = "DELETE FROM posts WHERE id = ?";

    @Override
    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection connection = PgConnectUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_POSTS)) {
            while (resultSet.next()) {
                Post post = Post.builder()
                    .id(resultSet.getLong("id"))
                    .title(resultSet.getString("title"))
                    .content(resultSet.getString("content"))
                    .build();
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post save(final Post post) {
        if (post.getId() == null) {
            return insert(post);
        }

        return update(post);
    }

    private Post insert(final Post post) {
        try (Connection connection = PgConnectUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_POST_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getContent());
            statement.executeUpdate();
            connection.commit();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                post.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return post;
    }

    private Post update(final Post post) {
        try (Connection connection = PgConnectUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_POST_QUERY)) {
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getContent());
            statement.setLong(3, post.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public Optional<Post> findById(final Long id) {
        try (Connection connection = PgConnectUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(POST_BY_ID_QUERY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(
                    Post.builder()
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Post> getPostWithComments(final Long postId) {
        try (Connection connection = PgConnectUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_POST_WITH_COMMENTS)) {
            statement.setLong(1, postId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }

                final Post post = Post.builder()
                    .id(resultSet.getLong("post_id"))
                    .content(resultSet.getString("content"))
                    .title(resultSet.getString("title"))
                    .build();

                while (resultSet.next()) {
                    final Comment comment = Comment.builder()
                        .id(resultSet.getLong(5))
                        .comment(resultSet.getString("comment"))
                        .author(resultSet.getString("author"))
                        .postId(post.getId())
                        .build();

                    post.getComments().add(comment);
                }
                return Optional.of(post);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
