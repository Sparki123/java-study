package org.example.repository;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("java:S119")
public interface CrudRepository<T, ID> {
    List<T> findAll();

    T save(final T post);

    Optional<T> findById(final ID id);

    void deleteById(final ID id);

}
