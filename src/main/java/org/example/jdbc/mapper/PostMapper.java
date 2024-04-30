package org.example.jdbc.mapper;

import org.example.jdbc.model.dto.PostDto;
import org.example.jdbc.model.entity.PostEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        imports = List.class,
        uses = CommentMapper.class
)
public interface PostMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "content", source = "content")
    @IterableMapping(qualifiedByName = "toCommentDto")
    PostDto toPostDto(PostEntity postEntity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "content", source = "content")
    @IterableMapping(qualifiedByName = "toComment")
    PostEntity toEntityPost(PostDto postDto);
}
