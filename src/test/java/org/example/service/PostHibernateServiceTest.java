package org.example.service;

import jakarta.persistence.EntityManager;
import org.example.hibernate.entity.Comment;
import org.example.hibernate.entity.Post;
import org.example.hibernate.entity.User;
import org.example.hibernate.repository.PostHibRepository;
import org.example.hibernate.repository.UserHibernateRepository;
import org.example.hibernate.service.PostService;
import org.example.hibernate.utils.HibernateUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;

class PostHibernateServiceTest {

    private static PostService postService;
    private static PostHibRepository hibRepository;
    private static UserHibernateRepository userHibRepository;

    @BeforeAll
    static void setUp() {
        EntityManager entityManager = HibernateUtils.getEntityManager();
        hibRepository = new PostHibRepository(entityManager);
        userHibRepository = new UserHibernateRepository(entityManager);
        postService = new PostService(hibRepository);
    }

    @Test
    void savePostShouldReturnPost() {
        Post post = Post.builder()
            .content("hello World!")
            .titleText("hello world!")
            .build();

        Post postDb = postService.savePost(post);

        assertThat(postDb).isSameAs(post);
    }

    @Test
    void savePostWithCommentsShouldReturnPostWithComments() {
        User user = User.builder()
            .userName("HelloWorld")
            .email("test@mail.com")
            .build();
        userHibRepository.save(user);

        Comment comment = Comment.builder()
            .text("Hello World!")
            .author("Kirill")
            .build();

        Post post = Post.builder()
            .content("hello World!")
            .titleText("hello world!")
            .user(user)
            .build()
            .withComment(comment);

        user.withPost(post);

        Post postDb = postService.savePost(post);

        assertThat(postDb.getComments().get(0)).isSameAs(comment);
    }

    @Test
    void getAllPostsShouldReturnListOfPosts() {
        Post post1 = Post.builder()
            .content("hello World!")
            .titleText("hello world!")
            .build();
        Post post2 = Post.builder()
            .content("hello World!")
            .titleText("hello world!")
            .build();

        hibRepository.save(post1);
        hibRepository.save(post2);

        List<Post> listOfPosts = postService.getAllPosts();
        assertThat(listOfPosts).hasSize(2).containsAll(List.of(post1, post2));
    }

    @Test
    void getPostByIdShouldReturnPost() {
        Post post = Post.builder()
            .content("hello World!")
            .titleText("hello world!")
            .build();

        hibRepository.save(post);

        Post postDb = postService.getPostById(post.getId());
        assertThat(postDb).isSameAs(post);
    }

    @Test
    void deletePostByIdShouldDeletePostFromDb() {
        Post post = Post.builder()
            .content("hello World!")
            .titleText("hello world!")
            .build();

        hibRepository.save(post);

        postService.deletePostById(post.getId());

        hibRepository.findById(post.getId());
        assertThatException().isThrownBy(() -> postService.getPostById(post.getId()));
        assertThat(hibRepository.findById(post.getId())).isEmpty();
    }
}
