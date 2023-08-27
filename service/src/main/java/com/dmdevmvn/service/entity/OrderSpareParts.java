package com.dmdevmvn.service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"order", "sparePart"})
@EqualsAndHashCode(exclude = {"order", "sparePart"})
@Builder
@Entity
@Table(name = "order_spare_parts")
public class OrderSpareParts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private SparePart sparePart;

    private Integer quantity;

    public void setOrder(Order order) {
        this.order = order;
        this.order.getOrderSpareParts().add(this);
    }

    public void setSparePart(SparePart sparePart) {
        this.sparePart = sparePart;
        this.sparePart.getOrderSpareParts().add(this);
    }
}
