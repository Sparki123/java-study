package org.example.repository;

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
    public static final String SELECT_ALL_POSTS = "SELECT * FROM posts";
    private static final String POST_BY_ID_QUERY = "SELECT * FROM posts WHERE post_id = ?";

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

    //todo: save and post in one method
    @Override
    public Post save(final Post post) {
        try (Connection connection = PgConnectUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_POST_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getContent());
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                post.setId(rs.getLong(1));
            }
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {
        //todo: implement
    }

    //todo: refactor with join
//    public Post getPostWithComments(Long postId) {
//        Post post = findById(postId);
//        if (post != null) {
//            try (Connection connection = PgConnectUtil.getConnection();
//                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM comments WHERE post_id = ?")) {
//                statement.setInt(1, postId);
//                ResultSet resultSet = statement.executeQuery();
//                while (resultSet.next()) {
//                    Comment comment = new Comment(resultSet.getInt("comment_id"), resultSet.getString("author"), resultSet.getString("comment"));
//                    post.addComment(comment);
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return post;
//    }
}
