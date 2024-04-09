package org.example.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.mapper.PostMapper;
import org.example.model.dto.CommentDto;
import org.example.model.dto.PostDto;
import org.example.model.entity.PostEntity;
import org.example.repository.PostRepository;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentService commentService;
    private final PostMapper postMapper = Mappers.getMapper(PostMapper.class);

    public List<PostDto> getAllPosts() {
        List<PostEntity> postEntities = postRepository.findAll();
        return postEntities.stream()
            .map(postMapper::toPostDto)
            .toList();
    }

    public Optional<PostDto> getPostById(Long id) {
        Optional<PostEntity> postEntityOptional = postRepository.findById(id);
        return postEntityOptional.map(postMapper::toPostDto);
    }

    public PostDto savePost(PostDto postDto) {
        PostEntity postEntity = postMapper.toEntityPost(postDto);
        return postMapper.toPostDto(postRepository.save(postEntity));
    }

    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

    public Optional<PostDto> getPostWithComments(Long id) {
        Optional<PostEntity> postEntity = postRepository.findById(id);

        if (postEntity.isPresent()) {
            PostDto post = postMapper.toPostDto(postEntity.get());
            post.setComments(commentService.getCommentByPostId(id));
            return Optional.of(post);
        }

        return Optional.empty();
    }
}
