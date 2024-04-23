package org.example.service.hibernate;

import org.example.mapper.PostMapper;
import org.example.model.dto.PostDto;
import org.example.repository.hibernate.PostHibRepository;
import org.hibernate.SessionFactory;

import java.util.List;

public class PostService {
    private final PostHibRepository postHibRepository;

    private final PostMapper postMapper;


    public PostService(final SessionFactory sessionFactory, final PostMapper postMapper) {
        this.postHibRepository = new PostHibRepository(sessionFactory);
        this.postMapper = postMapper;
    }

    public List<PostDto> getAllPosts() {
        return postHibRepository.findAll().stream().map(postMapper::toPostDto);
    }

    public PostDto getPostById(final Long id) {
        return postHibRepository.findById(id);
    }

    public PostDto savePost(final PostDto postDto) {
        return postHibRepository.save(postDto);
    }

    public void deletePost(final Long id) {
        postHibRepository.deleteById(id);
    }

    public void deleteAllPosts() {
        postHibRepository.deleteAll();
    }
}