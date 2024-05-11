package org.example.hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.hibernate.entity.Comment;
import org.example.hibernate.entity.Post;
import org.example.hibernate.entity.User;
import org.example.hibernate.utils.HibernateUtils;

import java.util.List;

public class App {
    public static void main(String[] args) {
        EntityManager entityManager = HibernateUtils.getEntityManager();

        System.out.println("BIDIRECTIONAL LINK / UNIDIRECTIONAL / CASCADE");

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        User user = User.builder()
            .userName("HelloWorld")
            .email("test@mail.com")
            .build();
        entityManager.persist(user);

        Comment comment1 = Comment.builder()
            .text("Hello World!")
            .author("Kirill")
            .build();
        Comment comment2 = Comment.builder()
            .text("Hello World!")
            .author("Kirill")
            .build();
        Comment comment3 = Comment.builder()
            .text("Hello World!")
            .author("Kirill")
            .build();

        Post post = Post.builder()
            .content("hello World!")
            .titleText("hello world!")
//            .user(user)
            .build()
            .withComment(comment1);

        Post post2 = Post.builder()
            .content("hello World!2222")
            .titleText("hello world!2222")
            .build()
            .withComment(Comment.builder()
                .text("Hello World!222")
                .author("Kirill222")
                .build());

        Post post3 = Post.builder()
            .content("hello World!3333")
            .titleText("hello world!3333")
            .build()
            .withComment(Comment.builder()
                .text("Hello World!333")
                .author("Kirill222")
                .build());

        entityManager.persist(post);
        entityManager.persist(post2);
        entityManager.persist(post3);

        transaction.commit();
        entityManager.close();

        System.out.println("===== FETCH_TYPE LAZY // STACK_OVERFLOW WITH TO_STRING// LAZY_INIT_EXCEPTION===== ");

        entityManager = HibernateUtils.getEntityManager();
        Post post1 = entityManager.find(Post.class, 1L);

        System.out.println("entityManager.find FINISHED");
        List<Comment> comments = post1.getComments();
        System.out.println(comments);

        entityManager.close();

        System.out.println("========================= ");
        System.out.println("===== N + 1 PROBLEM ===== ");
        System.out.println("========================= ");

        entityManager = HibernateUtils.getEntityManager();

        List<Comment> fromComment = entityManager.createQuery("FROM Comment", Comment.class)
            .getResultList();

        for (var comment : fromComment) {
            System.out.println(comment.getPost());
        }

        entityManager.close();

        System.out.println("===== JOINT FETCH ====");

        entityManager = HibernateUtils.getEntityManager();
        List<Post> resultList = entityManager.createQuery("FROM Post p JOIN FETCH p.comments", Post.class)
            .getResultList();

        resultList.forEach(System.out::println);

        System.out.println("===== CACHE first lvl / second lvl ===== ");

    }

}
