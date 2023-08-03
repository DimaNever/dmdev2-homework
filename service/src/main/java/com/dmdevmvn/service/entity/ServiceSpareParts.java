package com.dmdevmvn.service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"service", "sparePart"})
@Builder
@Entity
@Table(name = "service_spare_parts")
public class ServiceSpareParts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Service service;

    @ManyToOne
    private SparePart sparePart;

    private Integer quantity;

    public void setService(Service service) {
        this.service = service;
        this.service.getServiceSpareParts().add(this);
    }

    public void setSparePart(SparePart sparePart) {
        this.sparePart = sparePart;
        this.sparePart.getServiceSpareParts().add(this);
    }
}
