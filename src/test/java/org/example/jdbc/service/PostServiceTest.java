package org.example.jdbc.service;

import org.example.jdbc.model.dto.PostDto;
import org.example.jdbc.model.entity.CommentEntity;
import org.example.jdbc.model.entity.PostEntity;
import org.example.support.IntegrationTestBase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;

class PostServiceTest extends IntegrationTestBase {

    @Test
    void savePostShouldReturnPostDto() {
        final PostDto postDto = postService.savePost(PostDto.builder()
                .title("Test")
                .content("Test")
                .comments(new ArrayList<>())
                .build());

        assertThat(postDto.getId()).isNotNull();
        assertThat(postDto).usingRecursiveComparison()
                .isEqualTo(postRepository.findById(postDto.getId()).get());

    }

    @Test
    void getByIdShouldReturnPostDto() {
        final PostEntity post = postRepository.save(PostEntity.builder()
                .title("Test")
                .content("Test")
                .comments(new ArrayList<>())
                .build());

        final PostDto postById = postService.getPostById(post.getId());

        assertThat(postById.getId()).isNotNull();
        assertThat(postById).usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(PostDto.builder()
                    .title("Test")
                    .content("Test")
                    .comments(new ArrayList<>())
                    .build()
        );
    }

    @Test
    void deletePostByIdShouldSuccess() {
        final PostEntity post = postRepository.save(PostEntity.builder()
                .title("Test")
                .content("Test")
                .comments(new ArrayList<>())
                .build());

        postService.deletePostById(post.getId());

        assertThat(postRepository.findById(post.getId())).isEmpty();
    }

    @Test
    void getAllPostsShouldReturnListPostDto() {
        final PostEntity post = postRepository.save(PostEntity.builder()
                .title("Test")
                .content("Test")
                .comments(new ArrayList<>())
                .build());

        assertThat(postService.getAllPosts())
                .isNotEmpty()
                .hasSize(1)
                .contains(postMapper.toPostDto(post));
    }

    @Test
    void getPostWithCommentsShouldSuccess() {
        final PostEntity post = postRepository.save(PostEntity.builder()
                .title("Test")
                .content("Test")
                .comments(new ArrayList<>())
                .build());
        final CommentEntity expectedComment = commentRepository.save(CommentEntity.builder()
                .author("test")
                .text("test")
                .postId(post.getId())
                .build());

        final PostDto actualResult = postService.getPostWithComments(post.getId());

        assertThat(actualResult.getComments())
                .isNotEmpty()
                .hasSize(1)
                .contains(commentMapper.toCommentDto(expectedComment));
    }

    @Test
    void getPostWithCommentsShouldReturnExceptionWhenNotExist() {
        assertThatException().isThrownBy(() -> postService.getPostWithComments(1L));
    }

    @Test
    void getEmptyPostByIdShouldReturnNull() {
        assertThatException().isThrownBy(() -> postService.getPostById(1L));
    }
}
