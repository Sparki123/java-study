package org.example.model.dto;

import java.util.List;

public record PostDto(Long id, String title, String content, List<CommentDto> comments) {
}
