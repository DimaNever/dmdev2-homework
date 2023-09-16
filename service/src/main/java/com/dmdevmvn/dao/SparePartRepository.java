package com.dmdevmvn.dao;

import com.dmdevmvn.entity.SparePart;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dmdevmvn.entity.QOrderSpareParts.orderSpareParts;

import static com.dmdevmvn.entity.QSparePart.sparePart;

@Repository
public class SparePartRepository extends RepositoryBase<Long, SparePart> {

    public SparePartRepository(EntityManager entityManager) {
        super(SparePart.class, entityManager);
    }

    public List<SparePart> findAllSparePartsByOrderId(Long orderId) {
        return new JPAQuery<SparePart>(getEntityManager())
                .select(sparePart)
                .from(sparePart)
                .join(sparePart.orderSpareParts, orderSpareParts)
                .where(orderSpareParts.order.id.eq(orderId))
                .fetch();
    }
}
