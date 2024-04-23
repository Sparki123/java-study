package org.example.repository.hibernate;

import org.example.model.hibernate.entity.Comment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class CommentHibRepository {
    private final SessionFactory sessionFactory;

    public CommentHibRepository(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Comment> findAll() {
        try (Session session = sessionFactory.openSession()) {
            final Query<Comment> query = session.createQuery("FROM Comment", Comment.class);
            return query.list();
        }
    }

    public Comment save(final Comment commentEntity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(commentEntity);
            transaction.commit();
            return commentEntity;
        }
    }

    public Optional<Comment> findById(final Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Comment.class, id));
        }
    }

    public List<Comment> findByPostId(final Long id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Comment> query = session.createQuery("FROM Comment WHERE postId = :postId", Comment.class);
            query.setParameter("postId", id);
            return query.list();
        }
    }

    public void deleteById(final Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Comment commentEntity = session.get(Comment.class, id);
            if (commentEntity != null) {
                session.remove(commentEntity);
            }
            transaction.commit();
        }
    }

    public void deleteAll() {
        try (Session session = sessionFactory.openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.createQuery("DELETE FROM Comment", Comment.class).executeUpdate();
            transaction.commit();
        }
    }
}
