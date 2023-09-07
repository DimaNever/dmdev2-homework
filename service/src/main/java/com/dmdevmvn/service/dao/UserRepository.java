package com.dmdevmvn.service.dao;

import com.dmdevmvn.service.entity.Role;
import com.dmdevmvn.service.entity.User;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dmdevmvn.service.entity.QUser.user;

public class UserRepository extends RepositoryBase<Long, User> {

    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);
    }

    public List<User> findAllByRole(Role role) {
        return new JPAQuery<User>(getEntityManager())
                .select(user)
                .from(user)
                .where(user.role.eq(role))
                .fetch();
    }
}
