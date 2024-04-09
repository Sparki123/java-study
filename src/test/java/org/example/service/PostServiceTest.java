package org.example.service;

import org.example.model.dto.CommentDto;
import org.example.model.dto.PostDto;
import org.example.support.IntegrationTestBase;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostServiceTest extends IntegrationTestBase {

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

        assertThat(postService.getPostById(postDto.getId()))
            .isEmpty();
    }

    @Test
    void getAllPosts() {
        postService.savePost(PostDto.builder()
            .title("Test")
            .content("Test")
            .build());
        postService.savePost(PostDto.builder()
            .title("Test")
            .content("Test")
            .build());

        assertThat(postService.getAllPosts())
            .isNotEmpty()
            .hasSize(2);
    }

    @Test
    void getPostWithCommentsShouldSuccess() {
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
        final PostDto actualResult = postService.getPostWithComments(postDto.getId());

        assertThat(postService.getPostById(actualResult.getId()))
            .isNotEmpty();
        assertThat(actualResult.getComments())
            .isNotEmpty()
            .hasSize(2);
    }

    @Test
    void getPostWithCommentsShouldReturnExceptionWhenNotExist() {
        assertThat(postService.getPostWithComments(1L));
    }

    @Test
    void getEmptyPostById() {
        assertThat(postService.getPostById(1L)).isEmpty();
    }
}
