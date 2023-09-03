package com.dmdevmvn.service.dao;

import com.dmdevmvn.service.entity.User;

import javax.persistence.EntityManager;

public class UserRepository extends RepositoryBase<Long, User> {

    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);
    }
}
