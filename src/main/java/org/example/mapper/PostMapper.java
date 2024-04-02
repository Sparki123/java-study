package org.example.mapper;

import org.example.model.dto.PostDto;
import org.example.model.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(
    imports = List.class,
    uses = CommentMapper.class
)
public interface PostMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = ".", qualifiedByName = "toTitle")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "comments", expression = "java(List.of())")
    PostDto toPostDto(Post post);

    @Named("toTitle")
    default String toTitle(Post post) {
        return "Title: " + post.getTitle() + " " + post.getId();
    }

    Post toEntityPost(PostDto postDto);
}
