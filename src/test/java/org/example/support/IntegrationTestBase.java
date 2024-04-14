package org.example.support;

import org.example.Main;
import org.example.mapper.CommentMapper;
import org.example.mapper.PostMapper;
import org.example.repository.jdbc.CommentRepository;
import org.example.repository.jdbc.PostRepository;
import org.example.service.CommentService;
import org.example.service.PostService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mapstruct.factory.Mappers;

public class IntegrationTestBase {
    protected final CommentRepository commentRepository = new CommentRepository();
    protected final PostRepository postRepository = new PostRepository();

    protected final CommentService commentService = new CommentService(commentRepository);
    protected final PostMapper postMapper = Mappers.getMapper(PostMapper.class);
    protected final CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);
    protected final PostService postService = new PostService(postRepository, commentService, postMapper);

    @BeforeAll
    static void beforeAll() {
        Main.init();
        System.out.println("BeforeAll beforeAll!!");
    }

    @BeforeEach
    void setUp() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
    }
}
