package org.example;

import org.example.repository.CommentRepository;
import org.example.repository.PostRepository;
import org.example.service.CommentService;
import org.example.service.PostService;

public class Main {
    public static void main(String[] args) {
        final CommentRepository commentRepository = new CommentRepository();
        final PostRepository postRepository = new PostRepository();

        final CommentService commentService = new CommentService(commentRepository);
        final PostService postService = new PostService(postRepository);

    }
}
