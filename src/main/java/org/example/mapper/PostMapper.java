package org.example.mapper;

import org.example.model.dto.PostDto;
import org.example.model.entity.PostEntity;
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
    @Mapping(target = "comments", source = "comments")
    PostDto toPostDto(PostEntity postEntity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "comments", source = "comments")
    PostEntity toEntityPost(PostDto postDto);

    //Can't generate mapping method from iterable type from java stdlib to non-iterable type.
    //    @Mapping(target = "comments", source = "comments")
    //    void updateWithComments(@MappingTarget PostDto postDto, List<CommentDto> comments);
}
