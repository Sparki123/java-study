package org.example.hibernate.service;

import org.example.hibernate.entity.Comment;
import org.example.hibernate.repository.CommentHibRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentService {
    private final CommentHibRepository commentRepository;

    public CommentService(final CommentHibRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment saveComment(final Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment getCommentById(final Long id) {
        return commentRepository.findById(id).orElseThrow(
            () -> new IllegalStateException("Post with id %s not found".formatted(id)));
    }

    public void deleteCommentById(final Long id) {
        commentRepository.deleteById(id);
    }
}
