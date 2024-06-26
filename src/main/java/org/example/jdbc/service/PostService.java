package org.example.jdbc.service;

import lombok.AllArgsConstructor;
import org.example.jdbc.mapper.PostMapper;
import org.example.jdbc.model.dto.CommentDto;
import org.example.jdbc.model.dto.PostDto;
import org.example.jdbc.model.entity.PostEntity;
import org.example.jdbc.repository.PostRepository;

import java.util.List;

@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentService commentService;
    private final PostMapper postMapper;

    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(postMapper::toPostDto)
                .toList();
    }

    public PostDto getPostById(final Long id) {
        final PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Post with id %s not found".formatted(id)));

        return postMapper.toPostDto(postEntity);
    }

    public PostDto savePost(final PostDto postDto) {
        final PostEntity postEntity = postMapper.toEntityPost(postDto);
        postRepository.save(postEntity);
        return postMapper.toPostDto(postEntity);
    }

    public void deletePostById(final Long id) {
        postRepository.deleteById(id);
    }

    public PostDto getPostWithComments(final Long id) {
        final PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Post with id %s not found".formatted(id)));

        final List<CommentDto> comments = commentService.getCommentByPostId(id);

        final PostDto post = postMapper.toPostDto(postEntity);

        post.setComments(comments);

        return post;
    }
}
