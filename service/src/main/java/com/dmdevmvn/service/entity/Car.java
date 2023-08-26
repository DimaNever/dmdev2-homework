package com.dmdevmvn.service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NamedQuery(name = "findCarByModel", query = "select car from Car car " +
                                             "join car.client cl " +
                                             "where car.model = :title and cl.firstName = :name")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"client", "orders"})
@EqualsAndHashCode(exclude = {"client", "orders"})
@Builder
@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;

    private Integer year;

    private Long mileage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @Builder.Default
    @OneToMany(mappedBy = "car")
    private List<Order> orders = new ArrayList<>();
}
