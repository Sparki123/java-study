package org.example.mapper;

import org.example.model.dto.CommentDto;
import org.example.model.entity.CommentEntity;
import org.example.support.TestDataProvider;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentEntityMapperTest {

    private final CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    @Test
    void shouldConvertToDto() {
        final CommentEntity commentEntity = TestDataProvider.prepareComment()
            .build();

        assertThat(commentMapper.toCommentDto(commentEntity))
            .usingRecursiveComparison()
            .isEqualTo(CommentDto.builder()
                .id(123L)
                .author("Test")
                .postId(12L)
                .text("Hello World")
                .build()
            );
    }

    @Test
    void shouldConvertToEntity() {
        final CommentDto commentDto = TestDataProvider.prepareCommentDto()
            .build();

        assertThat(commentMapper.toComment(commentDto))
            .usingRecursiveComparison()
            .isEqualTo(CommentEntity.builder()
                .id(123L)
                .author("Test")
                .postId(12L)
                .text("Hello World")
                .build()
            );
    }

}
