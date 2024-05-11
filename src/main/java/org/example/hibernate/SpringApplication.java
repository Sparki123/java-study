package org.example.hibernate;

import jakarta.persistence.EntityManager;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
        EntityManager getEntityManager = run.getBean("getEntityManager", EntityManager.class);
        System.out.println(getEntityManager);
    }
}
