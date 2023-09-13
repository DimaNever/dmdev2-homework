package com.dmdevmvn.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "orderSpareParts")
@EqualsAndHashCode(exclude = "orderSpareParts")
@Builder
@Entity
@Table(name = "spare_part")
public class SparePart implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vendor_code", unique = true)
    private String vendorCode;

    private String title;

    private Long price;

    @Builder.Default
    @OneToMany(mappedBy = "sparePart")
    private List<OrderSpareParts> orderSpareParts = new ArrayList<>();
}
