package com.dmdevmvn.dao;

import com.dmdevmvn.entity.OrderSpareParts;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class OrderSparePartsRepository extends RepositoryBase<Long, OrderSpareParts> {

    public OrderSparePartsRepository(EntityManager entityManager) {
        super(OrderSpareParts.class, entityManager);
    }
}
