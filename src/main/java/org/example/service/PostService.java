package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.entity.Post;
import org.example.repository.PostRepository;

@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post findById(Long id) {
        return postRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    //todo: refactor with dto
    //todo: mapper with MapStruct https://github.com/mapstruct/mapstruct
    public Post save(Post post) {
        return postRepository.save(post);
    }

    public Post getPostWithComments(final Long id) {
        return postRepository.getPostWithComments(id)
            .orElseThrow(() -> new RuntimeException("Post not found"));
    }
}
