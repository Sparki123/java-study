package org.example.hibernate.service;

import org.example.hibernate.entity.Comment;
import org.example.hibernate.repository.CommentHibRepository;

import java.util.List;
import java.util.Optional;

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

    public Optional<Comment> getCommentById(final Long id) {
        return commentRepository.findById(id);
    }

    public void deleteCommentById(final Long id) {
        commentRepository.deleteById(id);
    }
}