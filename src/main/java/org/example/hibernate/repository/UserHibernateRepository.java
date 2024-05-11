package org.example.hibernate.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.example.hibernate.entity.User;

import java.util.List;
import java.util.Optional;

public class UserHibernateRepository implements CrudRepository<User, Long> {
    private final EntityManager entityManager;

    public UserHibernateRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<User> findAll() {
        Query query = entityManager.createQuery("FROM User");
        return query.getResultList();
    }

    public User save(User userEntity) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(userEntity);
        transaction.commit();
        return userEntity;
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    public void deleteById(Long id) {
        User userEntity = entityManager.find(User.class, id);
        if (userEntity != null) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(userEntity);
            transaction.commit();
        }
    }

    public void deleteAll() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.createQuery("DELETE FROM User").executeUpdate();
        transaction.commit();
    }
}
