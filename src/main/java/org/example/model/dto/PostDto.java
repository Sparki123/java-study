package org.example.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private List<CommentDto> comments;
}
