package org.example.support;

import lombok.experimental.UtilityClass;
import org.example.model.dto.CommentDto;
import org.example.model.dto.PostDto;

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
}
