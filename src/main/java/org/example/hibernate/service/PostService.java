package org.example.hibernate.service;

import org.example.hibernate.entity.Post;
import org.example.hibernate.repository.PostHibRepository;

import java.util.List;
import java.util.Optional;

public class PostService {
    private final PostHibRepository postRepository;

    public PostService(final PostHibRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post savePost(final Post post) {
        return postRepository.save(post);
    }

    public Optional<Post> getPostById(final Long id) {
        return postRepository.findById(id);
    }

    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }
}
