package org.example.hibernate.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.example.hibernate.entity.Post;

import java.util.List;
import java.util.Optional;

public class PostHibRepository implements CrudRepository<Post, Long> {
    private final EntityManager entityManager;

    public PostHibRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Post> findAll() {
        Query query = entityManager.createQuery("FROM Post");
        return query.getResultList();
    }

    public Post save(Post postEntity) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(postEntity);
        transaction.commit();
        return postEntity;
    }

    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Post.class, id));
    }

    public void deleteById(Long id) {
        Post postEntity = entityManager.find(Post.class, id);
        if (postEntity != null) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(postEntity);
            transaction.commit();
        }
    }

    public void deleteAll() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.createQuery("DELETE FROM Post").executeUpdate();
        transaction.commit();
    }
}
