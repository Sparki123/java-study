package org.example.jdbc.repository;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("all")
public interface CrudRepository<T, ID> {
    List<T> findAll();

    T save(T post);

    Optional<T> findById(ID id);

    void deleteById(ID id);

}
