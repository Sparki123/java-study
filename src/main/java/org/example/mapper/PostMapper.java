package org.example.mapper;

import org.example.model.dto.PostDto;
import org.example.model.entity.Post;
import org.mapstruct.Mapper;

@Mapper
public interface PostMapper {
    PostDto toDto(Post post);

    Post toEntity(PostDto postDto);
}
