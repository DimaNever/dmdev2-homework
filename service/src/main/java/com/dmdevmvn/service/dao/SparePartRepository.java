package com.dmdevmvn.service.dao;

import com.dmdevmvn.service.entity.SparePart;

import javax.persistence.EntityManager;

public class SparePartRepository extends RepositoryBase<Long, SparePart> {

    public SparePartRepository(EntityManager entityManager) {
        super(SparePart.class, entityManager);
    }
}
