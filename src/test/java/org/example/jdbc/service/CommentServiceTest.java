package org.example.jdbc.service;

import org.example.jdbc.model.dto.CommentDto;
import org.example.jdbc.model.entity.CommentEntity;
import org.example.jdbc.model.entity.PostEntity;
import org.example.support.IntegrationTestBase;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;

class CommentServiceTest extends IntegrationTestBase {

    @Test
    void saveCommentShouldSuccess() {
        final PostEntity postDto = postRepository.save(PostEntity.builder()
                .title("Test")
                .content("Test")
                .build());
        final CommentDto commentDto = commentService.saveComment(CommentDto.builder()
                .text("Test")
                .author("Test")
                .postId(postDto.getId())
                .build());

        assertThat(commentDto.getId()).isNotNull();
        assertThat(commentDto).isEqualTo(CommentDto.builder()
                .id(commentDto.getId())
                .text("Test")
                .author("Test")
                .postId(postDto.getId())
                .build());
    }

    @Test
    void getByIdShouldSuccess() {
        final PostEntity postDto = postRepository.save(PostEntity.builder()
                .title("Test")
                .content("Test")
                .build());
        final CommentEntity commentEntity = commentRepository.save(CommentEntity.builder()
                .text("Test")
                .author("Test")
                .postId(postDto.getId())
                .build());
        final CommentDto commentById = commentService.getCommentById(commentEntity.getId());

        assertThat(commentById.getId()).isNotNull();
        assertThat(commentById).isEqualTo(commentMapper.toCommentDto(commentEntity));

    }

    @Test
    void deleteCommentByIdShouldSuccess() {
        final PostEntity postDto = postRepository.save(PostEntity.builder()
                .title("Test")
                .content("Test")
                .build());
        final CommentEntity commentEntity = commentRepository.save(CommentEntity.builder()
                .text("Test")
                .author("Test")
                .postId(postDto.getId())
                .build());
        commentService.deleteCommentById(commentEntity.getId());

        assertThat(commentRepository.findById(commentEntity.getId())).isEmpty();
    }

    @Test
    void getAllCommentsShouldReturnList() {
        final PostEntity postDto = postRepository.save(PostEntity.builder()
                .title("Test")
                .content("Test")
                .build());
        final CommentEntity commentEntity = commentRepository.save(CommentEntity.builder()
                .text("Test")
                .author("Test")
                .postId(postDto.getId())
                .build());

        assertThat(commentService.getAllComments())
                .isNotEmpty()
                .hasSize(1)
                .contains(commentMapper.toCommentDto(commentEntity));
    }

    @Test
    void getAllCommentsByPostShouldReturnList() {
        final PostEntity postDto = postRepository.save(PostEntity.builder()
                .title("Test")
                .content("Test")
                .build());
        final CommentEntity commentEntity = commentRepository.save(CommentEntity.builder()
                .text("Test")
                .author("Test")
                .postId(postDto.getId())
                .build());

        assertThat(commentService.getCommentByPostId(postDto.getId()))
                .isNotEmpty()
                .hasSize(1)
                .contains(commentMapper.toCommentDto(commentEntity));
    }

    @Test
    void getCommentByIdShouldReturnNull() {
        assertThatException().isThrownBy(() -> commentService.getCommentById(1L));
    }

    @Test
    void gePostCommentsByIdShouldReturnEmptyList() {
        assertThat(commentService.getCommentByPostId(1L)).isEmpty();
    }
}
