package org.example.hibernate.service;

import org.example.hibernate.entity.Post;
import org.example.hibernate.repository.PostHibRepository;

import java.util.List;

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

    public Post getPostById(final Long id) {
        return postRepository.findById(id).orElseThrow(
            () -> new IllegalStateException("Post with id %s not found".formatted(id)));
    }

    public Post updatePost(Post post) {
        Post existingPost = postRepository.findById(post.getId())
            .orElseThrow(() -> new RuntimeException("Пост не найден"));

        existingPost.setTitleText(post.getTitleText());
        existingPost.setContent(post.getContent());

        return postRepository.save(existingPost);
    }

    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }
}
