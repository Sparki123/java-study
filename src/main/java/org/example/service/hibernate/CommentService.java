package org.example.service.hibernate;

import org.example.model.hibernate.entity.Comment;
import org.example.repository.hibernate.CommentHibRepository;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class CommentService {
    private final CommentHibRepository commentRepository;

    public CommentService(final SessionFactory sessionFactory) {
        this.commentRepository = new CommentHibRepository(sessionFactory);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Optional<Comment> getCommentById(final Long id) {
        return commentRepository.findById(id);
    }

    public List<Comment> getCommentsByPostId(final Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public Comment saveComment(final Comment commentEntity) {
        return commentRepository.save(commentEntity);
    }

    public void deleteComment(final Long id) {
        commentRepository.deleteById(id);
    }

    public void deleteAllComments() {
        commentRepository.deleteAll();
    }
}
