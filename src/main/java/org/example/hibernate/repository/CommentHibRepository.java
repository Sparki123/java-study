package org.example.hibernate.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.example.hibernate.entity.Comment;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CommentHibRepository implements CrudRepository<Comment, Long> {
    private final EntityManager entityManager;

    //Можно указывать бин при внедрении через @Qualifier(value = "getEntityManagerAnotherBean")
    public CommentHibRepository(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Comment> findAll() {
        final Query query = entityManager.createQuery("FROM Comment", Comment.class);
        return query.getResultList();
    }

    public Comment save(final Comment commentEntity) {
        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(commentEntity);
        transaction.commit();
        return commentEntity;
    }

    public Optional<Comment> findById(final Long id) {
        return Optional.ofNullable(entityManager.find(Comment.class, id));
    }

    public List<Comment> findByPostId(final Long id) {
        Query query = entityManager.createQuery("FROM Comment WHERE post.id = :postId", Comment.class);
        query.setParameter("postId", id);
        return query.getResultList();
    }

    public void deleteById(final Long id) {
        Comment commentEntity = entityManager.find(Comment.class, id);
        if (commentEntity != null) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(commentEntity);
            transaction.commit();
        }
    }

    public void deleteAll() {
        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.createQuery("DELETE FROM Comment").executeUpdate();
        transaction.commit();
    }
}
