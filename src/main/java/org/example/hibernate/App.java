package org.example.hibernate;

import jakarta.persistence.EntityManager;
import org.example.hibernate.entity.Comment;
import org.example.hibernate.entity.Post;
import org.example.hibernate.repository.CommentHibRepository;
import org.example.hibernate.repository.PostHibRepository;
import org.example.hibernate.service.CommentService;
import org.example.hibernate.service.PostService;
import org.example.hibernate.utils.HibernateUtils;

import java.util.List;

public class App {
    public static void main(String[] args) {
        EntityManager entityManager = HibernateUtils.getEntityManager();
        PostHibRepository postHibRepository = new PostHibRepository(entityManager);
        CommentHibRepository commentHibRepository = new CommentHibRepository(entityManager);
        CommentService commentService = new CommentService(commentHibRepository);
        PostService postService = new PostService(postHibRepository);
        postService.savePost(Post.builder()
                .content("hello World!")
                .titleText("hello world!")
                .comments(List.of(Comment.builder()
                        .text("Hello World!")
                        .author("Kirill")
                        .build()))
                .build());
        System.out.println("hello");
    }
}
