package com.dmdevmvn.dao;

import com.dmdevmvn.entity.Role;
import com.dmdevmvn.entity.User;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dmdevmvn.entity.QUser.user;

@Repository
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
