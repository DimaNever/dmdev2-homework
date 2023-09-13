package com.dmdevmvn.dao;

import com.dmdevmvn.entity.Client;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ClientRepository extends RepositoryBase<Long, Client> {

    public ClientRepository(EntityManager entityManager) {
        super(Client.class, entityManager);
    }
}
