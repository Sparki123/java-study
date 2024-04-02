package org.example.mapper;

import org.example.model.dto.CommentDto;
import org.example.model.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CommentMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "text", source = "text")
    CommentDto toCommentDto(Comment comment);

}
