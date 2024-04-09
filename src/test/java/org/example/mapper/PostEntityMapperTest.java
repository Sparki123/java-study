package org.example.mapper;

import org.example.model.dto.CommentDto;
import org.example.model.dto.PostDto;
import org.example.model.entity.CommentEntity;
import org.example.model.entity.PostEntity;
import org.example.support.TestDataProvider;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class PostEntityMapperTest {

    private final PostMapper postMapper = Mappers.getMapper(PostMapper.class);

    @Test
    void shouldConvertToDto() {
        final PostEntity postEntity = TestDataProvider.preparePost()
            .build();

        assertThat(postMapper.toPostDto(postEntity))
            .usingRecursiveComparison()
            .isEqualTo(PostDto.builder()
                .id(123L)
                .title("Test")
                .content("Test")
                .comments(List.of(CommentDto.builder()
                    .id(10L)
                    .author("test")
                    .text("test")
                    .postId(123L)
                    .build()))
                .build());
    }

    @Test
    void shouldConvertToEntity() {
        final PostDto postDto = TestDataProvider.preparePostDto()
            .title("321")
            .build();

        assertThat(postMapper.toEntityPost(postDto))
            .usingRecursiveComparison()
            .isEqualTo(PostEntity.builder()
                .id(123L)
                .title("321")
                .content("Test")
                .comments(List.of(CommentEntity.builder()
                    .id(10L)
                    .author("test")
                    .text("test")
                    .postId(129L)
                    .build()))
                .build());
    }

}
