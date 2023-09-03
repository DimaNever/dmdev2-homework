package com.dmdevmvn.service.dao;

import com.dmdevmvn.service.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyMap;

public interface Dao<K extends Serializable, E extends BaseEntity<K>> {

    E save(E entity);

    void delete(K id);

    void update(E entity);

    default Optional<E> findByID(K id) {
        return findByID(id, emptyMap());
    }

    Optional<E> findByID(K id, Map<String, Object> properties);

    List<E> findAll();
}
