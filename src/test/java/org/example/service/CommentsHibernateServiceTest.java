package org.example.service;

import jakarta.persistence.EntityManager;
import org.example.hibernate.entity.Comment;
import org.example.hibernate.repository.CommentHibRepository;
import org.example.hibernate.service.CommentService;
import org.example.hibernate.utils.HibernateUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;

class CommentsHibernateServiceTest {

    private static CommentService commentService;
    private static CommentHibRepository hibRepository;

    @BeforeAll
    static void setUp() {
        EntityManager entityManager = HibernateUtils.getEntityManager();
        hibRepository = new CommentHibRepository(entityManager);
        commentService  = new CommentService(hibRepository);
    }

    @Test
    void savePostShouldReturnPost() {
        Comment comment = Comment.builder()
            .text("Hello World!")
            .author("Kirill")
            .build();

        Comment commentDb = commentService.saveComment(comment);

        assertThat(commentDb).isSameAs(comment);
    }


    @Test
    void getAllPostsShouldReturnListOfPosts() {
        Comment comment1 = Comment.builder()
            .text("Hello World!")
            .author("Kirill")
            .build();
        Comment comment2 = Comment.builder()
            .text("Hello World!")
            .author("Kirill")
            .build();

        hibRepository.save(comment1);
        hibRepository.save(comment2);

        List<Comment> listOfPosts = commentService.getAllComments();
        assertThat(listOfPosts).hasSize(2).containsAll(List.of(comment1, comment2));
    }

    @Test
    void getPostByIdShouldReturnPost() {
        Comment comment = Comment.builder()
            .text("Hello World!")
            .author("Kirill")
            .build();

        hibRepository.save(comment);

        Comment postDb = commentService.getCommentById(comment.getId());
        assertThat(postDb).isSameAs(comment);
    }

    @Test
    void deletePostByIdShouldDeletePostFromDb() {
        Comment comment = Comment.builder()
            .text("Hello World!")
            .author("Kirill")
            .build();

        hibRepository.save(comment);

        commentService.deleteCommentById(comment.getId());

        hibRepository.findById(comment.getId());
        assertThatException().isThrownBy(() -> commentService.getCommentById(comment.getId()));
        assertThat(hibRepository.findById(comment.getId())).isEmpty();
    }
}
