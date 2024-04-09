package org.example.service;

import org.example.model.dto.CommentDto;
import org.example.model.dto.PostDto;
import org.example.repository.CommentRepository;
import org.example.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentServiceTest {
    private final CommentRepository commentRepository = new CommentRepository();
    private final PostRepository postRepository = new PostRepository();

    private final CommentService commentService = new CommentService(commentRepository);
    private final PostService postService = new PostService(postRepository, commentService);

    @BeforeEach
    void setUp() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    void saveComment() {
        final PostDto postDto = postService.savePost(PostDto.builder()
            .title("Test")
            .content("Test")
            .build());
        final CommentDto commentDto = commentService.saveComment(CommentDto.builder()
            .text("Test")
            .author("Test")
            .postId(postDto.getId())
            .build());
        commentService.getCommentById(commentDto.getId())
            .ifPresent(
                v -> assertThat(v).usingRecursiveComparison()
                    .isEqualTo(commentDto)
            );
    }

    @Test
    void getById() {
        final PostDto postDto = postService.savePost(PostDto.builder()
            .title("Test")
            .content("Test")
            .build());
        final CommentDto commentDto = commentService.saveComment(CommentDto.builder()
            .text("Test")
            .author("Test")
            .postId(postDto.getId())
            .build());
        commentService.getCommentById(commentDto.getId())
            .ifPresent(
                v -> assertThat(v).usingRecursiveComparison()
                    .isEqualTo(commentDto)
            );
    }

    @Test
    void deleteCommentById() {
        final PostDto postDto = postService.savePost(PostDto.builder()
            .title("Test")
            .content("Test")
            .build());
        final CommentDto commentDto = commentService.saveComment(CommentDto.builder()
            .text("Test")
            .author("Test")
            .postId(postDto.getId())
            .build());
        commentService.deleteCommentById(commentDto.getId());
        assertThat(commentService.getCommentById(commentDto.getId())).isEmpty();
    }

    @Test
    void getAllComments() {
        final PostDto postDto = postService.savePost(PostDto.builder()
            .title("Test")
            .content("Test")
            .build());
        commentService.saveComment(CommentDto.builder()
            .text("Test")
            .author("Test")
            .postId(postDto.getId())
            .build());
        commentService.saveComment(CommentDto.builder()
            .text("Test")
            .author("Test")
            .postId(postDto.getId())
            .build());

        assertThat(commentService.getAllComments())
            .isNotEmpty()
            .hasSize(2);
    }

    @Test
    void getAllCommentsByPost() {
        final PostDto postDto = postService.savePost(PostDto.builder()
            .title("Test")
            .content("Test")
            .build());
        commentService.saveComment(CommentDto.builder()
            .text("Test")
            .author("Test")
            .postId(postDto.getId())
            .build());
        commentService.saveComment(CommentDto.builder()
            .text("Test")
            .author("Test")
            .postId(postDto.getId())
            .build());

        assertThat(commentService.getCommentByPostId(postDto.getId()))
            .isNotEmpty()
            .hasSize(2);
    }

    @Test
    void getEmptyComments() {
        assertThat(commentService.getCommentById(1L)).isEmpty();
    }

    @Test
    void getEmptyPostCommentsById() {
        assertThat(commentService.getCommentByPostId(1L)).isEmpty();
    }
}