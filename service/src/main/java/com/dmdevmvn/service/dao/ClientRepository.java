package com.dmdevmvn.service.dao;

import com.dmdevmvn.service.entity.Client;

import javax.persistence.EntityManager;

public class ClientRepository extends RepositoryBase<Long, Client> {

    public ClientRepository(EntityManager entityManager) {
        super(Client.class, entityManager);
    }
}
