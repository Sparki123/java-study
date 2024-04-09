package org.example.service;

import org.example.model.dto.CommentDto;
import org.example.model.dto.PostDto;
import org.example.repository.CommentRepository;
import org.example.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostServiceTest {

    private final CommentRepository commentRepository = new CommentRepository();
    private final PostRepository postRepository = new PostRepository();

    private final CommentService commentService = new CommentService(commentRepository);
    private final PostService postService = new PostService(postRepository, commentService);

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
    }

    @Test
    void savePost() {
        final PostDto postDto = postService.savePost(PostDto.builder()
            .title("Test")
            .content("Test")
            .build());
        postService.getPostById(postDto.getId())
            .ifPresent(
                v -> assertThat(v).usingRecursiveComparison()
                    .ignoringFields("comments")
                    .isEqualTo(postDto)
            );
    }

    @Test
    void getById() {
        final PostDto postDto = postService.savePost(PostDto.builder()
            .title("Test")
            .content("Test")
            .build());
        postService.getPostById(postDto.getId())
            .ifPresent(
                v -> assertThat(v).usingRecursiveComparison()
                    .ignoringFields("comments")
                    .isEqualTo(postDto));
    }

    @Test
    void deletePostById() {
        final PostDto postDto = postService.savePost(PostDto.builder()
            .title("Test")
            .content("Test")
            .build());
        postService.deletePostById(postDto.getId());

        assertThat(postService.getPostById(postDto.getId())).isEmpty();
    }

    @Test
    void getAllPosts() {
        final PostDto postDto = postService.savePost(PostDto.builder()
            .title("Test")
            .content("Test")
            .build());
        final PostDto postDto2 = postService.savePost(PostDto.builder()
            .title("Test")
            .content("Test")
            .build());

        assertThat(postService.getAllPosts())
            .isNotEmpty()
            .hasSize(2);
    }

    @Test
    void getPostWithComments() {
        final PostDto postDto = postService.savePost(PostDto.builder()
            .title("Test")
            .content("Test")
            .build());
        commentService.saveComment(CommentDto.builder()
            .author("test")
            .text("test")
            .postId(postDto.getId())
            .build());
        commentService.saveComment(CommentDto.builder()
            .author("test")
            .text("test")
            .postId(postDto.getId())
            .build());
        final PostDto postDto1 = postService.getPostWithComments(postDto.getId()).get();

        assertThat(postService.getPostById(postDto1.getId())).isNotEmpty();
        assertThat(postDto1.getComments()).isNotEmpty().hasSize(2);
    }

    @Test
    void getEmptyPostWithComments() {
        assertThat(postService.getPostWithComments(1L)).isEmpty();
    }

    @Test
    void getEmptyPostById() {
        assertThat(postService.getPostById(1L)).isEmpty();
    }
}
