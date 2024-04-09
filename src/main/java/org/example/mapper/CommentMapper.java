package org.example.mapper;

import org.example.model.dto.CommentDto;
import org.example.model.entity.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CommentMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "text", source = "text")
    @Mapping(target = "postId", source = "postId")
    CommentDto toCommentDto(CommentEntity commentEntity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "text", source = "text")
    @Mapping(target = "postId", source = "postId")
    CommentEntity toComment(CommentDto comment);
}
