package com.dmdevmvn.service.dao;

import com.dmdevmvn.service.entity.OrderSpareParts;

import javax.persistence.EntityManager;

public class OrderSparePartsRepository extends RepositoryBase<Long, OrderSpareParts> {

    public OrderSparePartsRepository(EntityManager entityManager) {
        super(OrderSpareParts.class, entityManager);
    }
}
