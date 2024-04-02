package org.example.model.dto;

public record CommentDto(Long id, String author, String text, Long postId) {
}
