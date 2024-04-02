package org.example.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {
    private Long id;
    private String author;
    private String text;
    private Long postId;
}
