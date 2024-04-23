package org.example.repository.hibernate;

import org.example.model.hiberentity.Post;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class PostHibRepository {
    private final SessionFactory sessionFactory;

    public PostHibRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Post> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Post> query = session.createQuery("FROM Post", Post.class);
            return query.list();
        }
    }

    public Post save(final Post postEntity) {
        try (Session session = sessionFactory.openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.merge(postEntity);
            transaction.commit();
            return postEntity;
        }
    }

    public Optional<Post> findById(final Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Post.class, id));
        }
    }

    public void deleteById(final Long id) {
        try (Session session = sessionFactory.openSession()) {
            final Transaction transaction = session.beginTransaction();
            final Post postEntity = session.get(Post.class, id);
            if (postEntity != null) {
                session.remove(postEntity);
            }
            transaction.commit();
        }
    }

    public void deleteAll() {
        try (Session session = sessionFactory.openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.createQuery("DELETE FROM Post", Post.class).executeUpdate();
            transaction.commit();
        }
    }
}
