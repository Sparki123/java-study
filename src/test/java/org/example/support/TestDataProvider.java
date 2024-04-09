package org.example.support;

import lombok.experimental.UtilityClass;
import org.example.model.dto.CommentDto;
import org.example.model.dto.PostDto;
import org.example.model.entity.PostEntity;
import org.example.model.entity.CommentEntity;

import java.util.List;


@UtilityClass
public class TestDataProvider {
    public static PostDto.PostDtoBuilder preparePostDto() {
        return PostDto.builder()
            .id(123L)
            .title("Test")
            .content("Test")
            .comments(List.of(
                CommentDto.builder()
                    .id(10L)
                    .author("test")
                    .text("test")
                    .postId(129L)
                    .build()
            ));
    }

    public static PostEntity.PostEntityBuilder preparePost() {
        return PostEntity.builder()
            .id(123L)
            .title("Test")
            .content("Test")
            .comments(List.of(
                CommentEntity.builder()
                    .id(10L)
                    .author("test")
                    .text("test")
                    .postId(123L)
                    .build()
            ));
    }

    public static CommentDto.CommentDtoBuilder prepareCommentDto() {
        return CommentDto.builder()
            .id(123L)
            .author("Test")
            .postId(12L)
            .text("Hello World");
    }

    public static CommentEntity.CommentEntityBuilder prepareComment() {
        return CommentEntity.builder()
            .id(123L)
            .author("Test")
            .postId(12L)
            .text("Hello World");
    }
}
