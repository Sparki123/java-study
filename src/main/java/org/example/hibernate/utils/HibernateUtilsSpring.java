package org.example.hibernate.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class HibernateUtilsSpring {
    private final EntityManagerFactory ENTITY_MANAGER_FACTORY =
        Persistence.createEntityManagerFactory("JAVA");

    @Bean
    @Primary
    public EntityManager getEntityManager() {
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    @Bean
    public EntityManager getEntityManagerAnotherBean() {
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }
}

