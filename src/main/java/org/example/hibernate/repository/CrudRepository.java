package org.example.hibernate.repository;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("all")
public interface CrudRepository<T, ID> {
    List<T> findAll();

    T save(T t);

    Optional<T> findById(ID id);

    void deleteById(ID id);

}
